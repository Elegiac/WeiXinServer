package edu.demo.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求
 * 
 * @author zy
 *
 */
public class HttpUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * http请求类型
	 * 
	 * @author zy
	 *
	 */
	private enum HttpMethod {
		GET, POST
	}

	/**
	 * http响应实体
	 * 
	 * @author zy
	 *
	 */
	public static class HttpRep {
		private Integer statusCode;
		private String content;

		public Integer getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(Integer statusCode) {
			this.statusCode = statusCode;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			return "HttpRep [statusCode=" + statusCode + ", content=" + content
					+ "]";
		}
	}

	/**
	 * 执行post请求
	 * 
	 * @param url
	 * @return
	 */
	public static HttpRep doPost(String url) {
		return doPost(null, url);
	}

	/**
	 * 执行post请求
	 * 
	 * @param messageBody
	 * @param url
	 * @return
	 */
	public static HttpRep doPost(String messageBody, String url) {
		return doHttp(HttpMethod.POST, url, messageBody);
	}

	/**
	 * 执行get请求
	 * 
	 * @param url
	 * @return
	 */
	public static HttpRep doGet(String url) {
		return doHttp(HttpMethod.GET, url, null);
	}

	private static HttpRep doHttp(HttpMethod method, String url,
			String messageBody) {
		HttpRep rep = new HttpRep();
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		HttpRequestBase request = null;
		try {
			httpClient = HttpClients.createDefault();
			if (method == HttpMethod.POST) {
				request = new HttpPost(url);

				if (messageBody != null && !"".equals(messageBody.trim())) {
					StringEntity stringEntity = new StringEntity(messageBody,
							"UTF-8");
					((HttpPost) request).setEntity(stringEntity);
				}
			} else if (method == HttpMethod.GET) {
				request = new HttpGet(url);
			} else {
				throw new IllegalArgumentException("method not supported");
			}

			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(5000).setConnectTimeout(5000).build();
			// 设置请求和传输超时时间
			request.setConfig(requestConfig);

			httpResponse = httpClient.execute(request);

			// 获取响应码
			StatusLine sl = httpResponse.getStatusLine();

			int statusCode = sl.getStatusCode();

			rep.setStatusCode(statusCode);

			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				rep.setContent(EntityUtils.toString(entity, "utf-8"));
			}
			return rep;
		} catch (Exception e) {
			log.error("http请求发送失败", e);
			return null;
		} finally {
			try {
				if (httpResponse != null)
					httpResponse.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(doGet("http://www.baidu.com"));
	}
}
