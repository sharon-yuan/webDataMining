import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class getPostInfo {
	public static void main(String []args){
		httpUrlConnection();
	}
	private static void httpUrlConnection() {   
		try {   
		String pathUrl = "http://www.cdggzy.com:8147/newsite/Notice/CgIndex1.aspx?classid=2&typeid=1";   
		// 建立连接   
		URL url = new URL(pathUrl);   
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();   
		  
		   
		// //设置连接属性   
		httpConn.setDoOutput(true);// 使用 URL 连接进行输出   
		httpConn.setDoInput(true);// 使用 URL 连接进行输入   
		httpConn.setUseCaches(false);// 忽略缓存   
		httpConn.setRequestMethod("POST");// 设置URL请求方法   
		String requestString = "pageindex=4&pagesize=10";   
		  String hostString="www.cdggzy.com:8147";
		  String referer="http://www.cdggzy.com:8147/newsite/Notice/GGList.aspx?classid=2&typeid=1";
		   String userAgent="Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
		// 设置请求属性   
		// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致   
		byte[] requestStringBytes = requestString.getBytes("utf-8");   
		httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);   
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");   
		httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接   
		httpConn.setRequestProperty("Charset", "UTF-8");   
		//   
		//String name = URLEncoder.encode("黄武艺", "utf-8");   
		httpConn.setRequestProperty("pageindex", "4");
		httpConn.setRequestProperty("pagesize", "10");
		  
		   
		// 建立输出流，并写入数据   
		OutputStream outputStream = httpConn.getOutputStream();   
		outputStream.write(requestStringBytes);   
		outputStream.close();   
		// 获得响应状态   
		int responseCode = httpConn.getResponseCode();   
		  
		   
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功   
		// 当正确响应时处理数据   
		StringBuffer sb = new StringBuffer();   
		String readLine;   
		BufferedReader responseReader;   
		// 处理响应流，必须与服务器响应流输出的编码一致   
		 responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));   
		while ((readLine = responseReader.readLine()) != null) {
			if(readLine.equals(""))continue;
			System.out.println(readLine);
		sb.append(readLine).append("\n");   
		}   
		responseReader.close();   
		System.out.println(sb.toString());   
		}   
		} catch (Exception ex) {   
		ex.printStackTrace();   
		}   
		  
		   
		} 
	
	

}
