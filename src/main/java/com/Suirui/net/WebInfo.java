package com.Suirui.net;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hfut.dmic.webcollector.crawler.Crawler;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;

public class WebInfo {
	public static void main(String[] args) throws Exception {

		List<String> seedsURL = new ArrayList<>(), regexs = new ArrayList<>();
		for (int i = 0; i < 22; i++)
			seedsURL.add(
					"http://www.sczfcg.com/CmsNewsController.do?method=recommendBulletinList&moreType=provincebuyBulletinMore&channelCode=shiji_cggg1&rp=25&page="
							+ i);
		regexs.add("http://www.sczfcg.com/view/staticpags/shiji_zqyj/.*");

		// do not fetch jpg|png|gif
		regexs.add("-.*\\.(jpg|png|gif).*");
		// do not fetch url contains #
		regexs.add("-.*#.*");
	Crawler crawler = new WebProxyCrawler("/sharon/crawler/wpCrawler/", seedsURL, regexs);
		crawler.setResumable(false);
		crawler.setThreads(3);
		crawler.setTopN(10);
		crawler.start(4);
		
	seedsURL = new ArrayList<>(); regexs = new ArrayList<>();
		seedsURL.add("http://ggzy.luzhou.gov.cn/ceinwz/WebInfo_List.aspx?newsid=0&jsgc=0100000000&zfcg=&tdjy=&cqjy=&PubDateSort=0&ShowPre=0&CbsZgys=&zbfs=&qxxx=&showqxname=1&NewsShowPre=1&wsjj=&showCgr=0&ShowOverDate=&FromUrl=jsgc");
		
		Crawler crawler2=new JSwcCrawler("/sharon/crawler/jswcC/", seedsURL, regexs);
		crawler2.setThreads(3);
		crawler2.setTopN(10);
		crawler2.start(4);
	}
}
