package com.Suirui.net;






import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import cn.edu.hfut.dmic.webcollector.model.Page;

public class PageUtils {
	public static HtmlUnitDriver getDriver(Page page) {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        driver.setJavascriptEnabled(true);
        driver.get(page.getUrl());
        return driver;
    }

    public static HtmlUnitDriver getDriver(Page page, BrowserVersion browserVersion) {
        HtmlUnitDriver driver = new HtmlUnitDriver(browserVersion);
        driver.setJavascriptEnabled(true);
        driver.get(page.getUrl());
    	return driver;
    }
    
    public static WebDriver getWebDriver(Page page) {
  	
    	System.setProperty("phantomjs.binary.path", "D:/phantomjs-2.1.1-windows/bin/phantomjs.exe");
    	WebDriver driver = new PhantomJSDriver();
    	driver.get(page.getUrl());
    	return driver;
    }
    
    public static String getPhantomJSDriver(Page page) {
    	Runtime rt = Runtime.getRuntime();
    	Process process = null;
    	try {
			process = rt.exec("D:/phantomjs-2.1.1-windows/bin/phantomjs.exe "  + 
			"D:/phantomjs-2.1.1-windows/bin/parser.js "  +
			page.getUrl().trim());
			InputStream in = process.getInputStream();
			InputStreamReader reader = new InputStreamReader(
					in, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sbf = new StringBuffer();
			String tmp ;
			while((tmp = br.readLine())!=null){    
                sbf.append(tmp);    
            }
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public static void main(String []args) {
		
	}
}