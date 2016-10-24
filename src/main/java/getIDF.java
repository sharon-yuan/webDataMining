

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class getIDF {
	public static void caluIDF(String className) {
		/*
		 * List<String[]> content = new ArrayList<>(); String str;
		 * BufferedReader input; try { input = new BufferedReader( new
		 * InputStreamReader(new FileInputStream(new File(filePath +
		 * "-seg.txt")), "utf-8"));
		 * 
		 * HashMap<String, Integer> contentHashMap = new HashMap<String,
		 * Integer>();
		 * 
		 * while ((str = input.readLine()) != null) { String[] strings = new
		 * String[2]; strings = str.split(" "); if
		 * (contentHashMap.containsKey(strings[0])) {
		 * contentHashMap.put(strings[0], contentHashMap.get(strings[0]) + 1); }
		 * else { contentHashMap.put(strings[0], 1); } }
		 * 
		 * 
		 * input.close(); } catch (UnsupportedEncodingException |
		 * FileNotFoundException e) { // TODO 自动生成的 catch 块 e.printStackTrace();
		 * } catch (NumberFormatException e) { // TODO 自动生成的 catch 块
		 * e.printStackTrace(); } catch (IOException e) { // TODO 自动生成的 catch 块
		 * e.printStackTrace(); }
		 */
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(className + "Merged-seg.txt")), "utf-8"));

			input.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}
