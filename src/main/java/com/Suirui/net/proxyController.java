package com.Suirui.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class proxyController {
	public static void addGoodProxyToDir(String aProxy) {
		while(aProxy.startsWith("/")||aProxy.startsWith("\\")){
			aProxy=aProxy.substring(1);
		}

		BufferedWriter output;
		try {
			File tempF=new File(Config.PROXY_DIR + MD5Util.MD5(aProxy));
			if(tempF.exists()) return;
			output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(tempF), "utf-8"));
			output.write(aProxy);
			output.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<String[]> readerProxyFromDir() {
		List<String[]> ansList = new ArrayList<>();
		File proxyDir = new File(Config.PROXY_DIR);
		if (!proxyDir.isDirectory()) {
			System.err.println("proxy dir doesn't exist!");
			return null;
		}
		File[] filelist = proxyDir.listFiles();
		for (File tempF : filelist) {
			ansList.addAll(ProxyReader.getproxy(tempF.getPath()));
		}

		return ansList;
	}

}
