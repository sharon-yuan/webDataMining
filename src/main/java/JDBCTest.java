
import java.sql.SQLException;

/**  
     * @author wxweven  
     *  
     */  
    public class JDBCTest {  
      
        /**  
         * @param args  
         * @throws SQLException   
         */  
        public static void main(String[] args) throws SQLException {  
            //1.ע������  
            try {  
                Class.forName("com.mysql.jdbc.Driver");  
            } catch (ClassNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
              
           
            //useUnicode=true&characterEncoding=GBK��֧������  
            java.sql.Connection conn = java.sql.DriverManager.getConnection(  
                    "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=GBK",  
                    "root", "wang2016");  
              
            //3��ȡ���ʽSQL  
            java.sql.Statement stmt = conn.createStatement();  
              
            //4.ִ��SQL  
            String sql = "select * from test";  
            java.sql.ResultSet res = stmt.executeQuery(sql);  
              
            //5.��ӡ������������  
            while(res.next()) {  
                System.out.print("the id: ");  
                System.out.println(res.getInt(1));  
                System.out.print("the user: ");  
                System.out.println(res.getString("user"));  
                System.out.print("the address: ");  
                System.out.println(res.getString("addr"));  
                System.out.println();  
            }  
              
              
            //���Բ������ݿ�Ĺ��ܣ�  
            //String inSql = "insert into test(user,addr) values('����2','�µ�ַ2')";  
            //stmt.executeUpdate(inSql);  
              
            //6.�ͷ���Դ���ر����ӣ�����һ�����õ�ϰ�ߣ�  
            res.close();  
            stmt.close();  
            conn.close();  
        }  
      
    }  