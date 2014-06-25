/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DoganCan
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
/* Incoming client object */
public class ClientListener implements Runnable {
	/* Communication socket of with client */
	private Socket socket;
	private String client_name;
	private Thread servant;
	private Scanner pr;
	private DataOutputStream pw;
	
	public ClientListener(Socket socket) {
		this.socket = socket;
		servant = new Thread(this);
		servant.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			pr = new Scanner(socket.getInputStream());
			pw = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
 		while(!servant.isInterrupted()) {
			if(false){}
                        else{
                            
                            String line = null;
                            Query queries = new Query();
                            
                            while((line = pr.nextLine()) != null) {		
                                System.out.println(line);
                                if(line.length() == 0)
                                    break;

                                String body = StringEscapeUtils.unescapeHtml4(line);
                                String tempBody = "" , tempKey = "", key,value;
                                
                                String[] tokens = body.split("&");
                                if(body.contains("&")) {		
                                        for(String token : tokens) {
                                            if(token.length() == 0)
                                                continue;
                                            String split [] =  token.split("=");
                                            if (token.contains("=")){
                                                key = split[0];
                                                value = split[1];
                                                if(!queries.contain(key))
                                                        queries.newQuery(key, value);
                                                tempKey = key;
                                            }
                                            else{
                                                key = tempKey;
                                                tempBody = queries.get(key)+"\n"+body;
                                                queries.newQuery(key, tempBody);
                                            }
                                        }
                                } 
                                else{
                                    if(tempKey != null)
                                        queries.newQuery(tempKey,queries.get(tempKey)+body);
                                    else{
                                        String split [] =  body.split("=");
                                        if(body.contains("=")){
                                            key = split[0];
                                            value = split[1];
                                            if(!queries.contain(key))
                                                    queries.newQuery(key, value);
                                            tempKey = key;
                                        }
                                    }
                                }
                            }
                            String query_type = queries.get("QUERY");
                            String message = queries.get("MESSAGE");
                            
                            
                            System.err.println(client_name + " is connected!");
                            if (query_type == null){
                                continue;
                            }
                            if(query_type.equals("Connection")) {

                                    File f = new File("wwwroot\\");
                                    ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
                                    String dirs = "";
                                    for (int i = 0 ; i < names.size();i++){
                                        dirs += names.get(i) + " ";
                                    }
                                    Query query = new Query();
                                    query.newQuery("QUERY", "Directory");
                                    query.newQuery("MESSAGE", dirs);
                                    String content = query.toString() + "\n\n";


                                    String header = "HTTP/1.0 200 OK\n" + "Allow: GET\n" +
                                        "MIME-Version: 1.0\n" + "Server: HMJ Basic HTTP Server\n" +
                                        "Content-Type: text/plain\n" + "Content-length: " +
                                        content.length() + "\n\n";

                                    try {
                                        pw.writeBytes(header);
                                        pw.writeBytes(content);
                                        pw.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                            }                           
                            if(query_type.equals("DirectoryResponse")){  
                              
                                String [] messageParser = message.split(" ");
                           
                                Directory dr = new Directory (messageParser, pw);
                                dr.setVisible(true);
                            }  
                            if (query_type.equals("FileRequest")){
                                    Query query = new Query();
                                    String fileContent = null;
                                    try {
                                        String filename = "wwwroot\\" + message;
                                        fileContent = new String(Files.readAllBytes(Paths.get(filename)));
                                        System.out.println("babananas;ldfkaskd;f");
                                        query.newQuery("QUERY", "FileResponse");
                                        query.newQuery("MESSAGE", fileContent);
                                        query.newQuery("FILENAME", message);
                                        String content3 = query.toString() + "\n\n";
                                         try {
                                            //pw.writeBytes(header);
                                            pw.writeBytes(content3);
                                            pw.flush();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                            }
                            if(query_type.equals("FileResponse")) {
                                String filename = queries.get("FILENAME");
                                PrintWriter writer = null;
                                try {
                                    writer = new PrintWriter("wwwroot\\"+filename);
                                    writer.write(message);     
                                    writer.flush();
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if(writer != null)
                                    writer.close();
                            }
			}
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		servant.interrupt();
	}
	
}