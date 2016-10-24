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

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import com.Suirui.net.GetUrl;
import com.Suirui.net.PageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @author hu
 */
public class DemoJSCrawler extends DeepCrawler{

    
    public DemoJSCrawler(String crawlPath) {
        super(crawlPath);
    }

    @Override
    public Links visitAndGetNextLinks(Page page) {
       
       Document document=Jsoup.parse(PageUtils.getPhantomJSDriver(page));
       Elements docElements=document.getAllElements();
       for(Element aa:docElements){
    	   String hrefString=aa.attr("href");
    	   if(hrefString!=null&&hrefString!="")
    	   System.out.println(GetUrl.addSupandHref(hrefString, page.getUrl()));
       }
    
        return null;
    }
    
    public static void main(String[] args) throws Exception{
        DemoJSCrawler crawler=new DemoJSCrawler("/home/hu/data/wb");
       crawler.setThreads(2);
        crawler.addSeed("http://ggzy.luzhou.gov.cn/ceinwz/WebInfo_List.aspx?newsid=0&jsgc=0100000000&zfcg=&tdjy=&cqjy=&PubDateSort=0&ShowPre=0&CbsZgys=&zbfs=&qxxx=&showqxname=1&NewsShowPre=1&wsjj=&showCgr=0&ShowOverDate=&FromUrl=jsgc");
        crawler.addForcedSeed("http://ggzy.luzhou.gov.cn/ceinwz/WebInfo_List.aspx?newsid=0&jsgc=0100000000&zfcg=&tdjy=&cqjy=&PubDateSort=0&ShowPre=0&CbsZgys=&zbfs=&qxxx=&showqxname=1&NewsShowPre=1&wsjj=&showCgr=0&ShowOverDate=&FromUrl=jsgc");
        crawler.start(1);
    }
    
    

    
}
