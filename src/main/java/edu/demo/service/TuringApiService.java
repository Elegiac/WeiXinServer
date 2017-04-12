package edu.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.demo.utils.Aes;
import edu.demo.utils.HttpUtil;
import edu.demo.utils.HttpUtil.HttpRep;
import edu.demo.utils.JsonUtil;

@Service
public class TuringApiService {
	@Value("${turing_api_key}")
	private String turing_api_key;
	@Value("${turing_api_secret}")
	private String turing_api_secret;
	@Value("${turing_api_url}")
	private String turing_api_url;

	public Map<String, Object> getChatAIResponse(String content, String userId)
			throws UnsupportedEncodingException {

		Map<String, String> params = new HashMap<>();

		params.put("key", turing_api_key);
		params.put("info", content);
		params.put("userid", userId);

		// 待加密的json数据
		String json = JsonUtil.object2JsonString(params);

		// 获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis());

		// 生成密钥
		String keyParam = turing_api_secret + timestamp + turing_api_key;
		String key = DigestUtils.md5Hex(keyParam);

		// 加密
		Aes mc = new Aes(key);
		String data = mc.encrypt(json);

		params.clear();
		params.put("key", turing_api_key);
		params.put("timestamp", timestamp);
		params.put("data", data);

		HttpRep rep = HttpUtil.doPost(JsonUtil.object2JsonString(params),
				turing_api_url);

		if (rep != null && rep.getStatusCode() == 200) {
			String result = rep.getContent();

			Map<String, Object> map = JsonUtil.json2Map(result);

			return map;

		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			InterruptedException {

		// System.out.println(getChatAIResponse("乌拉", "121"));

		// Map<String, String> params = new HashMap<>();
		//
		// params.put("key", apiKey);
		// params.put("info", "最近热门电影");
		// params.put("userid", "1");
		//
		// String json = JsonUtil.object2JsonString(params);
		//
		// HttpRep rep = HttpUtil.doPost(json, url);
		//
		// System.out.println(rep.getContent());
		// String[] keys = {};
		// String begin = "hellow";
		// int index = 0;
		// while (true) {
		// System.out.println("机器人" + (index + 1) + "说：" + begin);
		//
		// begin = sendAndResponse(keys[index], begin);
		//
		// if (index == 0) {
		// index = 1;
		// } else {
		// index = 0;
		// }
		//
		// Thread.sleep(1000);
		// }

	}
}
