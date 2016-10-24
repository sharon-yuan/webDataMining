package com.Suirui.net;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

/*
 * JS爬取
 * Refer: http://blog.csdn.net/smilings/article/details/7395509
 */
public class WebCollector3 extends DeepCrawler {

	public WebCollector3(String crawlPath) {
		super(crawlPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		/*HtmlUnitDriver可以抽取JS生成的数据*/
//		HtmlUnitDriver driver=PageUtils.getDriver(page,BrowserVersion.CHROME);
//		String content = PageUtils.getPhantomJSDriver(page);
        WebDriver driver = PageUtils.getWebDriver(page);
//        List divInfos=driver.findElementsByCssSelector(#feed_content);
        List<WebElement> divInfos=driver.findElements(By.cssSelector("feed_content span"));
        for(WebElement divInfo:divInfos){
            System.out.println("Text:" + divInfo.getText());
        }
        return null;
	}
	
	public static void main(String[] args) {
		WebCollector3 crawler=new WebCollector3("/home/hu/data/wb");
        for(int page=1;page<=5;page++)
//        crawler.addSeed(http://www.sogou.com/web?query=+URLEncoder.encode(编程)+&page=+page);
        crawler.addSeed("http://cq.qq.com/baoliao/detail.htm?294064");
        try {
			crawler.start(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}