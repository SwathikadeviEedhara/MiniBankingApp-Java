package jdbcBankProject;

import java.util.Scanner;

public class BankService {

	public static void main(String[] args) {
		
	
	Scanner sc=new Scanner(System.in);
	
	BankingApp bank=new BankingApp();
	
	
	
	while(true) {
		
		
		System.out.println("\n=== BANK MENU ===");
        System.out.println("1. Create Account");
        System.out.println("2. Login");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Check Balance");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
		
		
		int choice=sc.nextInt();
		
		switch(choice) {
		
		case 1:
			bank.createAccount();
		      break;
		
		case 2:
			bank.login();
		      break;
		      
		      
		case 3:
			System.out.print("Account No: ");
            int Accno = sc.nextInt();
            bank.deposit(Accno);
            break; 
		 
		
		case 4:
		    System.out.print("Account No: ");
		    int wAcc = sc.nextInt();	    
		    bank.withdraw(wAcc);
		    break;

            
        case 5:
            System.out.print("Account No: ");
            int cAcc = sc.nextInt();
            bank.checkBalance(cAcc);
            break;
            
        case 6:
            System.out.println("Thank you for banking with us!");
            sc.close();
            System.exit(0);
            
        default:
            System.out.println("Invalid option—please try again.");
            
            
            
		     }
	
	   }
	
	
   }	
}
