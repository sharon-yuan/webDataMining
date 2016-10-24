import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testtest {
	public static void main(String []args){
		String regEx = "page_index=[0-9]+&";
		String s = "http://search.ccgp.gov.cn/dataB.jsp?searchtype=1&page_index=1234567890&bidSort=2&buyerName=&projectId=&pinMu=0&bidType=1&dbselect=bidx&kw=&start_time=2014%3A01%3A01&end_time=2016%3A06%3A30&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(s);
		boolean rs = mat.find();
		for(int i=0;i<=mat.groupCount();i++){
			String resultString=mat.group(i);
			System.out.println(resultString.substring("page_index=".length(),resultString.indexOf('&')));
		}
	}

}
