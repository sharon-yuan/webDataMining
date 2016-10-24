package com.Suirui.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProxyReader {
	public static void main(String []args){
		String filePath="proxy.txt";
		List<String[]> aList=getproxy(filePath);
		for(int i=0;i<aList.size();i++){
			System.out.println(aList.get(i)[0]+" "+aList.get(i)[1]);
		}
	}
	
	public static List<String[]> getproxy(String filePath){

		List<String[]> proxy=new ArrayList<>();
		
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath)), "utf-8"));
		String line;
		
			while((line=input.readLine())!=null){
				line=line.trim();
				if(line.contains("@")) line=line.split("@")[0];
				
				String []splitAns;
				if(line.contains(":"))
				splitAns=line.split(":");
				else if(line.contains('\t'+""))
					splitAns=line.split('\t'+"");
				else continue;
				if(splitAns.length!=2) continue;
				proxy.add(splitAns);
			}
			
			
		input.close();
		} catch ( IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	
	return proxy;
		
	}

}
