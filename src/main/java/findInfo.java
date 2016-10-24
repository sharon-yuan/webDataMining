
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 
 * 从文本中得到结构化数据 
 * 
 * 开标时间 or发布时间
 * 
 * 地点
 * 
 * 招标人
 * 
 * 部门
 * 
 * 
 * @author Sharon
 * */
public class findInfo {
	static List<ArrayList<String[]>> Loactions =null;
	private static String RexTime = "(\\d{4} ?年 ?\\d{2} ?月 ?\\d{2} ?日)|(\\d{4} ?- ?\\d{2} ?- ?\\d{2})";

	public static List<String> saveIntoSQL(String filePath) {
		List<ArrayList<String>> TAP = findTimeAndPerson(filePath);
		List<String> info = new ArrayList<String>();
		info.add("" + Math.round(Math.random() * 100000));
		// uid url creattime deadline keywords location
		String tempURL = TAP.get(2).get(0);
		info.add(tempURL);

		if (TAP.get(0).size() >= 2) {

			java.sql.Date creatTime = Fstring2Long(TAP.get(0).get(0));

			java.sql.Date deadline = Fstring2Long(TAP.get(0).get(0));
			if (deadline.compareTo(creatTime) < 0) {
				java.sql.Date temp = deadline;
				deadline = creatTime;
				creatTime = temp;
			}
			for (int iTap = 2; iTap < TAP.get(0).size(); iTap++) {// get
																	// creatTima
																	// &
																	// deadline
				java.sql.Date temp = Fstring2Long(TAP.get(0).get(iTap));
				if (deadline.compareTo(temp) < 0) {
					deadline = temp;
					continue;
				}
				if (creatTime.compareTo(temp) > 0) {
					creatTime = temp;
					continue;
				}

			}
			info.add(creatTime.toString());
			info.add(deadline.toString());
		} else {
			if (TAP.get(0).size() == 1) {
				java.sql.Date date = Fstring2Long(TAP.get(0).get(0));

				info.add(date.toString());
				info.add(date.toString());
			} else {
				info.add("1991-1-1");
				info.add("1991-1-1");
			}
		}
		// tap----1st->times 2nd->person 3rd->URL 4th->svm
		// info------ Uid URL CTIME DTIME KEYWORDS LOCATION SVM 8department
		if (findLocation(filePath) == null)
			return null;
		//info.add(findLocation(filePath)[0]);// 5 keywords
		info.add(" ");// 5 keywords
		info.add(findLocation(filePath)[1]);// 6location
		info.add(TAP.get(3).get(0));// 7svm
		if (TAP.get(1).size() != 0)
			info.add(TAP.get(1).get(0));// 8department
		else {
			info.add("");
		}

		//SARJDBC.insertIntoInfo(info);
		return info;

	}
	public static void main(String[] args) throws IOException {

		String filepath = "D:/sharon/done/sczf/sichuan-all";
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:/sharon/done/aaa.txt")),"utf-8"));
		
		File dir = new File(filepath);
		File[] files = dir.listFiles();
		int sum=0,svm=0;
		for (File tempF : files) {
			
			if (tempF.getName().endsWith("-seg")) {
				File tempfile=new File(tempF.getPath().substring(0, tempF.getPath().indexOf("-seg"))+"-tf");
				if(!tempfile.exists())continue;
				int aaaaa=Classify.getAns(tempF.getPath().substring(0, tempF.getPath().indexOf("-seg"))+"-tf");
				if(aaaaa==1){
					svm++;sum++;
					System.out.println(svm+" "+sum);
				}
				else {
					sum++;
				}
				List<String>templist=saveIntoSQL(tempF.getPath().substring(0, tempF.getPath().indexOf("-seg")));
				for(String a:templist){
					
					output.write(a+";");
				}
				output.write(aaaaa+" "+'\n');
				
			}
			
		}
		output.close();
	}

