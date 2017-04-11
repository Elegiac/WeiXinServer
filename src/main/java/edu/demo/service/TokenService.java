package edu.demo.service;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.demo.utils.HttpUtil;
import edu.demo.utils.HttpUtil.HttpRep;
import edu.demo.utils.JsonUtil;

@Service
public class TokenService {
	private static final Logger log = LoggerFactory.getLogger(TokenService.class);

	private String accessToken;

	@Value("${access_token_url}")
	private String access_token_url;
	@Value("${app_id}")
	private String app_id;
	@Value("${app_secret}")
	private String app_secret;

	@PostConstruct
	public void init() {
		// accessToken();
	}

	private void accessToken() {

		HttpRep rep = HttpUtil.doGet(String.format(access_token_url, app_id, app_secret));

		Map<String, Object> result = JsonUtil.json2Map(rep.getContent());

		accessToken = result.get("access_token").toString();

		int expires_in = Integer.parseInt(result.get("expires_in").toString());

		log.info("new token is " + accessToken + ",The next call will take place after " + expires_in + " seconds");

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				accessToken();
			}
		}, expires_in * 1000l);
	}

	public String getToken() {
		return accessToken;
	}
}