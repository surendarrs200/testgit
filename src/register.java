



import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
	@SuppressWarnings("serial")
	public class register extends HttpServlet {
		
	    
	    public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException 
		{
	    	doPost(request,response);
		}

		ddfdf
	    public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
		{RequestDispatcher rp;
	    String fname=null,lname=null,username=null,password1=null,emailid=null,phoneno=null,address=null,dob=null,gender=null,pincode=null;
	    	
	    fname=(String)request.getParameter("fname");
	    lname=(String)request.getParameter("lname");
	    username=(String)request.getParameter("username");
	    password1=(String)request.getParameter("password1");
	    emailid=(String)request.getParameter("emailid");
	    phoneno=(String)request.getParameter("phoneno");
	    address=(String)request.getParameter("address");
	    dob=(String)request.getParameter("dob");
	    gender=(String)request.getParameter("gender");
	    pincode=(String)request.getParameter("pincode");
	  			
	if(fname!=null||lname!=null||username!=null||password1!=null||emailid!=null||phoneno!=null||address!=null||dob!=null||gender!=null||pincode!=null){
		
		try {            
					        int rs1;
							Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
							java.sql.Connection con = DriverManager.getConnection("jdbc:odbc:nirimgtbl","sa","test");
							Statement s= con.createStatement();
		                     
							rs1=s.executeUpdate("insert into recregister(Fname,Lname,username,password1,emailid,phoneno,address,dob,gender,pincode) values('"+fname+"','"+lname+"','"+username+"','"+password1+"','"+emailid+"','"+phoneno+"','"+address+"','"+dob+"','"+gender+"','"+pincode+"')");
											
										if(username!=null){
										if(rs1==1)
										{
											System.out.println("Saved successfully==="+rs1);
											
											
											rp=request.getRequestDispatcher("Success.jsp");
											rp.forward(request, response);
										}
										else{
											rp=request.getRequestDispatcher("failure.jsp");
											rp.forward(request, response);
										}
										}
									}
									catch (Exception e1) {
		
										e1.printStackTrace();
									}
		
		
	}
		
		}
	   
		}



	
	

