package com.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;


public class pagePostRequest {

    public static final String ADD_URL = "http://www.cdggzy.com:8147/newsite/Notice/CgIndex1.aspx?classid=2&typeid=1";

    public static void appadd() {

        try {
           
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
          
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            String request="pageindex=4&pagesize=10";
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Length", request.length()+"");
            connection.setRequestProperty("Host", "www.cdggzy.com:8147");
            connection.setRequestProperty("Referer", "http://www.cdggzy.com:8147/newsite/Notice/GGList.ashx?classid=2&typeid=1");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
         
            connection.addRequestProperty("action", "GetGG");
            connection.addRequestProperty("classid", "2");
            connection.addRequestProperty("form", "CgList");
            connection.addRequestProperty("typeid", "1");
          
           
            connection.connect();

       
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
           
            obj.append("pageindex", "4");
            obj.append("pagesize", "10");
           
            out.write(obj.toString().getBytes("utf-8"));
            out.flush();
            out.close();

         
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
          
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        appadd();
    }

}