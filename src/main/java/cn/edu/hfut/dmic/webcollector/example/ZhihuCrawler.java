package cn.edu.hfut.dmic.webcollector.example;

import java.io.IOException;
import java.util.regex.Pattern;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

public class ZhihuCrawler extends BreadthCrawler{  
    
    public ZhihuCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
	
	}

	
   
  
    public static void main(String[] args) throws Exception{    
        ZhihuCrawler crawler=new ZhihuCrawler("ssasa", true);  
        crawler.addSeed("http://www.zhihu.com/question/21003086");  
        crawler.addRegex("http://www.zhihu.com/.*");  
        crawler.start(5);    
    }

	@Override
	public void visit(Page page, Links nextLinks) {
		
        String question_regex="^http://www.zhihu.com/question/[0-9]+";           
        if(Pattern.matches(question_regex, page.getUrl())){                
            System.out.println("processing "+page.getUrl());  
  
            /*extract title of the page*/  
            String title=page.getDoc().title();  
            System.out.println(title);  
  
            /*extract the content of question*/  
            String question=page.getDoc().select("div[id=zh-question-detail]").text();  
            System.out.println(question);  
  
        }  
    
		
	}  
}  
