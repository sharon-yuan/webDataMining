import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import KNN.TestKNN;

public class Classify {

	public static void main(String[] args) {

		/*
		 * compareKeys.getSingleKeyfile(
		 * "D:/sharon/done/sichuan/05424C8E6A85F19709B5C95395FEF3CC-tf-tf.txt");
		 * System.out.println(TestKNN.getAns(
		 * "D:/sharon/knn/webpageInfo/tempans.txt"));
		 */

		int sum = 0, svm = 0;
		File dir = new File("D:/sharon/done/sczf/sichuan-no/");
		File[] files = dir.listFiles();

		for (File tempfile : files) {
			if (tempfile.getName().contains("Merge"))
				continue;
			if (tempfile.getName().endsWith("-tf")) {
				sum++;
				if (getAns(tempfile.getPath()) == 1) {
					printFile(tempfile.getPath().substring(0, tempfile.getPath().indexOf("-tf")));
					System.out.println(svm++ + " " + sum);

				}

			}
		}
		System.out.println(svm + " " + sum + " " + (double) svm / (double) sum);
	}

	private static void printFile(String filePath) {
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath)), "utf-8"));

			String line;
			while ((line = input.readLine()) != null)
				System.out.println(line);
			input.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	public static int getAns(String filePath) {
		File aFile = new File(filePath);
		String keycodefilePath = "D:/sharon/knn/webpageInfo/forknn/" + aFile.getName();
		compareKeys.getSingleKeyfile(filePath, keycodefilePath);
		return TestKNN.getAns(keycodefilePath);

	}

}