	public static List<ArrayList<String>> findTimeAndPerson(String filePath) {
		// 1st->times 2nd->person 3rd->URL 4th->svm
		List<ArrayList<String>> TAP = new ArrayList<ArrayList<String>>();

		ArrayList<String> times = new ArrayList<String>();

		ArrayList<String> persons = new ArrayList<String>();

		ArrayList<String> URL = new ArrayList<String>();
		ArrayList<String> svm = new ArrayList<String>();
		

		String tempLine;
		try {
			@SuppressWarnings("resource")
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath  )), "utf-8"));
			while ((tempLine = input.readLine()) != null) {
				if (URL.size() == 0) {
					URL.add(tempLine);
					//System.out.println(tempLine);
				}
				Pattern pat = Pattern.compile(RexTime);
				Pattern patForPerson = Pattern.compile("采购人(名称)?(:|：| )[\u4E00-\u9FA5]{0,50}"),
						patForPerson2 = Pattern.compile("(受[\u4E00-\u9FA5]+委托)"),
						patForPersonPrefix = Pattern.compile("采购人(名称)?(:| |：)?");
				Matcher matForPerson = patForPerson.matcher(tempLine), matForPerson2 = patForPerson2.matcher(tempLine);
				Matcher mat = pat.matcher(tempLine);

				while (mat.find()) {
					// System.out.println("_-_-_-_-_------>"+mat.group().toString());
					if (times.isEmpty() || !times.contains(mat.group()))
						times.add(mat.group());
				}
				ArrayList<Integer> indexsss = new ArrayList<Integer>();
				if (matForPerson.find()) {
					Matcher matForPersonPrefix = patForPersonPrefix.matcher(matForPerson.group());
					while (matForPersonPrefix.find()) {
						indexsss.add(matForPersonPrefix.start());
						indexsss.add(matForPersonPrefix.end());
					}
					if (indexsss.size() > 2)
						persons.add(matForPerson.group().substring(indexsss.get(1), indexsss.get(2)));
					else
						persons.add(matForPerson.group().substring(indexsss.get(1)));
				}
				while (matForPerson2.find()) {
					String tempString = matForPerson2.group().substring(1, matForPerson2.group().length() - 2);
					if (tempString.matches("采购人(的?)")) {
						tempLine = "";
						continue;
					}
					if (tempString.matches(".*的"))
						tempString = tempString.substring(0, tempString.length() - 1);

					persons.add(tempString);

				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		URL.add("1111");
		TAP.add(times);// 1st->times 2nd->person 3rd->URL 4th->svm
		TAP.add(persons);// 2nd->person
		TAP.add(URL);// 3rd->URL
		int tempsvm = Classify.getAns(filePath);
		
		svm.add(tempsvm + "");
		TAP.add(svm);// 4th->svm
		// System.out.println("size---"+TAP.size());
		return TAP;
	}

	public static String[] findLocation(String filePath) {
		String[] anStrings = new String[3];
		String content = "";
		if(Loactions==null)
		 Loactions = SARJDBC.getFromCity();
		HashMap<String, String> cityName = new HashMap<String, String>();

		String tempLine;
		for (int i = 0; i < Loactions.size(); i++) {

			for (int j = 0; j < Loactions.get(i).size(); j++) {
				String key = Loactions.get(i).get(j)[0], val = Loactions.get(i).get(j)[1];
				if (!cityName.containsKey(key))
					cityName.put(key, val);
				else {
					String tempString = cityName.get(key);
					cityName.put(key, val + " " + tempString);
				}

			}
		}
		
		String keyWord = null;
		
		try {
			@SuppressWarnings("resource")
			BufferedReader input3 = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath + "-seg")), "utf-8"));
			int indexcont = 0;
			while ((tempLine = input3.readLine()) != null) {
				if ((indexcont++) <= 50) {

					content += tempLine.split(" ")[0] + " ";
				}
				
			}

			// anStrings[2] is the info about classify result
		
				anStrings[2] = Classify.getAns(filePath) + "";
			

			ArrayList<String> val = null;
			@SuppressWarnings("resource")
			BufferedReader input2 = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath + "-seg")), "utf-8"));

			while ((tempLine = input2.readLine()) != null) {

				String key = tempLine.split(" ")[0];
				if (cityName.containsKey(key)) {
					if (keyWord == null) {
						keyWord = key;
						for (String atempString : cityName.get(key).split(" ")) {
							val = new ArrayList<String>();
							val.add(atempString);
						}
						if (val.size() == 1) {// 县或者市对应唯一super
							// System.out.println(key+" "+cityName.get(key));
							anStrings[0] = content;
							anStrings[1] = key + " " + cityName.get(key);
							return anStrings;
						}

					} else {
						for (String aTempString : val) {
							if (aTempString.equals(key))// super做为key出现
							{
								// System.out.println(keyWord+" "+key);
								anStrings[0] = content;
								anStrings[1] = keyWord + " " + key;
								return anStrings;

							}

						}
						for (String aTempString : cityName.get(key).split(" ")) {
							if (aTempString.equals(keyWord))// key的super为最高频地点
							{
								// System.out.println(key+" "+keyWord);
								anStrings[0] = content;
								anStrings[1] = key + " " + keyWord;
								return anStrings;

							}
						}
						anStrings[0] = content;
						anStrings[1] = keyWord;
						return anStrings;

					}

				}
			}
			input2.close();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		anStrings[0] = content;
		anStrings[1] = keyWord;
		return anStrings;

	}

	public static void findPerson(String filePath) {

	}

	private static java.sql.Date Fstring2Long(String aString) {
		//System.out.println(aString);

		aString = aString.replaceAll("[\u4E00-\u9FA5]", "-").replaceAll("\\s*", "");
		if (aString.endsWith("-"))
			aString = aString.substring(0, aString.length() - 1);

		java.text.SimpleDateFormat formatter22 = new SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat formatter11 = new SimpleDateFormat("yyyy-M-d");
		java.text.SimpleDateFormat formatter12 = new SimpleDateFormat("yyyy-M-dd");
		java.text.SimpleDateFormat formatter21 = new SimpleDateFormat("yyyy-MM-d");

		java.util.Date qwertty;
		try {
			if (aString.matches("\\d{4}-\\d{2}-\\d{2}"))
				qwertty = formatter22.parse(aString);
			else if (aString.matches("\\d{4}-\\d{1}-\\d{2}"))
				qwertty = formatter12.parse(aString);
			else if (aString.matches("\\d{4}-\\d{2}-\\d{1}"))
				qwertty = formatter21.parse(aString);
			else if (aString.matches("\\d{4}-\\d{1}-\\d{1}"))
				qwertty = formatter11.parse(aString);
			else
				return null;
			java.sql.Date dateJava = new java.sql.Date(qwertty.getTime());
			//System.out.println("dateSQL " + dateJava.toString());
			return dateJava;
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;

	}
}
