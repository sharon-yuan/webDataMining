
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

public class freqToTF {

	public static void f2TF(String filePath) {
		System.out.println("freqToTF->f2TF"+filePath);
		try {
			List<String> contentList=new ArrayList<String>();
			List<Double> timesList=new ArrayList<Double>();
			String str;
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath)), "utf-8"));
			double sum=0.0;
			while ((str = input.readLine()) != null) {
				
				if(str.matches(".?[\u4E00-\u9FA5]+.? [0-9]?[0-9]?")) ;else {//System.out.println(str );
					continue;
				}
				
				String []strings=new String[2];
				strings=str.split(" ");
				contentList.add(strings[0]);
				timesList.add(Double.valueOf(strings[1]));
				sum+=Double.valueOf(strings[1]);
				
			}
					
			input.close();
			
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath+"-tf")),"utf-8"));
			for(int i=0;i<timesList.size();i++){
			
				output.write(contentList.get(i)+" "+(timesList.get(i)/sum)+'\n');
				
			}	
			output.close();
		}catch(

	UnsupportedEncodingException e)
	{

		e.printStackTrace();
	}catch(
	FileNotFoundException e)
	{

		e.printStackTrace();
	}catch(
	IOException e)
	{

		e.printStackTrace();
	}

	}

	public static void main(String[] args) {
		//String className = "ForC";

		// String filepath = "C:/Users/wangsy/Desktop/biding/content" +
		// className + "/";

		String filepath = "D:/sharon/done/sczf/sichuan-all/";

		File dir = new File(filepath);
		File[] files = dir.listFiles();
		for (File tempF : files) {
			if (tempF.getName().endsWith("-seg"))
				f2TF(tempF.getPath().substring(0, tempF.getPath().indexOf("-seg")));
		}

	}

	public static void getAns(String className) {

		int nameNumber = 0;
		String path = "C:/Users/wangsy/Desktop/biding/content" + className + "/";
		File file = new File(path + nameNumber + "-seg.txt");
		String filePath = path + (nameNumber);
		while (file.exists()) {

			f2TF(filePath);
			filePath = path + (nameNumber++);

			file = new File(filePath + "-seg.txt");
		}

		filePath = path + "Merged";
		f2TF(filePath);

	}
}
