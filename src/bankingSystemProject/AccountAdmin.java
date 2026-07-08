package bankingSystemProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class AccountAdmin{
	
	private Scanner scanner;
    
    public AccountAdmin(Scanner scanner) {
   	     this.scanner = scanner;
    }
    
	  
	 public long openAccount(String email)  {
		 if(!account_Exist(email)) {
		 String query = "INSERT INTO accounts(account_number,full_name,email,balance,security_pin) VALUES(?,?,?,?,?)";
		 scanner.nextLine();
		 System.out.println("Enter Full Name");
		 String full_name = scanner.nextLine();
		 System.out.println("Enter account Balance");
		 double balance = scanner.nextDouble();
		 scanner.nextLine();
		 System.out.println("Enter Security Pin");
		 String securityPin = scanner.next();
		 
		 try(Connection con = MySQLConnection.getConnection(); PreparedStatement ps = con.prepareStatement(query);){
		  long accountNumber = generateAccountNumber();
		  ps.setLong(1, accountNumber);
		  ps.setString(2, full_name);
		  ps.setString(3, email);
		  ps.setDouble(4, balance);
		  ps.setString(5, securityPin);
		  int roweffected = ps.executeUpdate();
		  if(roweffected>0) {
			  return accountNumber;
		  }
		  else {
			  throw new RuntimeException("Account Already Exist");
		  }
		 } 
		catch(Exception e) {
			e.printStackTrace();
		}
		 }
		 throw new RuntimeException("Account Already Exist");
	 }
	 
	 
	public void removeAccount(long accountNumber)  {
		 
		 String query ="DELETE FROM account WHERE account_number = ?";
		 try(Connection con = MySQLConnection.getConnection();
		 PreparedStatement ps = con.prepareStatement(query);){
		 ps.setLong(1,accountNumber );
		 int rows =  ps.executeUpdate();
		 if(rows>0) {
			System.out.println("ACCOUNT DELETED SUCCESSFULLY");
		 }
		 else {
			 System.out.println("ACCOUNT NOT FOUND");
		 } 
		 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
	 }
	 
	 
	public void searchAccount(long accountNumber) throws Exception{
		 
		 String query ="SELECT account_number,full_name,email,balance  FROM account WHERE account_number = ?";
		 try(Connection con = MySQLConnection.getConnection();
		 PreparedStatement ps = con.prepareStatement(query);){
		 ps.setLong(1,accountNumber);
		 ResultSet rs =ps.executeQuery();
		 if(rs.next()) {
		 Long accNumber = rs.getLong("account_number");
		 String name = rs.getString("full_name");
		 String email = rs.getString("email");
		 double balance = rs.getDouble("balance");
		 
		 System.out.println("Account Holder  DETAIL");
		 System.out.println("ACCOUNT NUMBER: "+accNumber);
		 System.out.println("ACCOUNT HOLDER : "+name);
		 System.out.println("ACCOUNT Email : "+email );
		 System.out.println("ACCOUNT BALANCE : "+balance);
		 }
		 else {
			 System.out.println("Account Not Found");
		 }
		 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
		 
		}
	 
	 
	 public long getAccountNumber(String email) {
		 String query = "SELECT account_number from accounts where email=?";
		 try(Connection con = MySQLConnection.getConnection();
		 PreparedStatement ps = con.prepareStatement(query);){
		 ps.setString(1, email);
		 ResultSet resultset = ps.executeQuery();
		 if(resultset.next()) {
			 return resultset.getLong("account_number");
			 
		 }
		 
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 throw new RuntimeException("Account Does Not exist");
	 }
	 
	 private long generateAccountNumber() {
		 String query = "Select account_number from accounts order by account_number desc limit 1";
		try(Connection connection = MySQLConnection.getConnection();
			PreparedStatement preparedstatement = connection.prepareStatement(query)) {
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				long last_account_number = resultset.getLong("account_number");
				return last_account_number+1;
			}
			else {
				return 18000001;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 18000001;
	 }
	 
	 
	 public boolean account_Exist(String email) {
		 String query = "SELECT ACCOUNT_NUMBER FROM ACCOUNTS WHERE EMAIL=?";
		 
		 try(Connection con = MySQLConnection.getConnection();
		 PreparedStatement ps = con.prepareStatement(query);){
			 ps.setString(1,email);
			 ResultSet resultset = ps.executeQuery();
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
	
	
	
	

