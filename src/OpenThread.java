
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DoganCan
 */
class OpenThread implements Runnable {

    String nickname;
    DataOutputStream pw;    
    private Scanner pr;
    int state;
    
    public OpenThread(String name, DataOutputStream printwriter, Scanner printreader) {
        
        pw = printwriter;
        
        pr = printreader;
        
        nickname = name;
        
        
    }

    @Override
    public void run() {
        state = 0;
        String content = "QUERY=Connection&USERNAME=" + nickname + "&ORIGIP=128.191.1.10&SUBMIT=Submit\n\n";
        
        String header = "POST / HTTP/1.0\n" + 
					"Connection: Keep-Alive\n" +
					"User-Agent: CS328-Servant\n" +
					"Accept-Language: en\n" +
					"Content-type: application/x-www-form-urlencoded\n" +
					"Content-length: " +
			        content.length() + "\n\n";
        try {
            pw.writeBytes(header);
            pw.writeBytes(content);
            pw.flush();
        } catch (IOException ex) {
            Logger.getLogger(OpenThread.class.getName()).log(Level.SEVERE, null, ex);
        }
	
        while(!Thread.interrupted()) {
		
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
            if (query_type == null){
                continue;
            }
            if(query_type.equals("Connection")) {
		nickname = queries.get("USERNAME");
            } 
            else if(query_type.equals("Directory")) {	
                
                System.out.println("Directory list request sent from the server!!!");
                
                
                String [] messageParser = message.split(" ");
                
                File f = new File("wwwroot\\");
                ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
                String dirs = "";
                for (int i = 0 ; i < names.size();i++){
                    dirs += names.get(i) + " ";
                }
                Query query = new Query();
                query.newQuery("QUERY", "DirectoryResponse");
                query.newQuery("MESSAGE", dirs);
                String content2 = query.toString() + "\n\n";

                String header2 = "HTTP/1.0 200 OK\n" + "Allow: GET\n" +
                    "MIME-Version: 1.0\n" + "Server: HMJ Basic HTTP Server\n" +
                    "Content-Type: text/plain\n" + "Content-length: " +
                    content2.length() + "\n\n";

                try {
                    
                    pw.writeBytes(content2);
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
              
                
                Directory dr = new Directory (messageParser, pw);
                dr.setVisible(true);
                
            }
            else if (query_type.equals("FileRequest")) {
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
                    pw.writeBytes(content3);
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                } catch (IOException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            else if(query_type.equals("FileResponse")) {
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
    
}
