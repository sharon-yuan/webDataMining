 package com.Suirui.net;

import java.util.ArrayList;
import java.util.List;

public class WebProxyCrawler extends ProxyCrawler{

	public WebProxyCrawler(String crawlPath,List<String> seeds,List<String> regexs) {
		super(crawlPath, seeds, regexs);
	
		
	}

	
	
	
	public static void main(String[] args) throws Exception {
		List<String>seedsURL=new ArrayList<>(),regexs=new ArrayList<>();
	/*for (int i = 1; i < 22; i++)
			seedsURL.add(
					"http://www.sczfcg.com/CmsNewsController.do?method=recommendBulletinList&moreType=provincebuyBulletinMore&channelCode=shiji_cggg1&rp=25&page="
							+ i);
		for (int i = 1; i < 900; i++)
			seedsURL.add(
					"http://www.sczfcg.com/CmsNewsController.do?method=recommendBulletinList&moreType=provincebuyBulletinMore&channelCode=shiji_cggg&rp=25&page="
							+ i);
		for (int i = 1; i < 47; i++)
			seedsURL.add(
					"http://www.sczfcg.com/CmsNewsController.do?method=recommendBulletinList&moreType=provincebuyBulletinMore&channelCode=cggg&rp=25&page="
							+ i);
		for (int i = 1; i < 25; i++)
			seedsURL.add(
					"http://www.sczfcg.com/CmsNewsController.do?method=recommendBulletinList&moreType=provincebuyBulletinMore&channelCode=cgygg&rp=25&page="
							+ i);*/
		
		/*for(int i=1;i<23;i++)
			seedsURL.add("http://www.sczfcg.com/CmsNewsController.do?method=search&chnlCodes=&city=&town=&cityText=&townText=&searchKey=\u89C6\u9891\u4F1A\u8BAE&distin=&type=&title=&beginDate=&endDate=&str1=&str2=&pageSize=10&curPage="+i+"&searchResultForm=search_result_anhui.ftl");
		*/
			//String aurl="http://www.sczfcg.com/CmsNewsController.do?method=search&chnlCodes=&city=&town=&cityText=&townText=&searchKey=\u89C6\u9891\u4F1A\u8BAE&distin=&type=&title=&beginDate=&endDate=&str1=&str2=&pageSize=10&curPage=1&searchResultForm=search_result_anhui.ftl";
		/*int size=38529;
		for(int i=1;i<10;i++)
			seedsURL.add("http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index="+i+"&dbselect=infox&kw=&buyerName=&projectId=&start_time=2014%3A01%3A01&end_time=2016%3A07%3A22&timeType=6&bidSort=0&pinMu=0&bidType=1&displayZone=&zoneId=&pppStatus=&agentName=");
		*/
		
		String urlString="http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index=4&dbselect=infox&kw=&buyerName=&projectId=&start_time=2014%3A01%3A01&end_time=2016%3A07%3A22&timeType=6&bidSort=0&pinMu=0&bidType=1&displayZone=&zoneId=&pppStatus=&agentName=";
		String zhongyanggk="http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index=1&bidSort=1&buyerName=&projectId=&pinMu=0&bidType=1&dbselect=bidx&kw=&start_time=2014%3A01%3A01&end_time=2016%3A07%3A22&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=";
		int zygkSize=1168;
		
		String difangggggk="http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index=1&bidSort=2&buyerName=&projectId=&pinMu=0&bidType=1&dbselect=bidx&kw=&start_time=2014%3A01%3A01&end_time=2016%3A07%3A22&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=";
		int dfgkSize=37363;
		
		String difanxunjia="http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index=1&bidSort=2&buyerName=&projectId=&pinMu=0&bidType=2&dbselect=bidx&kw=&start_time=2014%3A01%3A01&end_time=2016%3A07%3A22&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=";
		String zhongxunjia="http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index=1&bidSort=1&buyerName=&projectId=&pinMu=0&bidType=2&dbselect=bidx&kw=&start_time=2014%3A01%3A01&end_time=2016%3A07%3A22&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=";
		
		
		String HTMLdfxj="http://www.ccgp.gov.cn/cggg/dfgg/xjgg/201607/t20160701_6987013.htm";
		String HTMLzygk="http://www.ccgp.gov.cn/cggg/zygg/gkzb/201607/t20160722_7084212.htm";

		String tempDFGK="http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index=1&bidSort=2&buyerName=&projectId=&pinMu=0&bidType=1&dbselect=bidx&kw=&start_time=2014%3A01%3A01&end_time=2016%3A06%3A30&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=";
		int sizeTEMPDFGK=35983;
	

		for(int i=1;i<sizeTEMPDFGK;i++){
		seedsURL.add("http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index="+i+"&bidSort=2&buyerName=&projectId=&pinMu=0&bidType=1&dbselect=bidx&kw=&start_time=2014%3A01%3A01&end_time=2016%3A06%3A30&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=");
		}
		regexs.add("http://www.ccgp.gov.cn/cggg/dfgg/gkzb/.?htm");
		

		
		
		regexs.add("http://www.sczfcg.com/view/staticpags/.*");

		// do not fetch jpg|png|gif 
		regexs.add("-.*\\.(jpg|png|gif|css)");
		// do not fetch url contains # 
		regexs.add("-.*#");
		
		
		WebProxyCrawler crawler=new WebProxyCrawler("/china/dfgk88FST/", seedsURL, regexs);
		crawler.setResumable(false);
		crawler.setThreads(50);
		crawler.start(2);
	}

}

