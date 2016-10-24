

public class getAns {
	public static void main(String [] args) throws Exception{
	
		String className = "ForC";
		//0-��ŷʽ�ռ�        1-��tfidf
		int type=1;
		@SuppressWarnings("unused")
		String Url="http://www.ccgp.gov.cn/cggg/dfgg/";
		//��url��ȡ�ļ�
		//UrlCrawler.getAns(Url,10000);
		//��xls�õ����ݵ��ļ�
		//GetInfoFromXls.getAns(className);
		//���ļ��ִʷ����Ӧ�ļ��в����merged�Լ���merged�Ĵ�Ƶͳ��
		LexicalAnalyzer.getAns(className);
		//LexicalAnalyzer.getAns("ForNo");
		//LexicalAnalyzer.getAns("ForHebei");
		//ת��������ֵ
		compareKeys.getAns(className,type);
		//����-seg�õ������ļ����ڵ��ļ���TFֵ
		freqToTF.getAns(className);
	}
	

}
