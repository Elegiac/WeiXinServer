package edu.demo.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import edu.demo.bean.event.EventType;
import edu.demo.bean.event.WeChatEvent;
import edu.demo.bean.message.WeChatMessage;
import edu.demo.bean.message.receive.ImageReceive;
import edu.demo.bean.message.receive.LocationReceive;
import edu.demo.bean.message.receive.TextReceive;
import edu.demo.bean.message.receive.VideoReceive;
import edu.demo.bean.message.receive.VoiceReceive;
import edu.demo.bean.message.reply.ImageReply;
import edu.demo.bean.message.reply.ImageReply.Image;
import edu.demo.bean.message.reply.NewsReply;
import edu.demo.bean.message.reply.NewsReply.Item;
import edu.demo.bean.message.reply.TextReply;
import edu.demo.bean.message.reply.VoiceReply;
import edu.demo.bean.message.reply.VoiceReply.Voice;
import edu.demo.utils.BeanUtils;
import edu.demo.utils.StringUtils;
import edu.demo.utils.TulingApiProcess;

public class WeChatMessageFactory {
	@SuppressWarnings("unchecked")
	public static WeChatMessage parseMapToMessage(Map<String, Object> params)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String type = params.get("MsgType").toString();

		String className = null;

		if (type.equals("event")) {

			className = "edu.demo.bean.event."
					+ StringUtils.upperCaseFirstLetter(params.get("Event").toString().toLowerCase()) + "Event";

		} else {

			className = "edu.demo.bean.message.receive." + StringUtils.upperCaseFirstLetter(type.toLowerCase())
					+ "Receive";
		}

		Class<WeChatMessage> clz = (Class<WeChatMessage>) Class.forName(className);

