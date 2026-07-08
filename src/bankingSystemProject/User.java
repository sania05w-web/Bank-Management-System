package bankingSystemProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class User {
	
     private Scanner scanner;
      
     public User(Scanner scanner) {
    	     this.scanner = scanner;
     }
     
     public void register() {
    	    scanner.nextLine();
    	    System.out.println("ENTER FULL NAME");
    	    String fullName = scanner.nextLine();
    	    System.out.println("ENTER THE EMAIL");
    	    String email = scanner.nextLine();
    	    System.out.println("ENTER THE PASSWORD");
    	    String password = scanner.nextLine();
    	    if(user_exist(email)) {
    	    	   System.out.println("User Already Exist with This Email Address");
    	    	   return;
    	    }
    	    String register_query = "INSERT INTO USER(FULL_NAME,EMAIL,PASSWORD) VALUES(?,?,?)";
    	    try(Connection connection = MySQLConnection.getConnection(); 
    	    		PreparedStatement preparedstatement = connection.prepareStatement(register_query)){
    	    	    preparedstatement.setString(1, fullName);
    	    	    preparedstatement.setString(2, email);
    	    	    preparedstatement.setString(3,password);
    	    	    preparedstatement.executeUpdate();
    	    	    System.out.println("User register Successfully");
    	     }
    	    catch(Exception e) {
    	    	  e.printStackTrace();
    	    }
    	    
    	    
    	    
    	   
     }
      
     public String login() {
    	     scanner.nextLine();
    	     System.out.println("Enter The Email");
    	     String email = scanner.nextLine();
    	     System.out.println("Enter The Password");
    	     String password = scanner.nextLine();
    	     
    	     String login_query = "select * from user where email=? and password=?";
    	     
    	     try(Connection connection = MySQLConnection.getConnection();
    	    		 PreparedStatement preparedstatement = connection.prepareStatement(login_query);
    	    		 ){
    	    	     preparedstatement.setString(1, email);
    	    	     preparedstatement.setString(2,password);
    	    	     ResultSet result = preparedstatement.executeQuery();
    	    	     if(result.next()) {
    	    	    	   return email;
    	    	     }
    	    	     else {
    	    	    	   return null;
    	    	     }
    	    	     
    	     }
    	    
    	     
    	     catch(Exception e) {
    	    	    e.printStackTrace();
    	     }
    	     return null;
    	    
     }
     
	 public boolean user_exist(String email) {
		  String userExist_query = "SELECT * FROM USER WHERE EMAIL = ?";
		  
		 try(Connection connection = MySQLConnection.getConnection();
			 PreparedStatement preparedstatement = connection.prepareStatement(userExist_query)){
			 preparedstatement.setString(1, email);
			 ResultSet resultset = preparedstatement.executeQuery();
			 if(resultset.next()) {
				 return true;
			 }
			 else {
				 return false;
			 }
			
		 }
		 
	 catch(Exception e) {
			e.printStackTrace();
		 }
		 return false;
		
	 }
	  
}
