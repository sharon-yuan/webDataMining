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

package cn.edu.hfut.dmic.webcollector.model;

import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.util.CharsetDetector;
import java.io.UnsupportedEncodingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class Page{
    
    public static final Logger LOG=LoggerFactory.getLogger(Page.class);
    
    private HttpResponse response=null;
    private String url=null;  
    private String html=null;
    private Document doc=null;  
  
    public byte[] getContent() {
        if(response==null)
            return null;
        return response.getContent();
    }

    
    public String getUrl() {
        return url;
    }

  
    public void setUrl(String url) {
        this.url = url;
    }

   
    public String getHtml() {
        if(html!=null){
            return html;
        }
        if(getContent()==null){
            return null;
        }
        String charset=CharsetDetector.guessEncoding(getContent());
        try {
            this.html = new String(getContent(),charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            LOG.info("Exception",ex);
            return null;
        }       
    }

  
    public void setHtml(String html) {
        this.html = html;
    }

   
    public Document getDoc() {
        if(doc!=null){
            return doc;
        }
        try{
            
            this.doc=Jsoup.parse(getHtml(),url);
            return doc;
        }catch(Exception ex){
            LOG.info("Exception",ex);
            return null;
        }
        
    }
    

    
    

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    

   
    
}
