
import com.sun.corba.se.spi.activation.Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import javax.swing.SwingWorker;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DoganCan
 */
public class p2pmain {
 
   
    private static ServerSocket ss;
	
    private static List<ClientListener> list;
	
    public static String root_directory = "wwwroot/";
    
    
    public static void main (String [] args) throws IOException{
        
        final MainScreen ms =  new MainScreen();

        ms.setVisible(true);
        
        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                ss = new ServerSocket(4911);
			System.out.println("Server Started on  port 4911!");
			while(!Thread.interrupted()) {
				/* Get socket for client */
				System.err.println("Listening..");
				try {
					Socket socket = ss.accept();
					System.err.println("Someone connected! : " + socket.getInetAddress().toString());
					list.add(new ClientListener(socket));
				} catch(Exception e) {
					//e.printStackTrace();
				}
			} 
                
                
                return null;
            }
            
        }.execute();
        
        
        
       
        
        
        
    } 
    
    
    
    
    
    
}
