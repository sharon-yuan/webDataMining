
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
import java.util.List;
import java.util.Map;

public class compareKeys {
	private static List<String> keys;
	private static List<Double> result ;
	static List<String> stringList ;
	static List<Double> freqList ;
	static Map<String, Double> resultMap;

	public static void getCompareKey(String className, int type) {
		// @classname 1.ForYes 0.ForNo 3.ForHebei
		boolean forKNN = true;
		try {

			// String filepath = "D:/sharon/done/sichuan/";
			String filepath = "C:/Users/wangsy/Desktop/biding/content" + className;
			File dir = new File(filepath);
			File[] files = dir.listFiles();

			readKeyfile();
		
			File resultFile = new File("C:/Users/wangsy/Desktop/biding/" + className + type + ".txt");
			BufferedWriter output = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(resultFile), "utf-8"));
			for (File tempFile : files) {
				if (!tempFile.getPath().endsWith("-tf.txt"))
					continue;
				// 比较两个文件 并将结果数组输出到result

				if (resultFile.exists()) {
					// System.out.print("文件存在");
				} else {
					// System.out.print("文件不存在");
					resultFile.createNewFile();// 不存在则创建
				}

				BufferedReader input = new BufferedReader(
						new InputStreamReader(new FileInputStream(tempFile), "utf-8"));
				String line;
				stringList = new ArrayList<String>();
				while ((line = input.readLine()) != null) {

					String[] lineSplit = line.split(" ");
					if (lineSplit.length != 2)
						continue;
					stringList.add(lineSplit[0]);

					freqList.add(Double.valueOf(lineSplit[1]));
					resultMap.put(lineSplit[0],Double.valueOf(lineSplit[1]));
				}
				distance(stringList, type);
				if (forKNN) {
					// 写入result
					for (int index = 0; index < result.size(); index++) {
						output.write(result.get(index) + " ");
					}
					if (className == "ForYes")
						output.write("1");
					else // if (className == "ForNo")
						output.write("0");

				} else {
					if (className == "ForYes")
						output.write("1 ");
					else
						output.write("-1 ");
					for (int index = 0; index < result.size(); index++) {
						output.write(index + ":" + result.get(index) + " ");
					}
				}
				output.write('\n');
				input.close();

			}

			output.close();
			//System.out.println("将compareKey结果存到" + "C:/Users/wangsy/Desktop/biding/" + className + type + ".txt");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	// type==0 欧式距离
	// type ==1 tfidf
	private static void distance(List<String> key, int type) {
		result = new ArrayList<Double>();
		for (String aString : keys) {/*
			if (key.contains(aString)) {
				if (type == 1)
					result.add(freqList.get(keys.indexOf(aString)));
				else
					result.add(1.0);
			} else
				result.add(0.0);
		*/
			
		if(resultMap.containsKey(aString)){
			if (type == 1)
				result.add(resultMap.get(aString));
			else
				result.add(1.0);
		} else
			result.add(0.0);}
		
	}

