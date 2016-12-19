

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class choseKeyWords {
	class Node {
		public String word;
		public double freq;

		Node(String word, double freq) {
			this.word = word;
			this.freq = freq;

		}
	}

	public static Map<String, Double> contentYesMap = new HashMap<String, Double>();
	public static Map<String, Double> contentNoMap = new HashMap<String, Double>();
	public static List<ArrayList<String>> contentAns = new ArrayList<ArrayList<String>>();

	
	//v1.0 
	/*public static void main(String[] args) {

		String path = "D:/sharon/done/sczf/";
		getListFromFile(path + "sichuan-yes/", contentYesMap);
		getListFromFile(path + "sichuan-no/", contentNoMap);
		
		Iterator<Map.Entry<String, Double>> iter = contentYesMap.entrySet().iterator();

		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = iter.next();
			String key = entry.getKey().toString();
			double val = Double.valueOf(entry.getValue().toString());
			if (contentNoMap.containsKey(key)) {
				val = val - contentNoMap.get(key);
			}
			Jug(key, val);
			

		}
		
		BufferedWriter output;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path+"chosedKeyForSichuan.txt")),"utf-8"));
			for(int i=0;i<contentAns.size();i++){
				output.write(contentAns.get(i).get(0)+'\n');
				
			}	
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
System.out.println("get autoKey. Saved in " +path);
	}
	*/
	
	public static void main(String[] args) {

		String path = "E:/data/china";
		getListFromFile(path + "ChinaForYes-TF/", contentYesMap);
		getListFromFile(path + "ChinaForNo-TF/", contentNoMap);
		 Map<String, Double> contentAnsMap = contentYesMap;
//gothough contentnomap, calu the value=yes.value-no.value
		Iterator<Map.Entry<String, Double>> iter = contentNoMap.entrySet().iterator();
	while (iter.hasNext()) {
			Entry<String, Double> entry = iter.next();
			String key = entry.getKey().toString();
			double val = -1.0*Double.valueOf(entry.getValue().toString());
			if (contentAnsMap.containsKey(key)) {
				val += contentYesMap.get(key);
			}
			contentAnsMap.put(key, val);
		}
	//gothough contentansMap 
		iter=contentAnsMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Double> entry = iter.next();
			String key = entry.getKey().toString();
			double val = Double.valueOf(entry.getValue().toString());
			Jug(key, val,(int)Math.round(0.02*contentAnsMap.size()));
		}
		BufferedWriter output;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("chosedKeyForChina.txt")),"utf-8"));
			for(int i=0;i<contentAns.size();i++){
				output.write(contentAns.get(i).get(0)+'\n');
				
			}	
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
System.out.println("get autoKey. Saved in " +path);
	}
	public static void getAns(String className){
//ClassName=>contentForSuirui    contentForYes

		String path = "C:/Users/wangsy/Desktop/biding/";
		getListFromFile(path + className, contentYesMap);
		getListFromFile(path + "contentForNo/", contentNoMap);
		
		Iterator<Entry<String, Double>> iter = contentYesMap.entrySet().iterator();

		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			double val = Double.valueOf(entry.getValue().toString());
			if (contentNoMap.containsKey(key)) {
				val = val - contentNoMap.get(key);
			}
			Jug(key, val);
			
		}
		
		BufferedWriter output;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path+"chosedKeyForSuirui.txt")),"utf-8"));
			for(int i=0;i<contentAns.size();i++){
				output.write(contentAns.get(i).get(0)+'\n');
				
			}	
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
System.out.println("get autoKey. Saved in " +path);
	
	}
//
	private static void Jug(String key, double val) {
		if(key.length()=="是".length())return;
		String []stopwords={"省","市","县","州","区","局","厅","第","次","级","一","二","三","四","五","六","七","八","九","十","百","千","万"};
		for(String aString:stopwords){
			if(key.contains(aString)) return;
		}
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(key);
		keys.add(val + "");
		if (contentAns.isEmpty()) {
			contentAns.add(keys);
			
			return;
		}
		int i;
		for(i=0;i<contentAns.size()&&(Math.abs(val)>Math.abs(Double.valueOf(contentAns.get(i).get(1))));){
			i++;
		}
		if(i>0)contentAns.add(i, keys);		
		while(contentAns.size()>210)contentAns.remove(0);

	}
	private static void Jug(String key, double val,int size) {
		if(key.length()=="是".length())return;
		String []stopwords={"省","市","县","州","区","局","厅","第","次","级","一","二","三","四","五","六","七","八","九","十","百","千","万"};
		for(String aString:stopwords){
			if(key.contains(aString)) return;
		}
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(key);
		keys.add(val + "");
		if (contentAns.isEmpty()) {
			contentAns.add(keys);
			
			return;
		}
		int i;
		/*
		for(i=0;i<contentAns.size()&&(Math.abs(val)>Math.abs(Double.valueOf(contentAns.get(i).get(1))));){
			i++;
		}
		if(i>0)contentAns.add(i, keys);		*/
		for(i=0;i<contentAns.size();i++){
			double tempdouble=Double.valueOf(contentAns.get(i).get(1));
			if(tempdouble<0) tempdouble=tempdouble*(-10);
			if(val<0){
				val=val*(-0.1);
			}
			if(val<tempdouble)break;
		}
		if(i>0)contentAns.add(i, keys);
		while(contentAns.size()>size)contentAns.remove(0);

	}
	private static void getListFromFile(String path, Map<String, Double> contentMap) {
		String str = "";
		BufferedReader input;

		try {
			input = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(path + "Merged-tf")), "utf-8"));
			while ((str = input.readLine()) != null) {
				String[] tempStrings = str.split(" ");
				contentMap.put(tempStrings[0], Double.valueOf(tempStrings[1]));
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
