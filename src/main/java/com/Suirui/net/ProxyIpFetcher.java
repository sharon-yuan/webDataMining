
package com.Suirui.net;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProxyIpFetcher extends DeepCrawler {

	RegexRule regexRule = new RegexRule();

	JdbcTemplate jdbcTemplate = null;

	public ProxyIpFetcher(String crawlPath) {
		super(crawlPath);
	
		regexRule.addRule("-.*jpg.*");
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {

		try {
			org.jsoup.select.Elements IP = page.getDoc().getElementsByAttributeValue("data-title", "IP");
			org.jsoup.select.Elements PORT = page.getDoc().getElementsByAttributeValue("data-title", "PORT");
			BufferedWriter output = null;
			int index = (int) Math.round(Math.random() * 1000);
			output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("E:/proxy/goodProxy" + index + ".txt")), "utf-8"));
			if (IP.size() == PORT.size()) {
				for (int i = 0; i < IP.size(); i++) {
					System.out.println("IP=" + IP.get(i).text() + " PORT:" + PORT.get(i).text());
					output.write(IP.get(i).text() + " " + PORT.get(i).text() + '\n');
				}

			}
			output.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) throws Exception {
		/*
		 */
		ProxyIpFetcher crawler = new ProxyIpFetcher("/sichuan/");
		crawler.setThreads(1);
		for (int i = 1; i <= 22; i++)
			crawler.addSeed("http://www.kuaidaili.com/free/inha/" + i + "/");
		crawler.setResumable(false);

		crawler.setResumable(false);

		crawler.start(5);
	}

}
