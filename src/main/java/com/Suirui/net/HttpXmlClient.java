
package com.Suirui.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SuppressWarnings("deprecation")
public class HttpXmlClient {
	private static Logger log = Logger.getLogger(HttpXmlClient.class);

	public static String post(String url, Map<String, String> params, Map<String, String> header) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params, header);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static JSONObject postJson(String url, JSONObject json) {
		@SuppressWarnings("resource")
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				@SuppressWarnings("unused")
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity());
				response = JSONObject.parseObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	public static String get(String url, Map<String, String> header) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpGet get = new HttpGet(url);

		if (header != null) {
			@SuppressWarnings("unused")
			List<NameValuePair> nvpsheader = new ArrayList<NameValuePair>();
			Set<String> keySetHeader = header.keySet();
			for (String key : keySetHeader) {
				get.addHeader(key, header.get(key));
			}
		}
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);

		String body = null;
		try {
			body = EntityUtils.toString(entity);
			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
		log.info("execute post...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params, Map<String, String> header) {

		HttpPost httpost = new HttpPost(url);

		if (header != null) {
			@SuppressWarnings("unused")
			List<NameValuePair> nvpsheader = new ArrayList<NameValuePair>();

			Set<String> keySetHeader = header.keySet();
			for (String key : keySetHeader) {
				// nvpsheader.add(new BasicNameValuePair(key, header.get(key)));
				httpost.addHeader(key, header.get(key));
			}
		}

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}

	public static List<String> getLinksFromPostRequest(String Url, Map<String, String> params) {

		org.json.JSONObject jsonResponse = new org.json.JSONObject(HttpXmlClient.post(Url, params, null));
		Document doc = Jsoup.parse(jsonResponse.get("showinfo").toString());

		Elements links = doc.getElementsByAttribute("href");
		List<String> urLists = new ArrayList<String>();
		for (Element link : links) {
			
			System.out.println(urLists.size());
			System.out.println(link);
			String aaaaaString = GetUrl.fromHref(link.toString(), Url);
			if(aaaaaString!=null)
			urLists.add(aaaaaString);
		}
		System.out.println(urLists);

		return urLists;

	}

	public static void main(String[] args) {

		String url = "http://ggzy.luzhou.gov.cn/ceinwz/WebInfo_List.aspx?newsid=0&jsgc=0100000000&zfcg=&tdjy=&cqjy=&PubDateSort=0&ShowPre=0&CbsZgys=&zbfs=&qxxx=&showqxname=1&NewsShowPre=1&wsjj=&showCgr=0&ShowOverDate=&FromUrl=jsgc";
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageindex", "9");
		params.put("pagesize", "25");
		getLinksFromPostRequest(url, params);

	}
}
