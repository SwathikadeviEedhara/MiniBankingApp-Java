
package jdbcBankProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class BankingApp {
	boolean islogin=false;
	static Scanner sc=new Scanner(System.in);

	String sql="";
		
	boolean createAccount() {
	    Connection con = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;

	    try {
	        // Step 1: Connect to the database
	        con = JdbcConnection.getCon();

	        // Step 2: SQL query to insert a new account (without account_No, as it's auto-generated)
	        String insertSql = "INSERT INTO customer(customer_name, balance, passcode) VALUES (?, ?, ?)";

	        // Step 3: Prepare the SQL statement with RETURN_GENERATED_KEYS flag
	        pst = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);

	        // Step 4: Get user input for customer details
	        System.out.print("Enter Customer Name: ");
	        String name = sc.next();
	        System.out.print("Enter Initial Balance: ");
	        double balance = sc.nextDouble();
	        System.out.print("Enter Passcode: ");
	        int passcode = sc.nextInt();

	        // Step 5: Set parameters for the SQL statement
	        pst.setString(1, name);
	        pst.setDouble(2, balance);
	        pst.setInt(3, passcode);

	        // Step 6: Execute the insert operation
	        int rows = pst.executeUpdate();

	        // Step 7: If insertion is successful, retrieve the generated account number
	        if (rows > 0) {
	            rs = pst.getGeneratedKeys();  // Retrieve the generated keys (e.g., account_No)
	            if (rs.next()) {
	                int accountNo = rs.getInt(1); // Get the generated account number (first column)
	                System.out.println("Account Created Successfully!");
	                System.out.println("Your Account Number is: " + accountNo +" "+ " and passcode is: "+passcode ); // Display account number
	            }
	        } else {
	            System.out.println("Account creation failed.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Step 8: Close the resources in finally block to ensure they are closed
	        try {
	            if (rs != null) rs.close();   // Close ResultSet
	            if (pst != null) pst.close(); // Close PreparedStatement
	            if (con != null) con.close(); // Close Connection
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return false;
	}

	

	void login() {
	    if (islogin) {
	        System.out.println("Already logged in");
	        return;
	    }

	    int attempts = 0;
	    final int MAX_ATTEMPTS = 3;

	    while (attempts < MAX_ATTEMPTS) {
	        try {
	            Connection con = JdbcConnection.getCon();

	            System.out.print("Enter your Account Number: ");
	            int account_No = sc.nextInt();

	            System.out.print("Enter your Passcode: ");
	            int passcode = sc.nextInt();

	            sql = "SELECT * FROM customer WHERE account_No = ? AND passcode = ?";
	            PreparedStatement pst = con.prepareStatement(sql);

	            pst.setInt(1, account_No);
	            pst.setInt(2, passcode);

	            ResultSet rs = pst.executeQuery();

	            if (rs.next()) {
	                System.out.println("Login successful");
	                islogin = true;
	                rs.close();
	                pst.close();
	                con.close();
	                return;
	            } else {
	                attempts++;
	                System.out.println("Login failed! Attempts left: " + (MAX_ATTEMPTS - attempts));
	            }

	            rs.close();
	            pst.close();
	            con.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    System.out.println("Too many failed attempts. Login blocked for this session.");
	}

	
public  void deposit(int account_No) {
    System.out.print("Enter Amount to Deposit: ");
    double amount = sc.nextDouble();
    
    if(amount<0) {
    	
    	System.out.println("ammount must be more than 0");
    	return;
    }
    
    try {
        Connection con = JdbcConnection.getCon();
        String query = "UPDATE customer SET balance = balance + ? WHERE account_No = ?";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setDouble(1, amount);
        pst.setInt(2, account_No);

        int rows = pst.executeUpdate();

        if (rows > 0) {
            System.out.println("Deposit Successful!");
        } else {
            System.out.println("Deposit Failed!");
        }

        pst.close();
        con.close();

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}



public  void withdraw(int account_No) {
    System.out.print("Enter Amount to Withdraw: ");
    double amount = sc.nextDouble();

    try {
        Connection con = JdbcConnection.getCon();

        // Step 1: Check current balance
        String checkQuery = "SELECT balance FROM customer WHERE account_No = ?";
        PreparedStatement checkPst = con.prepareStatement(checkQuery);
        checkPst.setInt(1, account_No);
        ResultSet rs = checkPst.executeQuery();

        if (rs.next()) {
            double currentBalance = rs.getDouble("balance");

            if (currentBalance < amount) {
                System.out.println("Insufficient Balance!");
            } else {
                // Step 2: Withdraw amount
                String updateQuery = "UPDATE customer SET balance = balance - ? WHERE account_No = ?";
                PreparedStatement updatePst = con.prepareStatement(updateQuery);
                updatePst.setDouble(1, amount);
                updatePst.setInt(2, account_No);

                int rows = updatePst.executeUpdate();
                if (rows > 0) {
                    System.out.println("Withdrawal Successful!");
                } else {
                    System.out.println("Withdrawal Failed!");
                }

                updatePst.close();
            }
        } else {
            System.out.println("Account not found.");
        }

        rs.close();
        checkPst.close();
        con.close();

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}


public  void checkBalance(int account_No) {
    try {
        Connection con = JdbcConnection.getCon();
        String query = "SELECT balance FROM customer WHERE account_No = ?";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setInt(1, account_No);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            double balance = rs.getDouble("balance");
            System.out.println("Your Current Balance is: " + balance);
        } else {
            System.out.println("Account not found.");
        }

        rs.close();
        pst.close();
        con.close();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}



















	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
