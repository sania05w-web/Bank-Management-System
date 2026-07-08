package bankingSystemProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class AccountManager  {
	
	 private Scanner scanner;
	 AccountManager(Scanner scanner){
		 this.scanner = scanner;
	 }
	 
	 
    public void withdraw_money(long accountNumber)  {
    	    scanner.nextLine();
    	    System.out.println("Enter Amount: ");
    	    double amount = scanner.nextDouble();
    	    scanner.nextLine();
    	    System.out.println("Enter Security Pin: ");
    	    String securityPin = scanner.nextLine();
     	try(Connection con =  MySQLConnection.getConnection();) {
           con.setAutoCommit(false);
    	   
          if(accountNumber!=0) {
    	      PreparedStatement ps = con.prepareStatement("Select * from accounts where security_pin = ?");
    	      ps.setString(1, securityPin);
    	      ResultSet resultset = ps.executeQuery();
    	      
    	      if(resultset.next()) {
    	    	     double currentBalance = resultset.getDouble("balance");
    	    	     if(amount<=currentBalance) {
    	    	    	    String withdraw_query = "Update accounts set balance = balance - ? where account_number = ?";
    	    	    	    PreparedStatement preparedstatement = con.prepareStatement(withdraw_query);
    	    	    	    preparedstatement.setDouble(1, amount);
    	    	    	    preparedstatement.setLong(2, accountNumber);
    	    	    	    int rowaffected = preparedstatement.executeUpdate();
    	    	    	    if(rowaffected > 0) {
    	    	    	    	    System.out.println("Rs "+amount+" Withdraw Successfully");
    	    	    	    	    con.commit();
    	    	    	    	    con.setAutoCommit(true);
    	    	    	    	    
    	    	    	    }
    	    	    	    else {
    	    	    	    	     System.out.println("Transaction Failed");
    	    	    	    	     con.rollback();
    	    	    	    	     con.setAutoCommit(true);
    	    	    	    }
    	    	    	   
    	    	     }
    	    	     else {
    	    	    	    System.out.println("Insufficient Balance");
    	    	     }
    	    	     
    	      }
    	      else {
	    	      System.out.println("Account Not Found");
	      }
    	    }
     	}
     	 catch(Exception e) {
      		System.out.println("Exception ");
      		System.out.println(e);
      	}
    	   }
    	  
     
     
    public void deposit_money(long accountNumber) throws Exception {
    	   scanner.nextLine();
    	   System.out.println("Enter Amount: ");
    	   double amount = scanner.nextDouble();
    	   scanner.nextLine();
    	   System.out.println("Enter Security Pin: ");
    	   String securityPin = scanner.nextLine();
    	   try{
    		      Connection con =  MySQLConnection.getConnection();
              con.setAutoCommit(false);
        	   
              if(accountNumber!=0) {
        	      PreparedStatement ps = con.prepareStatement("Select * from accounts where security_pin = ?");
        	      ps.setString(1, securityPin);
        	      ResultSet resultset = ps.executeQuery();
        	      
        	      if(resultset.next()) {
        	    	     double currentBalance = resultset.getDouble("balance");
        	    	     if(amount>0) {
        	    	    	    String deposit_query = "Update accounts set balance = balance + ? where account_number = ?";
        	    	    	    PreparedStatement preparedstatement = con.prepareStatement(deposit_query);
        	    	    	    preparedstatement.setDouble(1, amount);
        	    	    	    preparedstatement.setLong(2, accountNumber);
        	    	    	    int rowaffected = preparedstatement.executeUpdate();
        	    	    	    if(rowaffected > 0) {
        	    	    	    	    System.out.println("Rs "+amount+" Deposit Successfully");
        	    	    	    	    con.commit();
        	    	    	    	    con.setAutoCommit(true);
        	    	    	    	    
        	    	    	    }
        	    	    	    else {
        	    	    	    	     System.out.println("Transaction Failed");
        	    	    	    	     con.rollback();
        	    	    	    	     con.setAutoCommit(true);
        	    	    	    }
        	    	    	   
        	    	     }
        	    	     else {
        	    	    	    System.out.println("Insufficient Balance");
        	    	     }
        	    	     
        	      }
        	      else {
    	    	      System.out.println("Invalid Pin !");
    	      }
        	    }
         	}
         	 catch(Exception e) {
          		e.printStackTrace();
          	}
    	        
				  
        	   }
        	  
     public void tranfer_money(long senderAccountNumber) {
    	    scanner.nextLine();
    	    System.out.println("Enter Receiver Account Number");
    	    long receiverAccNumber = scanner.nextLong();
    	    System.out.println("Enter Amount: ");
    	    double amount = scanner.nextDouble();
    	    scanner.nextLine();
    	    System.out.println("Enter security Pin");
    	    String securityPin = scanner.nextLine();
    	    try {
    	    	  Connection con = MySQLConnection.getConnection();
    	    	  con.setAutoCommit(false);
    	    	  if(senderAccountNumber!=0 && receiverAccNumber!=0) {
    	    		  PreparedStatement preparedstatement = con.prepareStatement("Select * from accounts where account_number = ? and security_pin = ?");
    	    		  preparedstatement.setLong(1, senderAccountNumber);
    	    		  preparedstatement.setString(2, securityPin);
    	    		  ResultSet result = preparedstatement.executeQuery();
    	    		  
    	    		  if(result.next()) {
    	    			  double current_balance = result.getDouble("balance");
    	    			  if(amount<=current_balance) {
    	    				  String debit_query = "Update accounts set balance = balance - ? where account_number = ?";
    	    				  String credit_query = "Update accounts set balance = balance + ? where account_number = ?";
    	    				  PreparedStatement creditStatement = con.prepareStatement(credit_query);
    	    				  PreparedStatement debitStatement = con.prepareStatement(debit_query);
    	    				  creditStatement.setDouble(1, amount);
    	    				  creditStatement.setLong(2,receiverAccNumber);
    	    				  debitStatement.setDouble(1,amount);
    	    				  debitStatement.setLong(2,senderAccountNumber );
    	    				  int rowsAffected1 = debitStatement.executeUpdate();
    	    				  int rowsAffected2 = creditStatement.executeUpdate();
    	    				  if(rowsAffected1>0 && rowsAffected2>0) {
    	    					  System.out.println("Transaction Successful");
    	    					  con.commit();
    	    					  con.setAutoCommit(true);
    	    				  }
    	    				  else {
    	    					  System.out.println("Transaction Failed");
    	    					  con.rollback();
    	    					  con.setAutoCommit(false);
    	    				  }
    	    				  
    	    			  }else {
    	    				  System.out.println("Insufficient Balance");
    	    			  }
    	    		  }
    	    		  else {
    	    			  System.out.println("Invalid Security Pin");
    	    		  }
    	    	  }
    	    	  else {
    	    		  System.out.println("Invalid Account Number");
    	    	  }
    	    }
    	    catch(Exception e) {
    	    	   e.printStackTrace();
    	    }
    	    
    	    
     }
      
     
     public void check_Balance(long accountNumber) throws Exception {
    	    scanner.nextLine();
    	    System.out.println("Enter Security Pin");
    	    String securityPin = scanner.nextLine();
    	    String query = "select balance from accounts where account_number = ? and security_pin = ?";
         
        try( Connection con =  MySQLConnection.getConnection(); 	   
             PreparedStatement ps = con.prepareStatement(query);){
 	         ps.setLong(1, accountNumber);
 	         ps.setString(2, securityPin );
 	         ResultSet rs = ps.executeQuery();
 	        if(rs.next()) {
 	    	      double accBalance = rs.getDouble("balance");
 	    	      System.out.println("Available Balance : "+accBalance);
 	    	    
     }
 	        else {
 	        	   System.out.println("Invalid Pin");
 	        }
        }
 	   catch(Exception e) {
 		   e.printStackTrace();
 	   }

}
}
