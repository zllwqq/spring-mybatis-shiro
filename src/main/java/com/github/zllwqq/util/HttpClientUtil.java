package com.github.zllwqq.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpClientUtil {
	
	private static final Logger logger = LogManager.getLogger(HttpClientUtil.class);
	
	private static String defaultCharset = "utf-8";
	
	/**
	 * 执行http get请求
	 * @param url 
	 * @param responseCharset 返回信息编码，默认编码为utf-8
	 * @return
	 */
	public static String httpGet(String url, String responseCharset) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info(url);
			if (statusCode == 200) {
				String responseContent = EntityUtils.toString(entity, responseCharset == null ? defaultCharset : responseCharset);
				logger.info(responseContent);
				return responseContent;
			}
		} catch (IOException e) {
			logger.error("执行HttpGet出错", e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.error("关闭HttpClient出错", e);
			}
		}
		return null;
	}
	
	/**
	 * 执行带参数http get请求
	 * @param url
	 * @param paramMap
	 * @param requestCharset 请求参数编码
	 * @param responseCharset 返回信息编码
	 * @return
	 */
	public static String httpGet(String url, Map<String, String> paramMap, String requestCharset, String responseCharset) {
        return httpGet(addParameters(url, paramMap, requestCharset), responseCharset);
    }
	
	public static String httpGet(String url) {
		return httpGet(url, null);
	}
	
	public static void httpPost(String url, Map<String, Object> params) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post= new HttpPost(url);
		
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String temp : params.keySet()) {
			list.add(new BasicNameValuePair(temp, params.get(temp).toString()));
		}
		
		try {
			post.setEntity(new UrlEncodedFormEntity(list));
			HttpResponse response = client.execute(post);
			response.getEntity();
		} catch (IOException e) {
			logger.error("执行HttpPost出错", e);
		}
	}
	
	public static String addParameters(String url, Map<String, String> paramMap, String charset) {
		if (null != paramMap) {
			List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
			for (String temp : paramMap.keySet()) {
				queryParams.add(new BasicNameValuePair(temp, paramMap.get(temp)));
			}
			return url+"?"+URLEncodedUtils.format(queryParams, charset);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String url = "http://203.81.21.13/send/gsend.asp";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("name", "yaoper");
		paramMap.put("pwd", "yaoper8");
		paramMap.put("dst", "13323712806,1378");
		paramMap.put("msg", "源来汉语如此简单");
		HttpClientUtil.httpGet(url, paramMap, "gb2312", "gb2312");
	}
}
