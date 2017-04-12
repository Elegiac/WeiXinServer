package edu.demo.controller;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import edu.demo.bean.message.WeChatMessage;
import edu.demo.service.TokenService;
import edu.demo.service.WeChatMessageService;
import edu.demo.utils.XmlUtils;

@Controller
@RequestMapping("wechat")
public class AuthController {

	String url = "http://endding.tunnel.qydev.com/WeiXinServer/wechat";

	@Value("${token}")
	private String token;

	@Value("${encoding_AES_Key}")
	private String encodingAESKey;

	@Value("${app_id}")
	private String appId;

	@Resource
	private TokenService tokenService;

	@Resource
	private WeChatMessageService weChatMessageService;

	private static final Logger log = LoggerFactory
			.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String wechatGet(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr) throws Exception {
		log.info("signature=" + signature + ",timestamp=" + timestamp
				+ ",nonce=" + nonce + ",echostr=" + echostr);

		// signature=4976af8d838cbdd4c3341646a489830ab8438dd5,timestamp=1486707690,nonce=813815797,echostr=719619426885819387

		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

		String[] arr = { token, timestamp, nonce };

		Arrays.sort(arr);

		String localSignature = DigestUtils.sha1Hex(Arrays.toString(arr)
				.replaceAll("(\\[)|(\\])|(\\,)|(\\W)", ""));

		if (localSignature.equals(signature)) {
			return echostr;
		}

		return null;

	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/xml; charset=utf-8")
	@ResponseBody
	public String wechatPost(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String context,
			@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("openid") String openid,
			@RequestParam("encrypt_type") String encrypt_type,
			@RequestParam("msg_signature") String msg_signature) {
		try {

			// log.info("接收消息密文: " + context);

			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAESKey, appId);

			context = pc.decryptMsg(msg_signature, timestamp, nonce, context);
			// log.info("接收消息明文: " + context);

			Map<String, Object> params = XmlUtils.parseSimpleXml2Map(context);

			log.info(params.toString());

			WeChatMessage receive = weChatMessageService
					.parseMapToMessage(params);

			log.info(receive.toString());

			WeChatMessage reply = weChatMessageService
					.parseMessageAndReply(receive);

			log.info(reply.toString());

			String result = XmlUtils.generateSimpleXmlFromObject(reply);
			// log.info("返回消息明文：" + result);

			result = pc.encryptMsg(result, timestamp, nonce);

			// log.info("返回消息密文：" + result);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "success";
		}
	}
}
