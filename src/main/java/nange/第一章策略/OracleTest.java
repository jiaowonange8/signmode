package nange.第一章策略;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleTest {
	
	 //数据库连接对象
    private static Connection conn = null;
     
    private static String driver = "oracle.jdbc.driver.OracleDriver"; //驱动
     
    private static String url = "jdbc:oracle:thin:@//192.168.51.251:1521/orcl"; //连接字符串
     
    private static String username = "C##nange"; //用户名
     
    private static String password = "qwer1234"; //密码
     
     
    // 获得连接对象
    private static synchronized Connection getConn(){
        if(conn == null){
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
     
    //执行查询语句
    public void query(String sql) throws SQLException, IOException{
        PreparedStatement pstmt;
         
        try {
            pstmt = getConn().prepareStatement(sql);
            //建立一个结果集，用来保存查询出来的结果
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);
//            	Blob b = rs.getBlob("image");
//            	InputStream in = b.getBinaryStream();
//            	OutputStream out = new FileOutputStream(new File("C:/Users/Administrator/Desktop/66666.jpg"));
//            	byte[] by = new byte[1024];
//            	int length;
//            	while((length=in.read(by))!=-1){
//            		out.write(by, 0, length);
//            	}
//            	out.flush();
//            	out.close();
//            	in.close();
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    public void insert(String sql) throws SQLException, IOException{
    	 PreparedStatement pstmt;
         pstmt = getConn().prepareStatement(sql);
         pstmt.setInt(1, 3);
         pstmt.setString(2, "test");
         InputStream in = new FileInputStream("E://身份证1.jpg");
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] b = new byte[1024]; 
         int len;
         while((len = in.read(b)) != -1){
             baos.write(b, 0, len);
         }
         pstmt.setBytes(3, baos.toByteArray());
         pstmt.execute();
         in.close();
    }
     
    
     
    //关闭连接
    public void close(){
        try {
            getConn().close();
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception {
    	OracleTest o = new OracleTest();
//    	o.query("select * from \"USER\"");
//    	o.query("select * from TEST_img");
//    	o.insert("insert into test_img(id,name,image) values(?,?,?)");
    	o.query("select * from test_img where id = 3");
     }

}
