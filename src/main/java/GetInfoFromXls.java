
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class GetInfoFromXls {

	public static void loadFileInfo(String xlsPath, String name) throws IOException {

		FileInputStream fileIn = new FileInputStream(xlsPath);
		// ����ָ�����ļ�����������Excel�Ӷ�����Workbook����

		Workbook wb0 = new HSSFWorkbook(fileIn);
		// ��ȡExcel�ĵ��еĵ�һ����
		Sheet sht0 = wb0.getSheetAt(0);
		// ��Sheet�е�ÿһ�н��е���

		String filePath = "C:/Users/wangsy/Desktop/biding/content" + name + "/";

		int nameInt = 0;
		try {
			for (Row r : sht0) {

				@SuppressWarnings("unused")
				String str = new String(); // ԭ��txt����
				String s1 = new String();// ���ݸ���
				File f = new File(filePath + (nameInt++) + ".txt");
				if (!f.exists()) {
					f.createNewFile();// �������򴴽�
				}
				/*
				 * ��Ҫ����ԭ�ļ�
				 * 
				 * BufferedReader input = new BufferedReader(new
				 * InputStreamReader(new FileInputStream(f),"utf-8"));
				 * 
				 * 
				 * while ((str = input.readLine()) != null) { s1 += new
				 * String((str + "\n")); System.err.println(str); }
				 * System.err.println(s1.length()); input.close();
				 */
				for (int i = 0; i < 4 && r.getCell(i) != null; i++) {
					s1 += (r.getCell(i).toString() + " ");
				}
				BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
				output.write(s1);
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		//wb0.close();
	}

	public static void main(String []args) {
		String path = "C:/Users/wangsy/Desktop/biding/";
		String className = "ForHebei";
		String fileName = "chinaBiding" + className + ".xls";
		try {
			loadFileInfo(path + fileName, className);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		System.out.println("creat xls");

	}

	public static void getAns(String className) {
		String path = "C:/Users/wangsy/Desktop/biding/";
		String fileName = "chinaBiding" + className + ".xls";
		try {
			loadFileInfo(path + fileName, className);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		System.out.println("creat xls");

	}
}