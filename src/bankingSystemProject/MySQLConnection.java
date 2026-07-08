package bankingSystemProject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

 class MySQLConnection {
	
	void createDatabase() {
		 
		 String url = "jdbc:mysql://localhost:3306/";
		 String uname ="your_Username";
		 String upass ="your_Mysql_password";
		 String query = "CREATE DATABASE IF NOT EXISTS banking_system";
		 try(Connection con = DriverManager.getConnection(url,uname,upass);  
				 PreparedStatement ps = con.prepareStatement(query); ){
			      Class.forName("com.mysql.cj.jdbc.Driver");
			      ps.executeUpdate();
			      System.out.println("DATABASE CREATED SUCCESSFULLY");
		 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
		}
	
	static Connection getConnection() throws ClassNotFoundException, SQLException{
		String url = "jdbc:mysql://localhost:3306/banking_system";
		String uname ="your-userName";
		String upass = "your_Mysql_password";
		Class.forName("com.mysql.cj.jdbc.Driver");
		return 
		DriverManager.getConnection(url,uname,upass);
	}
	
	void createTable() throws Exception{
		String createAccountTable = "CREATE TABLE IF NOT EXISTS ACCOUNTS("+"account_number bigint"+"full_name varchar(255)"+"email varchar(255)"+"balance decimal(10,2)"+"security_pin char(4))";
		String createUserTable ="CREATE TABLE IF NOT EXISTS USER("+"full_name varchar(255),"+"email varchar(255) primary key"+"password varchar(255))";
		try(Connection con = getConnection();
		    PreparedStatement ps = con.prepareStatement(createAccountTable);
		    PreparedStatement preparedstatement = con.prepareStatement(createUserTable);)
		{
			ps.executeUpdate();
			preparedstatement.executeUpdate();
			System.out.println("TABLE CREATED SUCCESSFULLY");
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
}
