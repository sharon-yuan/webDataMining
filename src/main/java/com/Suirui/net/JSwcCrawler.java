package com.Suirui.net;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

public class JSwcCrawler extends ProxyCrawler{

	public JSwcCrawler(String crawlPath,List<String> seedsURL,List<String> regexs) {
		super(crawlPath, seedsURL, regexs);
		
		
	}
	
	@Override
	public void visit(Page page, Links nextLinks) {
		String jsAns=PageUtils.getPhantomJSDriver(page);
		Document document=Jsoup.parse(page.getHtml());
		System.out.println(document.text());/*
		if(jsAns.equals("Unable to post!"))
		document=Jsoup.parse(page.getHtml());
		else document=Jsoup.parse(PageUtils.getPhantomJSDriver(page));
		
		File filedir = new File(filePath);
		  filedir.mkdirs();
		File file=new File(filePath+MD5Util.MD5(document.data()+" "+page.getUrl())); 
		if(file.exists())return;
		BufferIOFile.save(filePath+MD5Util.MD5(document.data()+" "+page.getUrl()),document.text());
		Elements elements=document.getElementsByAttribute("href");
		Links links = new Links();
		
		for(Element aElement:elements){				
			links.add(GetUrl.addSupandHref(aElement.attr("href"),page.getUrl()));	
		}
		nextLinks.addAll(links);
	*/
		System.out.println(document.text());
}
	
	

	
	public static void main(String []args) throws Exception{
		
		List<String> seedsURL=new ArrayList<>(),regexs=new ArrayList<>();
		//seedsURL.add("http://ggzy.luzhou.gov.cn/ceinwz/WebInfo_List.aspx?newsid=0&jsgc=0100000000&zfcg=&tdjy=&cqjy=&PubDateSort=0&ShowPre=0&CbsZgys=&zbfs=&qxxx=&showqxname=1&NewsShowPre=1&wsjj=&showCgr=0&ShowOverDate=&FromUrl=jsgc");
		seedsURL.add("http://www.sczfcg.com/DistrictController.do?method=getSubDistricts&objId=510000");
		JSwcCrawler crawler=new JSwcCrawler("/sharon/crawler/jswcC/", seedsURL, regexs);
		crawler.setThreads(1);
		crawler.start(4);
	}
	
	}


