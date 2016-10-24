
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class getAnsFromBaidu {
    public getAnsFromBaidu() {
    }

    public static void main(String[] args) {

        try {
            
          
           /*String account="78ea4278-7914-4a6a-8f89-f5628daae4a6";
           String bingKey="eG/eqWEbpfwXsKEn60O4ImPreMQGODy0T/rjGg+Dr2Y";
           
         ODataConsumer c = ODataConsumers
       		    .newBuilder("https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/")
        		    .setClientBehaviors(OClientBehaviors.basicAuth(account, bingKey))
       		    .build();
          String query = URLEncoder.encode("'视频会议'", "gb2312");
          OQueryRequest<OEntity> oRequest = c.getEntities("Web").custom("Query", query);

       		Enumerable<OEntity> entities = oRequest.execute();
        		System.out.println(entities.count());
       
        		Document doc = Jsoup.connect("https://api.datamarket.azure.com/Bing/Search/Web?Query=%27Xbox%27&$top=10").get();
        		String title = doc.title();
        		System.out.println(title);
       		*/
        	
        	String bingSearchUrl = "http://www.bing.com/search?q=keyword";
        	String keyword = "河北省政府采购";

        	String uaString = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.15 (KHTML, like Gecko) Chrome/24.0.1295.0 Safari/537.15";
        	String url = bingSearchUrl.replaceAll("keyword", URLEncoder.encode("intitle:\"" + keyword + "\"", "UTF-8"));

        	Document doc = Jsoup.connect(url).userAgent(uaString).get();

        	System.out.println(doc.select("a"));
        		
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}