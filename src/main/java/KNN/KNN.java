
package KNN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.google.common.base.FinalizablePhantomReference;

/**
 * 
 * @author Sharon
 */
public class KNN {
	private static Map<Integer, Integer> wordsWeight = new HashMap<>();

	private Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
		public int compare(KNNNode o1, KNNNode o2) {
			if (o1.getDistance() > o2.getDistance()) {
				return -1;
			} else if (o1.getDistance() == o2.getDistance()) {
				return 0;
			} else
				return 1;
		}
	};

	/**
	 *
	 * @param k
	 * @param max
	 * @return
	 */
	public List<Integer> getRandKNum(int k, int max) {
		List<Integer> rand = new ArrayList<Integer>(k);
		for (int i = 0; i < k; i++) {
			int temp = (int) (Math.random() * max);
			if (!rand.contains(temp)) {
				rand.add(temp);
			} else {
				i--;
			}
		}
		return rand;
	}

	public double calDistance(List<Double> testList, List<Double> dataList) {
		List<Double> testFinal=testList;
		if(testList.size()==dataList.size())
			testFinal=testList.subList(0, testList.size()-1);
		else if((testList.size()+1)==dataList.size());
		else {
			System.err.println("testdata size error!");
		}
		double distance = 0.0;
		int times = 1;
		for (int i = 0; i < testFinal.size(); i++) {
			if (wordsWeight.containsKey(i)) {
				times = wordsWeight.get(i);
			} else
				times = 1;
			double tempdis = testFinal.get(i) - dataList.get(i);
			distance += Math.abs(testFinal.get(i) - dataList.get(i)) * times;

		}
		return distance;
	}

	private void getKeywordsWeight() throws Exception {
		BufferedReader inputData, keywordsData;

		inputData = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("chosedKeyForSichuan.txt")), "utf-8"));
		keywordsData = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File("keywords.txt")), "utf-8"));
		Map<String, Integer> wordandWeight = new HashMap<>();
		String line;
		while ((line = keywordsData.readLine()) != null) {
			String[] templines = line.split(" ");
			if (templines.length < 2)
				continue;
			wordandWeight.put(templines[0], Integer.valueOf(templines[1]));
		}
		int linenum = 0;
		while ((line = inputData.readLine()) != null) {
			if (wordandWeight.containsKey(line)) {
				wordsWeight.put(linenum, wordandWeight.get(line));
				// System.out.println("put wordweight into map:" +line+ "
				// "+linenum+" "+wordandWeight.get(line));
			}
			linenum++;
		}

		inputData.close();
		keywordsData.close();
	}

	
	public int knn(final List<List<Double>> datas, List<Double> testData, int k) {
		try {
			getKeywordsWeight();
		} catch (Exception e) {
		
			e.printStackTrace();
		}

		PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);
		List<Double> contrastData = datas.get(0);
		int dataClassNum = (int) Math.round(contrastData.get(contrastData.size() - 1));

		if (testData.size() > contrastData.size()) {
			System.err.println("targetData.size()>contrastData.size()" + testData.size() + " " + contrastData.size());
		}
		double distance= calDistance(testData, contrastData);
		pq.add(new KNNNode(0, distance, dataClassNum));
		double mindistcan = distance;
		for (int i = 1; i < datas.size(); i++) {
			KNNNode top = pq.peek();

			contrastData = datas.get(i);
			distance = calDistance(testData, contrastData);
			if (distance < mindistcan) {
				// System.out.println("min distcane changed! befor->" +
				// mindistcan + "after" + distance);
				mindistcan = distance;
			}

			if (pq.size() < k)
				pq.add(new KNNNode(i, distance, (int) Math.round(contrastData.get(contrastData.size() - 1))));
			else if (top == null) {
				System.err.println("queue is empty!");
			} else if (top.getDistance() > distance) {

				// System.err.println("top distance " + top.getDistance() + " "
				// + distance);
				if (pq.size() >= k)
					pq.remove();
				pq.add(new KNNNode(i, distance, (int) Math.round(contrastData.get(contrastData.size() - 1))));

			}
		}
		// System.out.println("pq size->" + pq.size());
		return getMostClass(pq);
	}

	private int getMostClass(PriorityQueue<KNNNode> pq) {
		Map<Integer, Integer> classCount = new HashMap<Integer, Integer>();

		for (int i = 0; i < pq.size(); i++) {

			KNNNode node = pq.remove();
			// System.out.println("distance->" + node.getDistance());
			int c = node.getC(), val = 1;
			if (c == 1) {
				// System.out.println(c);
				val = 3;
			}
			if (classCount.containsKey(c)) {
				classCount.put(c, classCount.get(c) + val);

			} else {
				classCount.put(c, val);
			}
			// System.out.println("the nearest class:" + c + " freq:" +
			// classCount.get(c));
		}
		int maxIndex = -1;
		int maxCount = 0;
		Object[] classes = classCount.keySet().toArray();
		for (int i = 0; i < classes.length; i++) {
			if (classCount.get(classes[i]) > maxCount) {
				maxIndex = i;
				maxCount = classCount.get(classes[i]);
			}
		}
		return Integer.valueOf(classes[maxIndex].toString());
	}
}