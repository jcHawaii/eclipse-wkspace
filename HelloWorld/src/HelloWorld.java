import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class HelloWorld {	

	
	public static void main(String[] args)  throws IOException
    {    
	   
		 
	    Date date = new Date(); 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat fr = new SimpleDateFormat("ddMMMMyyyy-hhmmss");  
	    
		String strcontroller = RandomController();
		
        // initialize hl7 string
        String str = "MSH|^~\\&|ABC|AVAILITY|HEALTHPLAN|WELLCARE|"+ df.format(date) +"||ORU^R01|" + strcontroller + "|P|2.3\n"
        		+ "PID|1||"+ RandomMRN() +"^^^ABC^^1||CAT^CLAW^A||19610924|F|||PO BOX 123^^CAPTAIN COOK^HI^96825||8081112|||||109258872\n"
        		+ "PV1|1|O|||||123456^WHO^JOHN^M (ABC)^^^^^NPI^^^^NPI|||||||||||OP||||||||||||||||||||||||||20201118000000\n"
        		+ "NTE|1|L|Result is negative.";
        
        
        //0x0b
        //0x1c0x0d
        
        TimerTask task = new TimerTask() {
	        public void run() {
	        	try {
	        		System.out.println("Task performed on: " + new Date());  
	            	// attach a file to FileWriter
	                FileWriter fw
	                    = new FileWriter("/Users/joemcostes/Public/" + strcontroller + "-"  + fr.format(date) + ".txt");

	                // read each character from string and write
	                // into FileWriter
	                for (int i = 0; i < str.length(); i++)
	                    fw.write(str.charAt(i));

	                System.out.println("Successfully written to disk");
	                System.out.println(tcpSend("192.168.4.11", 12345, 500, str.toString()));
	                
	                // close the file
	                fw.close();
	                
	                
	                
	            }
	            catch (Exception e) {
	                e.getStackTrace();
	            }
	        }
	    };
	    Timer timer = new Timer("Timer");
	    
	    long delay = 1000;
	    timer.schedule(task, delay);
	    
    }
	
	
	public static String tcpSend(String ip, int port, int timeout, String content)
	{
	     String ipaddress = ip;
	     int portnumber = port;
	     String sentence;
	     String modifiedSentence;
	     Socket clientSocket;
	     System.out.println(content);
         
	     
	     try
	     {
	         clientSocket = new Socket(ipaddress, portnumber);
	         DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	         BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	         outToServer.writeBytes(content + '\n');
	         clientSocket.setSoTimeout(timeout);
	         modifiedSentence = inFromServer.readLine();
	         clientSocket.close();
	             outToServer.close();
	         inFromServer.close();
	     }
	     catch (Exception exc)
	     {
	          modifiedSentence = "TCP Connection - " + exc.getMessage();
	     }
	          return modifiedSentence;
	}
	

	private static String RandomController() {
		// TODO Auto-generated method stub
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder(12);
		Random random = new Random();
		for (int i = 0; i < 12; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();

	}
	
	private static String RandomMRN() {
		// TODO Auto-generated method stub
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder(7);
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();

	}

	
}

	
