
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.Graphics2D;
	@SuppressWarnings("serial")
	public class img extends HttpServlet {
		String desktopPath1 = System.getProperty("user.home") + "/Desktop";
		String desktopPath = desktopPath1.replace("\\", "/");
		 int  resizlen;	int hashcode;
	    public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException 
		{
	    	doPost(request,response);
		}

		
	    public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
		{
	    	RequestDispatcher rp;
	    	String contentType = request.getContentType();
	    	
	    
	    	 int lent=0; FileInputStream fs=null; File fi=null;
	    	if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
	    		
	    		   java.sql.Connection con;
		    	    int len;
	                String query;
	                PreparedStatement pstmt;
	                DataInputStream in;
	                int formDataLength=0;
	     	in = new DataInputStream(request.getInputStream());
	    	
	    	formDataLength = request.getContentLength();
	    		byte dataBytes[] = new byte[formDataLength];
	    		int byteRead = 0;
	    		int totalBytesRead = 0;
	    		
	    		
	    		while (totalBytesRead < formDataLength) {
	    			byteRead = in.read(dataBytes, totalBytesRead,formDataLength);
	    			totalBytesRead += byteRead;
	    			}
	    		
	    		String file = new String(dataBytes);
	    		
	    		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
	    		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
	    		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
	    		int lastIndex = contentType.lastIndexOf("=");
	    		String boundary = contentType.substring(lastIndex + 1,contentType.length());
	    		int pos;
	    		
	    		pos = file.indexOf("filename=\"");
	    		pos = file.indexOf("\n", pos) + 1;
	    		pos = file.indexOf("\n", pos) + 1;
	    		pos = file.indexOf("\n", pos) + 1;
	    		int boundaryLocation = file.indexOf(boundary, pos) - 4;
	    		int startPos = ((file.substring(0, pos)).getBytes()).length;
	    		int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
	    	
	    		FileOutputStream fileOut = new FileOutputStream("D:/"+saveFile);
	    		fileOut.write(dataBytes, startPos, (endPos - startPos));
	    		fileOut.flush();
	    		fileOut.close();
	    			
	    		File ff=new File("D:/"+saveFile);
	    		System.out.println("The store befor=="+dataBytes.length);
	    		lent=dataBytes.length;
	    		BufferedImage image = ImageIO.read(ff);
	    	    
	    	    int clr=  image.getRGB(100,40); 
	    	    int  red   = (clr & 0x00ff0000) >> 16;
	    		
	    	    int  green = (clr & 0x0000ff00) >> 8;
	    	    int  blue  =  clr & 0x000000ff;
	    	     
	    	    File fil = new File("D:/"+saveFile);
             FileInputStream fis = new FileInputStream(fil);
             DataInputStream innv = new DataInputStream(fis);
             len = (int)fil.length();
             
             System.out.println("Image Stored");
		      System.out.println("Image Color="+red+"=="+green+"=="+blue);
			     
		      BufferedImage originalImage = null;
		     
			
				try {
					
					originalImage = ImageIO.read(fis);
				} catch (IOException e1) {

					e1.printStackTrace();
				}           
	    		   
             
              int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		BufferedImage resizedImage = new BufferedImage(200, 150, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 200, 150, null);
		g.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( resizedImage, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		
		try{
		    String strDirectoy ="D:/NIR";
		  
		    boolean success = (new File(strDirectoy)).mkdir();
		    if (success) {
		      
		    } 
		    
		    FileOutputStream fos = new FileOutputStream("D:/NIR/"+saveFile);
		    fos.write(imageInByte);
		    fos.close();
		     
		}catch (Exception e){
		      System.err.println("Error: " + e.getMessage());
	    }
		try{
	   fi = new File("D:/NIR/"+saveFile);
       fs = new FileInputStream(fi);
       hashcode=fi.hashCode();
       
        resizlen = (int)fi.length();
		}catch(Exception e){
			
		}
		
		if(resizlen!=0||fi!=null||fs!=null){
		
			    try{
			    	
			      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			      con=DriverManager.getConnection("jdbc:odbc:nirimgtbl","sa","test");
			      Statement s=con.createStatement();
			      query = ("insert into imagedownload values(?,?,?,?,?,?)");
			      pstmt = con.prepareStatement(query);
                  pstmt.setLong(1,hashcode);
                  pstmt.setBinaryStream(2,fs,resizlen); 
                  pstmt.setString(3,saveFile); 
                  pstmt.setInt(4, red);
                  pstmt.setInt(5, green);
                  pstmt.setInt(6, blue);
                  pstmt.executeUpdate();
                  con.close();
                  pstmt.close();
                  in.close();
                  
                    rp=request.getRequestDispatcher("again_image.jsp");
					rp.forward(request, response);
			    	
			    }catch(Exception e1){
			    	e1.printStackTrace();
			    	
			    }
		}  
	    	
		}

		}

	}

	
	

