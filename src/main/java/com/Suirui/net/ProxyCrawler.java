package com.Suirui.net;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.net.RandomProxyGenerator;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *use muti-proxy to crawler data from website
 *
 *override visitAndGetNextLinks to add links to queue
 *
 * @author sharon.w
 */
public  class ProxyCrawler extends BreadthCrawler {
public String filePath;
	AtomicInteger id = new AtomicInteger(0);

	/**
	 * @param crawlPath exp->/sharon/crawler/pCrawler
	 *            crawlPath is the path of the directory which maintains
	 *            information of this crawler
	 * @param seedsURL
	 *
	 * @param regexs
	 */
	public ProxyCrawler(String crawlPath,List<String>seedsURL,List<String>regexs) {
		
		super(crawlPath,true);
		filePath="E:/data"+crawlPath;
		

		for(String seed:seedsURL){
			this.addSeed(seed);
		
		}
		if(regexs!=null)
		for(String regex:regexs){
			this.addRegex(regex);
		}
		HttpRequesterImpl requester = (HttpRequesterImpl) this.getHttpRequester();
		for (int i = 1; i <= 30; i++) {

			try {
				addProxy("http://www.kuaidaili.com/free/inha/" + i + "/", proxyGenerator);
					addProxy("http://www.kuaidaili.com/free/ouha/" + i + "/", proxyGenerator);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		requester.setProxyGenerator(proxyGenerator);
	}

	
	

	/**
	 * 
	 *
	 * @param url
	 * 
	 * @param proxyGenerator
	 * 
	 * @throws Exception
	 */
	public static void addProxy(String url, RandomProxyGenerator proxyGenerator) throws Exception {
		// webcrawler need 2.07+
		@SuppressWarnings("unused")
		HttpRequest request = new HttpRequest(url);
		int thecaseNum=2;
		if(thecaseNum==1){
		List<String>proxyUrls=new ArrayList<>();
		List<Integer>ports=new ArrayList<>();
		proxyUrls.add("117.63.161.95");ports.add(8888);
		proxyUrls.add("220.249.21.222");ports.add(8118);
		proxyUrls.add("124.88.67.30");ports.add(80);
		proxyUrls.add("122.96.59.106");ports.add(843);
		proxyUrls.add("222.59.161.12");ports.add(8118);
		proxyUrls.add("222.82.222.242");ports.add(9999);
		proxyUrls.add("122.96.59.106");ports.add(83);
		
		
		
		proxyUrls.add("122.5.24.100");ports.add(9529);
		for(int j=0;j<proxyUrls.size();j++){
			proxyGenerator.addProxy(proxyUrls.get(j),ports.get(j));
		}
		}
		else if(thecaseNum==2)
		for (int i = 0; i <= 3; i++) {

			
			String proxyFilePath="aaaproxy.txt";
			List<String[]> proxyList=ProxyReader.getproxy(proxyFilePath);
			for(int j=0;j<proxyList.size();j++){
				proxyGenerator.addProxy(proxyList.get(j)[0],Integer.valueOf(proxyList.get(j)[1]));
			}
		
			
			try {

				Document doc = Jsoup.connect(url).get();
				org.jsoup.select.Elements IP = doc.getElementsByAttributeValue("data-title", "IP");
				org.jsoup.select.Elements PORT = doc.getElementsByAttributeValue("data-title", "PORT");

				if (IP.size() == PORT.size()) {
					for (int index = 0; index < IP.size(); index++) {
						proxyGenerator.addProxy(IP.get(index).text(), Integer.valueOf(PORT.get(index).text()));

						System.out.println("addPorxy: IP" + IP.get(index).text() + " Port" + PORT.get(index).text());
					}

				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		
			
	}
		else if(thecaseNum==3){
			
			String proxyFilePath="aaaproxy.txt";
			List<String[]> proxyList=ProxyReader.getproxy(proxyFilePath);
			for(int j=0;j<proxyList.size();j++){
				proxyGenerator.addProxy(proxyList.get(j)[0],Integer.valueOf(proxyList.get(j)[1]));
			}
		}

	}
	public static RandomProxyGenerator proxyGenerator = new RandomProxyGenerator() {

		@Override
		public void markGood(Proxy proxy, String url) {
			proxyGenerator.addProxy(proxy);
			InetSocketAddress address = (InetSocketAddress) proxy.address();
			System.out.println("--------->>>>Good Proxy:" + address.toString() + "   " + url);
		proxyController.addGoodProxyToDir(address.toString());
		}

		@Override
		public void markBad(Proxy proxy, String url) {
			InetSocketAddress address = (InetSocketAddress) proxy.address();
		//System.out.println("--------->>>>Bad Proxy:" + address.toString() + "   " + url);
			
			removeProxy(proxy);
			if(this.getSize()<=5)
			{
				List<String[]> proxyList=proxyController.readerProxyFromDir();
				
				for(String[]tempProxy:proxyList){
					
					this.addProxy(tempProxy[0],Integer.valueOf(tempProxy[1]));
				}
			}

		}

	};

	
		@Override
		public void visit(Page page, Links nextLinks) {
			
			Document document=Jsoup.parse(page.getHtml());			
			File filedir = new File(filePath);
			 filedir.mkdirs();
			File file=new File(filePath+MD5Util.MD5(document.data()+" "+page.getUrl())); 
			Elements elements=document.getElementsByAttribute("href");
			Links links = new Links();
			//System.out.println("-----links-----------"+this.threads+"----"+elements.size());
			for(Element aElement:elements){				
				links.add(GetUrl.addSupandHref(aElement.attr("href"),page.getUrl()));	
				
			//	System.out.println(GetUrl.addSupandHref(aElement.attr("href"),page.getUrl()));
			}
			
			//System.out.println("-----links-end----------"+this.threads+"----"+elements.size());
			
			nextLinks.addAll(links);
			if(file.exists())return;
			BufferIOFile.save(filePath+MD5Util.MD5(document.data()+" "+page.getUrl()),page.getUrl()+'\n'+document.text());
			
	
		}
/*
	public static void main(String[] args) throws Exception {
		
			List<String>seedsURL=new ArrayList<>(),regexs=new ArrayList<>();
			for (int i = 0; i < 22; i++)
				seedsURL.add(
						"http://www.sczfcg.com/CmsNewsController.do?method=recommendBulletinList&moreType=provincebuyBulletinMore&channelCode=shiji_cggg1&rp=25&page="
								+ i);
			
		ProxyCrawler crawler = new ProxyCrawler("/sharon/crawler/crawl_dazhong/", seedsURL, null);

		crawler.setThreads(50);
		crawler.setTopN(100);

	
		crawler.setTopN(100000);

		// crawler.setResumable(true);
		// start crawl with depth of 4 
		crawler.start(4);
	

	}*/

	

}