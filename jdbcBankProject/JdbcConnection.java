package jdbcBankProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {


	  public static Connection getCon() {
	
		  try {
		
	String url="";	
	String user="";// 
	String pass="";//enter you url ,username and password
       
//        System.out.println("connection successfull");
        Connection con=DriverManager.getConnection(url, user, pass);
        
        return con;
        
	    }
	catch(Exception e) {
		
		System.out.println("exception handliled");
		
		return null;
	     }
   }



	  

}
