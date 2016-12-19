
package KNN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * KNN算法测试类
 * 
 * @author Rowen
 * @qq 443773264
 * @mail luowen3405@163.com
 * @blog blog.csdn.net/luowen3405
 * @data 2011.03.25
 */
public class TestKNN {

	/**
	 * 从数据文件中读取数据
	 * 
	 * @param datas
	 *            存储数据的集合对象
	 * @param path
	 *            数据文件的路径
	 */
	public void read(List<List<Double>> datas, String path) {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String data = br.readLine();
			List<Double> l = null;
			while (data != null) {
				String t[] = data.split(" ");
				l = new ArrayList<Double>();
				for (int i = 0; i < t.length; i++) {
					l.add(Double.parseDouble(t[i]));
				}
				datas.add(l);
				data = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getAns(String testFilePath) {

		TestKNN testKnnIns = new TestKNN();
		String datafile = "D:/sharon/done/sczf/data";
		try {
			List<List<Double>> datas = new ArrayList<List<Double>>();
			List<List<Double>> testDatas = new ArrayList<List<Double>>();
			testKnnIns.read(datas, datafile);
			testKnnIns.read(testDatas, testFilePath);
			List<Double> test = testDatas.get(0);			
			KNN knn = new KNN();
			return (int) Math.round(Double.valueOf(knn.knn(datas, test,10)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * 程序执行入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getAns("tempans.txt"));
		//oneFileMutilRow("D:/sharon/done/sczf/data2", "D:/sharon/done/sczf/aaaa.txt");
	}

	public static void oneFileMutilRow(String datafilePath, String testfilePath) {

		int wroT = 0;
		TestKNN testKnnIns = new TestKNN();
		try {
			List<List<Double>> tempdatas = new ArrayList<List<Double>>();
			List<List<Double>> testDatas = new ArrayList<List<Double>>();
			testKnnIns.read(tempdatas, datafilePath);
			testKnnIns.read(testDatas, testfilePath);
			List<Double> result = new ArrayList<Double>();
			final List<List<Double>> datas = tempdatas;
			if(testDatas.get(0).size()>=datas.get(0).size())
			for (int i = 0; i < testDatas.size(); i++) {
				result.add(testDatas.get(i).get(testDatas.get(i).size() - 1));
				testDatas.get(i).remove(testDatas.get(i).get(testDatas.get(i).size() - 1));
			}
			KNN knn = new KNN();
			for (int i = 0; i < testDatas.size(); i++) {
				List<Double> test = testDatas.get(i);
				
				int tempResult=10;
				if(result.size()>0)
				tempResult = (int) Math.round(result.get(i));
				int tempAns = (int) Math.round(Double.valueOf(knn.knn( datas, test, 10)));
				System.out.println(i + " 实际类别为: " + tempResult + "   判定为:" + tempAns);

				if (tempAns != tempResult) {
					wroT++;
					System.out.println(wroT + "次错误");
				}
			}
			System.err.println("误判率" + (double) wroT / (double) testDatas.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}