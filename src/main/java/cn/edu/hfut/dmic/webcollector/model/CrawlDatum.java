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

import com.sleepycat.je.DatabaseEntry;
import java.io.UnsupportedEncodingException;



public class CrawlDatum{
    
    public static final byte STATUS_DB_INJECTED=0x01;
    
    public static final byte STATUS_DB_UNFETCHED=0x04;
        public static final byte STATUS_DB_FETCHED=0x05;
    
    
    private String url;
    private byte status=CrawlDatum.STATUS_DB_INJECTED;
    private int retry=0;
  
    public DatabaseEntry getKey() throws UnsupportedEncodingException{
        return new DatabaseEntry(url.getBytes("utf-8"));
    }
    public DatabaseEntry getValue(){
        byte[] value=new byte[2];
        value[0]=status;
        value[1]=(byte) retry;
        return new DatabaseEntry(value);
    }
    
    public CrawlDatum(String url,byte status){
        this.url=url;
        this.status=status;
        this.retry=0;
    }
    
    public CrawlDatum(String url,byte status,int retry){
        this.url=url;
        this.status=status;
        this.retry=retry;
    }
    
    public CrawlDatum(DatabaseEntry key,DatabaseEntry value) throws UnsupportedEncodingException{
        this.url=new String(key.getData(),"utf-8");
        byte[] valueData=value.getData();
        this.status=valueData[0];
        this.retry=valueData[1];
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CrawlDatum(String url) {
        this.url = url;
    }

    public byte getStatus() {
        return status;
    }

   
    public void setStatus(byte status) {
        this.status = status;
    }

    public int getRetry() {

        return retry;
    }

    public void setRetry(int retry) {
    	
        this.retry = retry;
    }

    
}