	public static void saveCodeIntoFile(String inputFilePath, String outputFilePath) throws IOException {
		File file = new File(inputFilePath);
		File outputFile = new File(outputFilePath);
		// 比较两个文件 并将结果数组输出到result

		if (outputFile.exists()) {
			// System.out.print("文件存在");
		} else {
			// System.out.print("文件不存在");
			outputFile.createNewFile();// 不存在则创建
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf-8"));

		String line;
		stringList = new ArrayList<String>();
		while ((line = input.readLine()) != null) {

			String[] lineSplit = line.split(" ");
			if (lineSplit.length != 2)
				continue;
			stringList.add(lineSplit[0]);

			freqList.add(Double.valueOf(lineSplit[1]));
			resultMap.put(lineSplit[0],Double.valueOf(lineSplit[1]));
		}
		// 0 forTFIDF
		// 1 for 欧式距离

		int type = 1;
		distance(stringList, type);
		boolean forKNN = true;
		String className = "Forsichuan";
		if (forKNN) {
			// 写入result
			for (int index = 0; index < result.size(); index++) {
				output.write(result.get(index) + " ");
				System.out.println(result.get(index) + " ");
			}
			if (className == "ForYes")
				output.write("1");
			else // if (className == "ForNo")
				output.write("0");

		} else {
			if (className == "ForYes")
				output.write("1 ");
			else
				output.write("-1 ");
			for (int index = 0; index < result.size(); index++) {
				output.write(index + ":" + result.get(index) + " ");
			}
		}
		output.write('\n');
		input.close();
		output.close();

	}
	
	//将commonkeyForsuirui.txt中的内容存入程序 keys

	public static void readKeyfile() {
		readKeyfile("D:/sharon/done/sczf/chosedKeyForSichuan.txt");
	}
	public static void readKeyfile(String filePath) {
		keys=new ArrayList<>();
		// 得到commonkeys

		try {
			File commonkeys = new File(filePath);
			if (commonkeys.exists()) {

				BufferedReader input;
				input = new BufferedReader(new InputStreamReader(new FileInputStream(commonkeys), "utf-8"));
				String a;
				while ((a = input.readLine()) != null) {
					keys.add(a);
				}
				input.close();
			} else {
				System.err.println("commonkeys 不存在");
				return;
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	public static void getSingleKeyfile(String filePath){
		//1-->for yes 
		//0-->for no
		//else-->for test
		int dataType=3;

		// @classname 1.ForYes 0.ForNo 3.ForHebei
		boolean forKNN = true;
		try {

			// String filepath = "D:/sharon/done/sichuan/";
			//String filepath = "C:/Users/wangsy/Desktop/biding/content" + className;
			//File dir = new File(filepath);
			//File[] files = dir.listFiles();
			File file=new File(filePath); 

			readKeyfile();
			
			File resultFile = new File("D:/sharon/knn/webpageInfo/tempans.txt");
			BufferedWriter output = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(resultFile), "utf-8"));
			
				
				// 比较两个文件 并将结果数组输出到result

				if (resultFile.exists());
				else {
					resultFile.createNewFile();// 不存在则创建
				}

				BufferedReader input = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), "utf-8"));
				String line;
				stringList = new ArrayList<String>();
				while ((line = input.readLine()) != null) {

					String[] lineSplit = line.split(" ");
					if (lineSplit.length != 2)
						continue;
					stringList.add(lineSplit[0]);

					freqList.add(Double.valueOf(lineSplit[1]));
					resultMap.put(lineSplit[0],Double.valueOf(lineSplit[1]));
				}
				distance(stringList, 1);//1 for TFIDF
				if (forKNN) {
					// 写入result
					for (int index = 0; index < result.size(); index++) {
						output.write(result.get(index) + " ");
					}
					if (dataType == 1)
						output.write("1");
					else // if (dataType == 0)
						output.write("0");

				} else {
					if (dataType == 1)
						output.write("1 ");
					else
						output.write("-1 ");
					for (int index = 0; index < result.size(); index++) {
						output.write(index + ":" + result.get(index) + " ");
					}
				}
				output.write('\n');
				input.close();

			

			output.close();
			//System.out.println("将compareKey结果存到" + resultFile.getPath());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	
		
	}
	
	public static void getSingleKeyfile(String inputfilePath,String outputfilePath){
		//1-->for yes 
		//0-->for no
		//else-->for test
		int dataType=3;

		// @classname 1.ForYes 0.ForNo 3.ForHebei
		boolean forKNN = true;
		try {

			// String filepath = "D:/sharon/done/sichuan/";
			//String filepath = "C:/Users/wangsy/Desktop/biding/content" + className;
			//File dir = new File(filepath);
			//File[] files = dir.listFiles();
			File file=new File(inputfilePath); 

			readKeyfile();
			
			File resultFile = new File(outputfilePath);
			BufferedWriter output = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(resultFile), "utf-8"));
			
				
				// 比较两个文件 并将结果数组输出到result

				if (resultFile.exists());
				else {
					resultFile.createNewFile();// 不存在则创建
				}

