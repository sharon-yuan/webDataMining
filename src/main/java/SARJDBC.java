

//ʹ��JDBC��д���ݿ�
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SARJDBC {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://192.168.62.48/Crawler?useUnicode=true&characterEncoding=utf-8";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "redhat";

	public static void main(String[] args) {

		getFromCity();
	}// end main

	public static void get() {

		Connection conn = getConn();
		Statement stmt = null;
		try {

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM City";
			stmt.execute(sql);
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name

				System.out.print(rs.getString("Name"));

			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
			// System.out.println("Goodbye!");

	}

	public static List<ArrayList<String[]>> getFromCity() {

		List<ArrayList<String[]>> Loactions = new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> Cities = new ArrayList<String[]>();
		ArrayList<String[]> Counties = new ArrayList<String[]>();
		Connection conn = getConn();
		Statement stmt = null;
		try {

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			ArrayList<String> list2level = new ArrayList<String>();
			list2level.add("City");
			list2level.add("County");

			sql = "SELECT * FROM City";
			
			stmt.execute(sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Cities.add(new String[] { rs.getString("Name"), rs.getString("Super") });

			}
			sql = "SELECT * FROM County";
			System.out.println(sql);
			stmt.execute(sql);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Counties.add(new String[] { rs.getString("Name"), rs.getString("Super") });

			}

			// STEP 6: Clean-up environment
			rs.close();

			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
			// System.out.println("Goodbye!");
		Loactions.add(Cities);
		Loactions.add(Counties);
		return Loactions;

	}

	private static Connection getConn() {

		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER).newInstance();
			
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
		
			e.printStackTrace();
		}
		return conn;
	}

	public static int insertIntoLocations(String tableName, int Uid, String name, String superName) {
		Connection conn = getConn();
		int i = 0;
		String sql = "insert into " + tableName + " values(?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, Uid);
			pstmt.setString(2, name);
			pstmt.setString(3, superName);
			System.out.println(pstmt.toString());
			i = pstmt.executeUpdate();

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static int insertIntoInfo(List<String> info) {
		System.out.println(info.toString());
		Connection conn = getConn();
		int i = 0;
		
		//uid creattime deadline keywords location
	String sql = "insert into HtmlInfo values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.valueOf(info.get(0)));
			for (int index = 2; index < 9; index++)
				pstmt.setString(index, info.get(index-1));

			
			i = pstmt.executeUpdate();

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static int insertIntoOriginalData(String URL,String textPage){

		
		int i = 0;		
		try {
			String FilePath="textToSeg.txt";
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(FilePath)),"utf-8"));
			output.write(textPage);	
			output.close();
			
			//List<String> textSeg=WordFrequencyStatistics.getWordFre(FilePath);
		
			Connection conn = getConn();
			
			String sql = "insert into OriginalData values(?,?,?)";
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, URL);
			pstmt.setString(2, textPage);
			//pstmt.setString(3, textSeg.toString());
			System.out.println(pstmt.toString());
			i = pstmt.executeUpdate();

			pstmt.close();
			conn.close();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	return i;
    }

}