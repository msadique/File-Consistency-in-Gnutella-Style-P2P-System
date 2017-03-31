/*******************************************************************************
 *  FILE NAME    : updateThread.java
 *
 *  DESCRIPTION  : his class invoke its constructor when its object is created, 
 a value one second is passed for checking the status of the file at the client side whether 
 it has undergone any change. If it has undergone any change then it update the file status at the server side as well. 
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
 import java.util.StringTokenizer;


public class versionControl {
	Timer timer;
    
	public versionControl(int seconds) {    //in constructor make list of files in input folder
        timer = new Timer();
	System.out.println("Hello");
        timer.schedule(new RemindTask2(),0, seconds*3000);
	timer.schedule(new RemindTask3(),0,1000);
	}

	// this class runs in every second set by client
    class RemindTask2 extends TimerTask {
    	public void run() {
	    vControl();
	}
    }
    class RemindTask3 extends TimerTask {
    	public void run() {
	    vRefress();
	}
    }
	public void vRefress(){
	for(int i =0;i<Client.versionList.size();i++)
	if(Client.versionList.get(i).getAuthor().equals("Slave"))
	{
		if(Client.versionList.get(i).getCTTR()<1)
		{
			System.out.println(" FileName :"+Client.versionList.get(i).getFileName()+"  Refress with Master");
			Client.versionList.get(i).setCTTR(Client.versionList.get(i).getTTR());
			Query query = new Query();
			query.setVersion(Client.versionList.get(i).getVersion());
		        query.setFileName(Client.versionList.get(i).getFileName());
         		query.setIpAddr(Client.ClientIP);
         		query.setPort(Client.ClientPort);
         		query.setQueryType(QueryType.Refress);
               		try
                	{
                        	{
                               	Socket socket=new Socket(Client.versionList.get(i).getIpAddr(),Client.versionList.get(i).getPort());
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                oos.writeObject(query);
                                socket.close();
                        }
                	}
                	catch(Exception e)
                	{
                	//        System.out.println(e.getMessage());
               		 }
                
		}
		else
		{
			Client.versionList.get(i).setCTTR((Client.versionList.get(i).getCTTR()-1));
		}

	}	
	
}
    public void vControl()
    {
	int mainFlag=0;
//	System.out.println("----SAM----");
	Path currentRelativePath = Paths.get("");
	String cpath = currentRelativePath.toAbsolutePath().toString();
	 try {
   	    
		Client.versionList2.clear();
            	File clientFolder= new File(cpath+"/input_files/");
            	File[] clientFiles=clientFolder.listFiles();
            for(File file:clientFiles)
            {
				int flag=1;
	//			if(file.getName().startsWith("."))System.out.println("---Not include----"+file.getName());
				if (!(file.getName().startsWith("."))){
				for(int i=0;i<Client.versionList.size();i++)
				{
                    			if( file.isFile()&& file.getName().equals( Client.versionList.get(i).getFileName() ))
                    			{
						if(Client.versionList.get(i).getTime() !=  (file.lastModified()/1000))
						{
							Client.versionList.get(i).setTime(file.lastModified()/1000);
							Client.versionList.get(i).setVersion((Client.versionList.get(i).getVersion()+.1));
							flag=0;
		    					mainFlag=1;
							invalidateSlaves(file.getName(),Client.versionList.get(i).getVersion());// Invalidation
							break;
						}
						else
						{
							flag=0;
							break;
						}
					}
				}
				if(flag==1)
				{
					Version ver = new Version();
					ver.setFileName(file.getName());
                    			ver.setAuthor("Master");
					ver.setTime(file.lastModified()/1000);
					ver.setVersion(1.0);
					ver.setTTR(60);
					ver.setValid(1);
					ver.setIpAddr(Client.ClientIP);
					ver.setPort(Client.ClientPort);
					Client.versionList2.add(ver);
		    			mainFlag=1;
		    		
				}}
            }// file ended
	    clientFolder= new File(cpath+"/output_files/");
            clientFiles=clientFolder.listFiles();
            for(File file:clientFiles)
            {
                                int flag=1;
        //                      if(file.getName().startsWith("."))System.out.println("---Not include----"+file.getName());
                                if (!(file.getName().startsWith("."))){
                                for(int i=0;i<Client.versionList.size();i++)
                                {
                                        if( file.isFile()&& file.getName().equals( Client.versionList.get(i).getFileName() ))
                                        {
                                        	flag=0;
                                                break;
                                        }
                                }
                                if(flag==1)
                                {
                                        Version ver = new Version();
                                        ver.setFileName(file.getName());
                                        ver.setAuthor("Slave");
                                        ver.setTime(file.lastModified()/1000);
                                        ver.setVersion(1.0);
                                        ver.setTTR(60);
                                        ver.setValid(1);
                                        ver.setIpAddr(Client.ClientIP);
                                        ver.setPort(Client.ClientPort);
                                        Client.versionList2.add(ver);
                                        mainFlag=1;

                                }}
            }// file ended

			if(mainFlag==1){
			   // Client.versionList.clear();
			    Client.versionList.addAll(Client.versionList2);
	    		PrintWriter writer = new PrintWriter("tmp.txt", "UTF-8");
			System.out.println("Version Size :-"+Client.versionList.size());
			for(int i=0;i<Client.versionList.size();i++)
			{
				writer.println(Client.versionList.get(i).getFileName()+" "+
					Client.versionList.get(i).getAuthor()+" "+
			 		 Client.versionList.get(i).getTime()+" "+
			  		  Client.versionList.get(i).getVersion()+" "+
			   		  Client.versionList.get(i).getTTR()+" "+
 						Client.versionList.get(i).getValid()+" "+
                                                Client.versionList.get(i).getIpAddr()+" "+
						Client.versionList.get(i).getPort());

					  } 
			writer.close();
	Process p = Runtime.getRuntime().exec("mv tmp.txt fileVersion.txt");
	System.out.println("mv1 tmp.txt fileVersion.txt");
		}
     	} // try 
        catch(FileNotFoundException ex) {
        }
 	catch(Exception e)
        {
	     System.out.println(e.getMessage());
	}
    }
public void invalidateSlaves(String fileName,double ver)
{

	 Query query = new Query();
         query.setFileName(fileName);
         query.setIpAddr(Client.ClientIP);
         query.setPort(Client.ClientPort);
         query.setQueryType(QueryType.Invalid);
	 query.setTTR(Client.TTR);
	 query.setVersion(ver);
                for(int i=0;i<Client.peers.size();i++){
                try
                {
                        {
                                Socket socket=new Socket(Client.peers.get(i).getIpAddr(),Client.peers.get(i). getPort());
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                oos.writeObject(query);
                                socket.close();
                        }
                }
                catch(Exception e)
                {
                //        System.out.println(e.getMessage());
                }
                }
}

}