				BufferedReader input = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), "utf-8"));
				String line;
				stringList = new ArrayList<String>();
				freqList = new ArrayList<>();
				resultMap=new HashMap<>();
				while ((line = input.readLine()) != null) {

					String[] lineSplit = line.split(" ");
					if (lineSplit.length != 2)
						continue;
					stringList.add(lineSplit[0]);

					freqList.add(Double.valueOf(lineSplit[1]));
					resultMap.put(lineSplit[0],Double.valueOf(lineSplit[1]));
				}
				distance(stringList, 1);//1 for TFIDF
				if (forKNN) {
					// 写入result
					for (int index = 0; index < result.size(); index++) {
						output.write(result.get(index) + " ");
					}
					if (dataType == 1)
						output.write("1");
					else // if (dataType == 0)
						output.write("0");

				} else {
					if (dataType == 1)
						output.write("1 ");
					else
						output.write("-1 ");
					for (int index = 0; index < result.size(); index++) {
						output.write(index + ":" + result.get(index) + " ");
					}
				}
				output.write('\n');
				input.close();

			

			output.close();
			//System.out.println("将compareKey结果存到" + resultFile.getPath());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	
		
	}
	public static void getWholeDir(String inputfilePath,String outputfilePath,int dataType){
		//1-->for yes 
		//0-->for no
		//else-->for test
		

		// @classname 1.ForYes 0.ForNo 3.ForHebei
		boolean forKNN = true;
		try {

			// String filepath = "D:/sharon/done/sichuan/";
			//String filepath = "C:/Users/wangsy/Desktop/biding/content" + className;
			//File dir = new File(filepath);
			//File[] files = dir.listFiles();
			File dir = new File(inputfilePath);
			File[] files = dir.listFiles();
			

			readKeyfile();
			
			File resultFile = new File(outputfilePath);
			BufferedWriter output = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(resultFile), "utf-8"));
			
			for(File file:files){
				if(!file.getName().endsWith("-tf"))continue;
				// 比较两个文件 并将结果数组输出到result
System.out.println(file.getPath());
				if (resultFile.exists());
				else {
					resultFile.createNewFile();// 不存在则创建
				}

				BufferedReader input = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), "utf-8"));
				String line;
				stringList = new ArrayList<String>();
				freqList = new ArrayList<>();
				resultMap=new HashMap<>();
				while ((line = input.readLine()) != null) {

					String[] lineSplit = line.split(" ");
					if (lineSplit.length != 2)
						continue;
					stringList.add(lineSplit[0]);

					freqList.add(Double.valueOf(lineSplit[1]));
					resultMap.put(lineSplit[0],Double.valueOf(lineSplit[1]));
				}
				System.out.println("stringlist"+stringList);
				distance(stringList, 1);//1 for TFIDF
				if (forKNN) {
					// 写入result
					for (int index = 0; index < result.size(); index++) {
						output.write(result.get(index) + " ");
					}
					if (dataType == 1)
						output.write("1");
					else // if (dataType == 0)
						output.write("0");

				} else {
					if (dataType == 1)
						output.write("1 ");
					else
						output.write("-1 ");
					for (int index = 0; index < result.size(); index++) {
						output.write(index + ":" + result.get(index) + " ");
					}
				}
				output.write('\n');
				input.close();

			
			}
			output.close();
			
			//System.out.println("将compareKey结果存到" + resultFile.getPath());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	
		
	
		
		
	}

	public static void main(String[] args) {
		//getCompareKey("ForNo", 1);
		// readKeyfile();
		/*
		 * String inputPath =
		 * "D:/sharon/done/sichuan/F0B2A2DFE3939C7CD0739A857077CEA2-tf-tf.txt",
		 * outputPath = "C:/Users/wangsy/Desktop/biding/sichuan/aaa.txt"; try {
		 * saveCodeIntoFile(inputPath,outputPath);
		 * 
		 * } catch (IOException e) { // TODO 自动生成的 catch 块 e.printStackTrace();
		 * }
		 */
		//getSingleKeyfile("D:/sharon/done/sichuan/F0B2A2DFE3939C7CD0739A857077CEA2-tf-tf.txt");
		
		getWholeDir("D:/sharon/done/sczf/sichuan-no", "D:/sharon/done/sczf/no",0);
	}

	public static void getAns(String className, int type) {
		getCompareKey(className, type);
	}
	
	

}
