package bankingSystemProject;

import java.util.Scanner;

public class BankAccountManagementSystem2 {
   
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		AccountAdmin account = new AccountAdmin(sc);
		AccountManager accountmanager = new AccountManager(sc);
		User user = new User(sc);
		
		String email;
		long account_number;
		
		while(true) {
			System.out.println("** WELCOME TO BANKING SYSTEM **");
			System.out.println();
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.println("Enter Your Choice: ");
			int inputUser = sc.nextInt();
			switch(inputUser) {
			case 1:
				user.register();
				break;
			case 2:
				email = user.login();
				if(email!=null) {
					System.out.println();
					System.out.println("User Logged In!");
					if(!account.account_Exist(email)) {
						System.out.println();
						System.out.println("1. Open a new Bank Account");
						System.out.println("2. Exit");
						if(sc.nextInt() == 1) {
							account_number = account.openAccount(email);
							System.out.println("Account Created Successfully");
							System.out.println("Your Account Number is: "+account_number);
						}
						else {
							break;
						}
					}
				   account_number = account.getAccountNumber(email);
				   int inputUser1 = 0;
				   while(inputUser1 != 5) {
					   System.out.println();
					   System.out.println("1.Debit Money");
					   System.out.println("2.Credit Money");
					   System.out.println("3.Transfer Money");
					   System.out.println("4.Check Balance");
					   System.out.println("5.Log Out");
					   System.out.println("Enter your choice: ");
					   inputUser1 = sc.nextInt();
					   switch(inputUser1) {
					    case 1:
					    	 accountmanager.withdraw_money(account_number);
					    	 break;
					    case 2:
					    	 accountmanager.deposit_money(account_number);
					    	 break;
					    case 3:
					    	 accountmanager.tranfer_money(account_number);
					    	 break;
					    case 4:
					    	 accountmanager.check_Balance(account_number);
					    	 break;
					    case 5:
					    	 break;
					    	default:
					    		System.out.println("Enter valid choice");
					    		break;
					   }
				   }
				}
			case 3:
				System.out.println("THANK YOU FOR USING BANKING SYSTEM");
				System.out.println("Exiting System!");
				return;
			default:
				System.out.println("Enter valid Input");
				
			
		}
	}

}
}
