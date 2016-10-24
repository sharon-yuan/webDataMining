package cn.edu.hfut.dmic.webcollector.crawler;
import java.security.MessageDigest;

public class checkCode {
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes();
        
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
          
            mdInst.update(btInput);
           
            byte[] md = mdInst.digest();
          
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.err.println(checkCode.MD5(""));
        System.err.println(checkCode.MD5("����"));
    }

    
	public static String getAns(String text) {
		
		return checkCode.MD5(text);
	}
}
