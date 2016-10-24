package com.Suirui.net;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;

public class PostCrawler extends ProxyCrawler {

	public PostCrawler(String crawlPath,List<String> seedsURL,List<String> regexs) {
		super(crawlPath,seedsURL,regexs);
		
		
	}

	
	public static void main(String[] args) throws Exception {
		String url = "http://www.cdggzy.com:8147/newsite/Notice/ListHandler.ashx?form=CgList&action=GetGG&classid=2&typeid=1&QX=";
		List<String> listURL=new ArrayList<String>();
		for(int i=1;i<136;i++){
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageindex", i+"");
		params.put("pagesize", "25");
		listURL.addAll(HttpXmlClient.getLinksFromPostRequest(url, params));
		}
		ProxyCrawler crawler = new PostCrawler("/sharon/crawler/wpCrawler/",listURL,null);

		crawler.setThreads(50);
		

		HttpRequesterImpl requester = (HttpRequesterImpl) crawler.getHttpRequester();
		for (int i = 20; i <= 35; i++) {

			addProxy("http://www.kuaidaili.com/free/inha/" + i + "/", proxyGenerator);
		}
		requester.setProxyGenerator(proxyGenerator);
		crawler.setTopN(100000);

		crawler.start(4);

	}

	

}