		return BeanUtils.beanFromMap(clz, params);

	}

	public static WeChatMessage parseMessageAndReply(WeChatMessage receive)
			throws UnsupportedEncodingException, DocumentException {

		MessageType type = receive.getMsgType();

		WeChatMessage reply = null;

		switch (type) {
		case TEXT:

			TextReceive text = (TextReceive) receive;

			String content = text.getContent();

			String pattern = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";

			// 去掉微信默认表情
			content = content.replaceAll(pattern, "");

			if ("".equals(content)) {
				// 对方发的全是默认表情
				// 表情原样返回
				content = text.getContent();
			} else if ("【收到不支持的消息类型，暂无法显示】".equals(content)) {
				// 对方发送的是自定义表情图片
				content = "别发这个，我这收不到的";
			} else {
				// 是普通文本内容
				// 调用聊天机器人接口
				reply = getTulingResponseAndReply(content, text.getFromUserName(), text.getToUserName());
			}

			if (reply == null)
				reply = generateTextReply(text.getToUserName(), text.getFromUserName(), content);

			break;
		case IMAGE:
			ImageReceive image = (ImageReceive) receive;

			ImageReply ir = new ImageReply();
			ir.setFromUserName(image.getToUserName());
			ir.setToUserName(image.getFromUserName());
			ir.setCreateTime(getMessageCreateTime());
			Image img = ir.new Image();
			img.setMediaId(image.getMediaId());

			ir.setImage(img);

			reply = ir;
			break;

		case VOICE:

			VoiceReceive voice = (VoiceReceive) receive;
			// 语音识别结果
			String recognition = voice.getRecognition();
			if (!org.springframework.util.StringUtils.isEmpty(recognition)) {
				// 识别出了语音信息
				// 调用聊天机器人接口
				reply = getTulingResponseAndReply(recognition, voice.getFromUserName(), voice.getToUserName());
			} else {
				VoiceReply vr = new VoiceReply();
				vr.setFromUserName(voice.getToUserName());
				vr.setToUserName(voice.getFromUserName());
				vr.setCreateTime(getMessageCreateTime());

				Voice voi = vr.new Voice();
				voi.setMediaId(voice.getMediaId());

				vr.setVoice(voi);

				reply = vr;
			}
			break;
		case LOCATION:
			LocationReceive location = (LocationReceive) receive;

			reply = generateTextReply(location.getToUserName(), location.getFromUserName(),
					"已收到你的地址（" + location.getLabel() + "）");
			break;
		case VIDEO:
			VideoReceive video = (VideoReceive) receive;
			//
			// VideoReply vo = new VideoReply();
			// vo.setFromUserName(video.getToUserName());
			// vo.setToUserName(video.getFromUserName());
			// vo.setCreateTime(getMessageCreateTime());
			//
			// Video vod = vo.new Video();
			// vod.setMediaId(video.getMediaId());
			// vod.setTitle("你得视频");
			// vod.setDescription("你刚刚发的");
			//
			// vo.setVideo(vod);

			reply = generateTextReply(video.getToUserName(), video.getFromUserName(), "流量都没了发什么视频啊");
			break;
		case EVENT:

			WeChatEvent event = (WeChatEvent) receive;

			EventType eventType = event.getEvent();

			if (eventType == EventType.SUBSCRIBE) {
				reply = generateTextReply(event.getToUserName(), event.getFromUserName(), "你好啊，欢迎关注我，我们可以开始聊天了。");
			}

			break;
		default:
		}

		return reply;

	}

	public static TextReply generateTextReply(String from, String to, String content) {
		TextReply reply = new TextReply();
		reply.setFromUserName(from);
		reply.setToUserName(to);
		reply.setCreateTime(getMessageCreateTime());
		reply.setContent(content);

		return reply;
	}

	public static String getMessageCreateTime() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static WeChatMessage getTulingResponseAndReply(String content, String fromUserName, String toUserName)
			throws UnsupportedEncodingException {

		WeChatMessage reply = null;

		Map<String, Object> tulingResponse = TulingApiProcess.getChatAIResponse(content,
				fromUserName.replaceAll("_", ""));

		if (tulingResponse != null && !tulingResponse.isEmpty()) {
			String code = tulingResponse.get("code").toString();

			String urlKey = null;
			String titleKey = null;
			String picUrlKey = null;
			String descriptionKey = null;

			switch (code) {
			case "100000":
				// 文本类
				content = tulingResponse.get("text").toString();
				break;
			case "200000":
				// 链接类
				content = tulingResponse.get("text").toString() + " <a href='" + tulingResponse.get("url")
						+ "'>打开链接</a>";
				break;
			case "302000":
				// 新闻类
				urlKey = "detailurl";
				titleKey = "article";
				picUrlKey = "icon";
				descriptionKey = "source";

			case "308000":
				// 菜谱类

				if (urlKey == null) {
					urlKey = "detailurl";
					titleKey = "name";
					picUrlKey = "icon";
					descriptionKey = "info";
				}

				@SuppressWarnings("unchecked")
				List<Map<String, String>> newsList = (List<Map<String, String>>) tulingResponse.get("list");

				Collections.shuffle(newsList);

				if (newsList.size() > 8) {
					newsList = newsList.subList(0, 8);
				}

				List<Item> newsItems = new ArrayList<>();

				NewsReply news = new NewsReply();

				for (Map<String, String> newsMap : newsList) {
					Item item = news.new Item();

					item.setDescription(newsMap.get(descriptionKey));
					item.setPicUrl(newsMap.get(picUrlKey));
					item.setTitle(newsMap.get(titleKey));
					item.setUrl(newsMap.get(urlKey));

					newsItems.add(item);
				}

				news.setFromUserName(toUserName);
				news.setToUserName(fromUserName);
				news.setCreateTime(getMessageCreateTime());
				news.setArticleCount("" + newsList.size());
				news.setArticles(newsItems);

				reply = news;
				break;
			}

			if (reply == null) {
				// 返回纯文本消息
				reply = generateTextReply(toUserName, fromUserName, content);
			}

		}
		return reply;
	}

	public static void main(String[] args) throws DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedEncodingException {
		// {MsgId=6388265220498273458,
		// FromUserName=oFwZsw_zDjIHYFBbATwlsproGO3A, CreateTime=1487383903,
		// Label=新城区韩森寨街道咸宁中路东方社区31小区旁, Scale=16, Location_X=34.251282,
		// ToUserName=gh_fb60111b11a1, Location_Y=109.014420, MsgType=location}
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("MsgId", "6388265220498273458");
		params.put("FromUserName", "oFwZsw_zDjIHYFBbATwlsproGO3A");
		params.put("CreateTime", "1487383903");
		params.put("Label", "新城区韩森寨街道咸宁中路东方社区31小区旁");
		params.put("Scale", "16");
		params.put("Location_X", "34.251282");
		params.put("ToUserName", "gh_fb60111b11a1");
		params.put("Location_Y", "109.014420");
		params.put("MsgType", "location");

		WeChatMessage receive = parseMapToMessage(params);

		System.out.println(receive);

		WeChatMessage reply = parseMessageAndReply(receive);

		System.out.println(reply);
	}
}
