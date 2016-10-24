
import java.io.File;
import java.util.List;

import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;


public class LexicalAnalyzer {
	public static void main(String args[]) {
		
		String filePath="D:/sharon/crawler/wpCrawlerResult1234/";
		getAllfileFre(filePath);
		}
	private static void getAllfileFre(String filePath) {
		File dir=new File(filePath);
		File []files=dir.listFiles();
		for(File f:files){
			if(f.getName().endsWith("-seg.txt")|f.getName().contains("Merged"))continue;
			else{
				System.out.println(f.getName());
				
				String path=f.getPath().substring(0, f.getPath().indexOf(".txt"));
				wordSegFromFile(path);
				
			}
		}
		
		
	}

	public static void getAns(String className) {
		

		String path = "C:/Users/wangsy/Desktop/biding/content" + className + "/";
		File dir=new File(path);
		 File[] fs = dir.listFiles();
		for(File file:fs) {
			System.out.println(file.getName());
			
			if(file.getPath().contains("-seg.txt")) continue;
			if(file.getPath().endsWith("-tf.txt")) continue;
			wordSegFromFile(file.getPath().substring(0, file.getPath().indexOf(".txt")));
			
		}
		
		fileMerge.getAns(className);

	}

	public static void wordSegFromFile(String filePath) {
File input=new File(filePath + ".txt");
File output=new File(filePath + "-seg.txt");

WordFrequencyStatistics wordFrequencyStatistics = new WordFrequencyStatistics("word-frequency-statistics.txt",SegmentationAlgorithm.MaxNgramScore);

wordFrequencyStatistics.setRemoveStopWord(true);


	
			//WordSegmenter.segWithStopWords(input,output);
			//WordFrequencyStatistics.getWordFre(filePath + ".txt", filePath + "-seg.txt");
			try {
				wordFrequencyStatistics.seg(input, new File("text-seg-result.txt"));
				
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			wordFrequencyStatistics.dump(filePath + "-seg.txt");
		
	}

	public static void wordSegFromString(String inputString) {
		List<Word> words = WordSegmenter.seg(inputString);
		for(Word a:words){
			if (a.getText().matches("[^\u4E00-\u9FA5]+"))words.remove(a);
		}
		
	}
}
