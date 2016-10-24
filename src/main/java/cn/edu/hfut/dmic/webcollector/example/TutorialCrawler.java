/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cn.edu.hfut.dmic.webcollector.example;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.example.util.JDBCHelper;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import cn.edu.hfut.dmic.webcollector.net.RandomProxyGenerator;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.mortbay.html.Link;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * WebCollector
 *
 * 
 * @author hu
 */
public class TutorialCrawler extends DeepCrawler {

    RegexRule regexRule = new RegexRule();

    JdbcTemplate jdbcTemplate = null;

    public TutorialCrawler(String crawlPath) {
        super(crawlPath);

      
        regexRule.addRule("-.*jpg.*");

    }

    @Override
    public Links visitAndGetNextLinks(Page page) {
        Document doc = page.getDoc();
        String title = doc.title();
        System.out.println("URL:" + page.getUrl() + "  title:" + title);

       
        if (jdbcTemplate != null) {
            int updates=jdbcTemplate.update("insert into tb_content (title,url,html) value(?,?,?)",
                    title, page.getUrl(), page.getHtml());
            if(updates==1){
                System.out.println("mysql");
            }
        }

        Links nextLinks = new Links();

      
        nextLinks.addAllFromDocument(doc, regexRule);

       
        Links ans=new Links();
        for(String a:nextLinks){
        	System.out.println("------>>>>>>"+a);
        	if(a.startsWith("/")){System.out.println("--->>>>"+htmlRoot(page.getUrl())+a);
        	ans.add(htmlRoot(page.getUrl())+a);
        	}
        	
        }
        return nextLinks;
        
    }
    public static String htmlSuper(String aString) {
		String regEx = "/[^/]+$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(aString.substring(0, aString.length() - 1));

		if (mat.find()) {
			aString = aString.substring(0, mat.start() + 1);
		}
		return aString;
	}

	public static String htmlRoot(String aString) {
		String regEx = "^(https?://)?[a-z\\.-]*/";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(aString);

		if (mat.find()) {
			aString = aString.substring(mat.start(), mat.end());
		}
		return aString;
	}
	
    public static void main(String[] args) throws Exception {
       
        TutorialCrawler crawler = new TutorialCrawler("sichuan");
        crawler.setThreads(10);
        for(int i=1;i<=22;i++)
        crawler.addSeed("http://www.kuaidaili.com/free/inha/"+i);
        crawler.setResumable(false);


    
       
        HttpRequesterImpl requester=(HttpRequesterImpl) crawler.getHttpRequester();    
       
       	  
		  
		  RandomProxyGenerator proxyGenerator = new RandomProxyGenerator() {

	          
	            @Override
	            public void markGood(Proxy proxy, String url) {
	                InetSocketAddress address = (InetSocketAddress) proxy.address();
	                System.out.println("Good Proxy:" + address.toString() + "   " + url);
	            }

	         
	            @Override
	            public void markBad(Proxy proxy, String url) {
	                InetSocketAddress address = (InetSocketAddress) proxy.address();
	                System.out.println("Bad Proxy:" + address.toString() + "   " + url);

	                
	            }

	
		  };
       
        requester.setProxyGenerator(proxyGenerator);
     


  
        crawler.setResumable(false);

        crawler.start(5);
    }

}
