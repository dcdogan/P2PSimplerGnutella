
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javax.net.SocketFactory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DoganCan
 */
public class client {
    
    private String client_name;
    private String ipAdress;
    private Thread servant;
    private Socket socket;
    
    // output
    private DataOutputStream pw;
    
    private Scanner pr;

    public static boolean isConnected = false;
  
    private String workingDirectory;
	
    
    public client (String name) throws IOException{
        
        this.client_name = name;
        this.workingDirectory = "";
	
        
        SocketFactory factory = SocketFactory.getDefault();
        Socket s = null;
        
        s = factory.createSocket("192.168.1.10", 4911);
        socket = s;
        pw = new DataOutputStream (new BufferedOutputStream (socket.getOutputStream()));
				
        pr = new Scanner(socket.getInputStream());
        
        if(socket != null) {
            
            System.out.println("Connected!");
            isConnected = true;				
            servant = new Thread(new OpenThread(client_name, pw, pr));
            servant.start();
        
        }
        
    }
    
    public client (String name , String ipAdress) throws IOException{
        this.ipAdress = ipAdress;
        this.client_name = name;
        this.workingDirectory = "";
		
        
        SocketFactory factory = SocketFactory.getDefault();
        Socket s = null;
        
        s = factory.createSocket(ipAdress, 4911);
        socket = s;
        pw = new DataOutputStream (new BufferedOutputStream (socket.getOutputStream()));
        pr = new Scanner(socket.getInputStream());
        
        if(socket != null) {
            System.out.println("Connected!");
				
            isConnected = true;
				
            servant = new Thread(new OpenThread(client_name, pw, pr));
				
            servant.start();
        
        }
        
    }
    
  
    
}
