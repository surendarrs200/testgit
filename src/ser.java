
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
	@SuppressWarnings("serial")
	public cfglass ser extends HttpServlet {
		String desktopPath1 = System.getProperty("user.home") + "/Desktop";
		String desktopPath = desktopPath1.replace("\\", "/");
	    
	    public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException 
		{
	    	doPost(request,response);
		}

		
	    public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
		{
	    	

	                
	                 byte[] fileBytes;
	                 String query;
	                 try
	                 {
	                	 java.sql.Connection conn=null;
	                     
	 			    	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	 			        conn=DriverManager.getConnection("jdbc:odbc:nirimgtbl","sa","test");
	 			    	
	 			    	
	                         query = "select originalimage from imgnir";
	                         Statement state = conn.createStatement();
	                         ResultSet rs = state.executeQuery(query);
	                         if (rs.next())
	                        {
	                                  fileBytes = rs.getBytes(1);
	                                  OutputStream targetFile=new FileOutputStream("D:/new.JPG");

	                                  targetFile.write(fileBytes);
	                                  targetFile.close();
	                        }        
	                        
	                 }
	                 catch (Exception e)
	                 {
	                         e.printStackTrace();
	                 }
	        
			   
	    	    
	    	  
	    	   
	    	
				
	    	
		

		}

	}

	
	

