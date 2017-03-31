/*******************************************************************************
 *  FILE NAME    : peerServerThread.java
 *
 *  DESCRIPTION  : This class is used to make client act as a server by communicating with other client member in the connected cluster.
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;

public class peerServerThread extends Thread
{
	Socket requestClientSoc;
	String clientName;
	Socket socket;
	Query peerQuery;
	ObjectInputStream ois=null;
	FileInputStream fi=null;
	BufferedInputStream BS=null;
	OutputStream OS=null;
	Path currentRelativePath = Paths.get("");
	String cpath = currentRelativePath.toAbsolutePath().toString();
	public peerServerThread(Socket requestClientSocket, String ClientName)
	{
		this.requestClientSoc=requestClientSocket;
		this.clientName=ClientName;
	}
	
	public void run()
	{
		readRequestObject();
		if(peerQuery.getQueryType()== QueryType.Request){
			int flag =1;
			if(peerQuery.getTtl()<=0)
				return;					//if Time to live is less than 1 discard message
			else
				peerQuery.setTtl(peerQuery.getTtl()-1);   // decrease Time to live by 1

			for(int i=0;i<Client.queryList.size();i++){
				if(Client.queryList.get(i).getMessageID()==(peerQuery.getMessageID())){
					flag=0;
					break;
				}
			}
			if(flag==1){
				Client.queryList.add(peerQuery);
					
				//Forward request to all peers
				Query pquery =  new Query(peerQuery);
				pquery.setIpAddr(Client.ClientIP);
				pquery.setPort(Client.ClientPort);		
				sendObject2Peer(pquery);
				System.out.println("Query Type:- "+pquery.getQueryType());
				sendResponse(peerQuery); // Send Response to server if file found
				
					//Search begin in local directory
			}
		}
		else if(peerQuery.getQueryType()== QueryType.Response){
			if(Client.messageID == peerQuery.getMessageID())
			{
				Client.lTime=System.nanoTime();
				long x = Client.lTime-Client.sTime;
				Client.tTotalTime +=(x);
				peerQuery.setTime(x);
				Client.peersResponse.add(peerQuery);
			}
			else
			{
				for(int i=0;i<Client.queryList.size();i++){
                  	        	if(Client.queryList.get(i).getMessageID()==(peerQuery.getMessageID())){
                               			try{
                                			socket=new Socket(Client.queryList.get(i).getIpAddr(),Client.queryList.get(i).getPort());
                               				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                			oos.writeObject(peerQuery);
                                			socket.close();
                        			}
                				catch(Exception e)
                				{
                        				System.out.println(e.getMessage());
                				}
						break;
					}
                        	}

			}
		}	
		else if(peerQuery.getQueryType()== QueryType.Download){
			sendRequestFile(peerQuery);		
		}
		else if(Client.PNP ==1&&peerQuery.getQueryType()== QueryType.Invalid)
		{
		    inValidate(peerQuery);
		}
		else if(Client.PNP==1 &&peerQuery.getQueryType()== QueryType.Refress)
		{
			refress(peerQuery);	
		}

		try{
                	requestClientSoc.close();
                   }
                   catch(Exception e)
                   {
                   	System.out.println(e.getMessage());
                   }

	
	
	}
	private void refress(Query query1)
	{
                System.out.println("Slave Call this function");

		int flag=1;
                for(int i=0;i<Client.versionList.size();i++)
                {
               	  if(Client.versionList.get(i).getFileName().equals(peerQuery.getFileName()))
                  {
			if(Client.versionList.get(i).getVersion() != peerQuery.getVersion())
                    	{
			Query query = new Query();
		        query.setFileName(query1.getFileName());
         		query.setIpAddr(Client.ClientIP);
         		query.setPort(Client.ClientPort);
         		query.setQueryType(QueryType.Invalid);
               		 try
                	{
                        	{
                                Socket socket=new Socket(peerQuery.getIpAddr(),peerQuery.getPort());
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
		}
	

	private void inValidate(Query query)
	{
	    	int flag=1;
		for(int i=0;i<Client.versionList.size();i++)
		{
		    if(Client.versionList.get(i).getFileName().equals(peerQuery.getFileName()))
		    {
			flag = 0;
			Client.versionList.get(i).setValid(0);
			Client.versionList.get(i).setVersion(query.getVersion());
			Client.versionList.get(i).setIpAddr(query.getIpAddr());
			Client.versionList.get(i).setPort(query.getPort());
			
			try{
			PrintWriter writer = new PrintWriter("tmp.txt", "UTF-8");
			    for(int k=0;k<Client.versionList.size();k++)
				    {
					        writer.println(Client.versionList.get(k).getFileName()+" "+
					            Client.versionList.get(k).getAuthor()+" "+
					                Client.versionList.get(k).getTime()+" "+
						            Client.versionList.get(k).getVersion()+" "+
						                Client.versionList.get(k).getTTR()+" "+
							            Client.versionList.get(k).getValid()+" "+
						                	Client.versionList.get(k).getIpAddr()+" "+
							            	Client.versionList.get(k).getPort());
									
				    }
			            writer.close();
				            Process p = Runtime.getRuntime().exec("mv tmp.txt fileVersion.txt");
					            System.out.println("mv tmp.txt fileVersion.txt");
						                    
		         } // try
		        catch(FileNotFoundException ex) {
			            }
			        catch(Exception e)
				            {
						             System.out.println(e.getMessage());
		    }
		    }

		}
	}
	//This function return the file to the requesting client.
	private void sendRequestFile(Query query)
	{
                String FileName=query.getFileName();
		int flag=0;
                System.out.println("FileName:-"+FileName);
                File clientFolder= new File(cpath+"/input_files/");
                File[] clientFiles=clientFolder.listFiles();
		File file1 = null;
		for(File file:clientFiles)
                {
                        if( file.isFile()&& file.getName().equals( FileName ))
                        {
				file1=file;
				flag=1;
				break;
			}
                }
		if(flag==0)
		{
			clientFolder= new File(cpath+"/output_files/");
                	clientFiles=clientFolder.listFiles();
                	for(File file:clientFiles)
                	{
                        	if( file.isFile()&& file.getName().equals( FileName ))
                        	{
                                	file1=file;
                                	flag=1;
                                	break;
                        	}
                	}
		}
		if(flag==1)
		{ 
			try{
				sendFile( file1);
                           }
                           catch(Exception e)
                           {
                                System.out.println(e.getMessage());
                           }

		}
		else
			System.out.println("File not Found");

	}

	private void sendResponse(Query query)
        {
	        Query pQuery = new Query();
		int qflag=1;
		String FileName=query.getFileName();
		System.out.println("FileName:-"+FileName);
		File clientFolder= new File(cpath+"/input_files/");
		File[] clientFiles=clientFolder.listFiles();
		for(File file:clientFiles)
		{
			if( file.isFile()&& file.getName().equals( FileName ))
			{
				qflag=0;
				break;
			}
		}
		if (qflag==1&&Client.PNP==1)
		{
			for(int i =0;i<Client.versionList.size();i++){
                	        if(Client.versionList.get(i).getFileName().equals(FileName)&&Client.versionList.get(i).getValid()==1)
                                {
                                	qflag=0;
                                }
			}
		}                	
		if(qflag==0)
		{
		 try{
                    	socket=new Socket(query.getIpAddr(),query.getPort());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        pQuery.setMessageID(query.getMessageID());
                        pQuery.setFileName(query.getFileName());
                        pQuery.setIpAddr(Client.ClientIP);
                        pQuery.setPort(Client.ClientPort);
                        pQuery.setTTR(Client.TTR);
                        pQuery.setQueryType(QueryType.Response);
                        if(Client.PNP==1){
                        	int i=0;
                                for(i =0;i<Client.versionList.size();i++){
                                	if(Client.versionList.get(i).getFileName().equals(FileName))
                                        {
                                        	pQuery.setTime(Client.versionList.get(i).getTime());
                                                pQuery.setAuthor(Client.versionList.get(i).getAuthor());
                                                pQuery.setVersion((Client.versionList.get(i).getVersion()));
                                                break;
                                        }
				}
                                System.out.println("Version No:- "+(Client.versionList.get(i).getVersion())+"  Ver "+pQuery.getVersion());
                        }
                        oos.writeObject(pQuery);
                        socket.close();
                        }
                        catch(Exception e)
                        {
		                System.out.println(e.getMessage());
                        }

		}			

	}
	
	private void sendFile(File file) throws IOException
	{
		byte[] by= new byte[512000];		
		fi=new FileInputStream(file);
		BS=new BufferedInputStream(fi);
		OS=requestClientSoc.getOutputStream();
		int bytesRead=0;
		while((bytesRead=BS.read(by, 0, by.length))!=-1)
		{
			OS.write(by, 0, bytesRead);
		}
		OS.close(); //have to close this port to end the outputstream, else will through 
					// stack exception
		System.out.println("File Sent");
		return;
	}
	//This function read the object related to the communication between the requesting client and processing client.

	private void readRequestObject()
	{
		try 
		{
				//System.out.println("came to reading the object");
				ois=new ObjectInputStream(requestClientSoc.getInputStream());
				peerQuery = (Query) ois.readObject();
				//System.out.println("read the request first");	
		} 
		catch (IOException | ClassNotFoundException e) 
		{
			
			e.printStackTrace();
		}
		
	}
	 private void sendObject2Peer(Query query)
        {
                        for(int i=0;i<Client.peers.size();i++){
                try
                {
                        {
                                socket=new Socket(Client.peers.get(i).getIpAddr(),Client.peers.get(i). getPort());
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

