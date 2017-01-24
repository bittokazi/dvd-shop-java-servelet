import java.io.*;
import java.sql.*;

public class db {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/dvdshop";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   
   Connection conn = null;
   Statement stmt = null;
   
   public db() {
	   try{
	   Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   }
	   catch(Exception se){
		   
	   }
	   
   }
   
   public ResultSet get_data(String q) throws SQLException {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			return rs;
   }
   
   public String insert_data(String sql) {
	   try {
		   stmt = conn.createStatement();
			int num=stmt.executeUpdate(sql);
		   return "ok";
	   }
	   catch (Exception e) {
			return e.toString();
	   }
   }
   public String update_data(String sql) {
	   try {
		   stmt = conn.createStatement();
			int num=stmt.executeUpdate(sql);
		   return "ok";
	   }
	   catch (Exception e) {
			return e.toString();
	   }
   }
   public String delete_data(String sql) {
	   try {
		   stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		   return "ok";
	   }
	   catch (Exception e) {
			return e.toString();
	   }
   }
   
	public boolean dbcon() {
	   try{
	   Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      return true;
	   }
	   catch(Exception se){
		   
	   }
	   return false;
	   
   }
}