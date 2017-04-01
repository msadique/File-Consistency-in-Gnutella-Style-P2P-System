
/*******************************************************************************
 *  FILE NAME    : peerClientThread.java
 *
 *  DESCRIPTION  : This class take the client requestType and calls related functions 
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/
import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class peerClientThread extends Thread
{
	Socket socket;
	Path currentRelativePath = Paths.get("");  // Get current path
	String cpath = currentRelativePath.toAbsolutePath().toString();
	
	public peerClientThread()
	{
		
	}
	//This function accept the client request and call the respective functions
	public void run() 
	{
		Scanner scanner=new Scanner(System.in);
		while(true)
		{
			System.out.println("\n\n\t1. Lookup Files");
			/*--------- start change ----------*/ 
			if(Client.PNP == 1)System.out.println("\t2. Refress Files");
			System.out.println("\t3. Exit ");
			/*--------- end change ----------*/ 
			System.out.print("\tEnter :- ");
			int input = scanner.nextInt();
			if( input == 1 )
			{
			/*--------- start change ----------*/ 	
	        		scanner=new Scanner(System.in);
				System.out.print("\n\n Enter File name :- ");
        			 String input1 = scanner.next();
				if(Client.PNP==1){
				 int flag=0;
				for(int k =0;k<Client.versionList.size();k++)
				    if(Client.versionList.get(k).getFileName().equals(input1))
					flag=1;
				if(flag==1){
				    System.out.println("File is Already Exist");
				    return;
				}}
				/*--------- end change ----------*/ 
				for(int j=0;j<Client.Repeat;j++){
				RequestPeerList(input1);  // This function access all the peer names and information associated with it from the server.
				try {
    					Thread.sleep(Client.WaitTime*1000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
    					Thread.currentThread().interrupt();
				}	
			//}
				if(Client.peersResponse.size()!=0){	
				
				Client.avgTime = Client.tTotalTime/Client.peersResponse.size();
	 			System.out.println( "---------------------------------------------------------------------------------" );
        			System.out.println( "Average Response Time from '"+Client.peersResponse.size()+ "' Clients in Milliseconds: " +(Client.avgTime/1000000) );
	 			System.out.println( "---------------------------------------------------------------------------------" );
			
			//if( input == 2 ){
				/*--------- start change ----------*/ 
				 System.out.println("************    Download file    ***********");
				for(int i=0;i<Client.peersResponse.size();i++){
					System.out.print(" "+(i+1)+".   Client"+(i+1)+"   IP:"+Client.peersResponse.get(i).getIpAddr()+"  Port:"+Client.peersResponse.get(i).getPort()+ "   Response Time :- "+(Client.peersResponse.get(i).getTime()/1000000)+"ms");
					if(Client.PNP==1)
						System.out.println("Author:"+Client.peersResponse.get(i).getAuthor()+"  Version:-"+Client.peersResponse.get(i).getVersion());
				}
	        		scanner=new Scanner(System.in);
                		System.out.print("\n\nSelect Client to Download (Please Enter No.) :-  ");
                		input1 = scanner.next();
				int pos = Integer.parseInt(input1);
				DownloadFile(pos-1);  // This function access all the peer names and information associated with it from the server.
				}else
				System.out.print("File Not Found");
			}
			}
			else if (Client.PNP ==1 && input == 2 )
			{
				int flag=1,i,count=0,j=0;
				System.out.println("List of invalid Files");
                		for( i=0;i<Client.versionList.size();i++)
                		{
                    			if(Client.versionList.get(i).getValid()==0)//&&Client.versionList.get(i).getAuthor().equals("Slave"))
                    			{
						System.out.println(" "+(++j)+". FileName: "+Client.versionList.get(i).getFileName()+"   IP:"+Client.versionList.get(i).getIpAddr()+"  Port:"+Client.versionList.get(i).getPort());
						count++;
					}
				}
				
				if(count==0)
					continue;
				
				 System.out.print("\n\nSelect File to Download (Please Enter No.) OR Press '0' to Update All :-  ");
				scanner=new Scanner(System.in);
                                int input2 = Integer.parseInt(scanner.next());
				 Query query = new Query();
				if(input2 == 0)
				{
					 for(i = 0;i<Client.versionList.size();i++)
                                	{
                                        	if(Client.versionList.get(i).getValid()==0)//&&Client.versionList.get(i).getAuthor().equals("slave"))
                                        	{
                                        		Client.versionList.get(i).setValid(1);
			               			query.setFileName(Client.versionList.get(i).getFileName());
                					query.setIpAddr(Client.versionList.get(i).getIpAddr());
                					query.setPort(Client.versionList.get(i).getPort());
							try
                					{
							receiveFile(query); 
							}
                					catch(Exception e)
                					{
                        					System.out.println(e.getMessage());
                					}
						}
					}
				}
				else if (input2<=count)
				{	j=1;
					 for( i=0;i<Client.versionList.size();i++)
                              		 {
                                        	if(Client.versionList.get(i).getValid()==0)//&&Client.versionList.get(i).getAuthor().equals("Slave"))
                                        	{
							if( j == input2){
								
								break;
							}j++;
						}	
					}
							Client.versionList.get(i).setValid(1);
		               		query.setFileName(Client.versionList.get(i).getFileName());
                			query.setIpAddr(Client.versionList.get(i).getIpAddr());
                			query.setPort(Client.versionList.get(i).getPort());
					try
                                        {
                                              receiveFile(query);
                                        }
                                        catch(Exception e)
                                        {
                                              System.out.println(e.getMessage());
                                        }
				}

				
			}
			/*--------- end change ----------*/ 
			else
			{	
				System.exit(0);
			}
		}
	}
	private void RequestPeerList(String input)
	{
		
		System.out.println("************    Lookup for file   ***********");
		Client.sTime = System.nanoTime();
		Client.avgTime=0;
		 Client.tTotalTime =0;
		Client.peersResponse.clear();
		Query query = new Query();
		Client.messageID = ThreadLocalRandom.current().nextInt(Client.ClientPort, (Integer.MAX_VALUE/2)); 
		query.setMessageID(Client.messageID);
		query.setTtl(Client.TTL);
		query.setIpAddr(Client.ClientIP);
		query.setPort(Client.ClientPort);
		query.setFileName(input);
		query.setQueryType(QueryType.Request);
		Client.queryList.add(query);

		sendObject2AllPeer(query);
	}
	private void DownloadFile(int inP)
	{
		int pos = inP;
		try{
			/*--------- start change ----------*/ 
		if(Client.PNP ==1){
		System.out.println("Version :- "+ Client.peersResponse.get(pos).getVersion());
			try
			{
			 	        Version ver = new Version();
                                        ver.setFileName(Client.peersResponse.get(pos).getFileName( ));
                                        ver.setAuthor("Slave");
                                        ver.setTime(System.currentTimeMillis() / 1000L);
                                        ver.setVersion(Client.peersResponse.get(pos).getVersion());
                                        ver.setTTR(Client.peersResponse.get(pos).getTTR());
                                        ver.setValid(1);
                                        ver.setIpAddr( Client.peersResponse.get(pos).getIpAddr());
                                        ver.setPort(Client.peersResponse.get(pos).getPort());
                                        ver.setCTTR(Client.peersResponse.get(pos).getTTR());
                                        Client.versionList.add(ver);
					  PrintWriter writer = new PrintWriter("tmp.txt", "UTF-8");

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
        				System.out.println("mv2 tmp.txt fileVersion.txt");
			}
			catch(IOException ioe)
			{
	        		System.err.println("IOException: " + ioe.getMessage());
			}
	}//PULL and PUSH	
		    long lStartTime = System.nanoTime();
			receiveFile(Client.peersResponse.get(pos));   // recieve file
			long lEndTime = System.nanoTime();
			/*--------- end change ----------*/ 
	 long output = lEndTime - lStartTime;
        File file =new File( cpath + "/output_files/" + Client.peersResponse.get(pos).getFileName( ) );
        if( file.exists( ) )
        {
                double bytes = file.length( );
                double kilobytes = ( bytes / 1024 );
                double megabytes = ( kilobytes / 1024 );
                DecimalFormat df = new DecimalFormat( );
                df.setMaximumFractionDigits( 2 );
                System.out.println( "---------------------------------------------------------------------------------" );
        System.out.println( "Transfer Time in milliseconds: " + output / 1000000 );

        if( megabytes < 1 )
         System.out.println( "\nDownloaded file :" + Client.peersResponse.get(pos).getFileName() + " File size : " + df.format( kilobytes)+"KB");
        else
        System.out.println("\nDownloaded file :"+Client.peersResponse.get(pos).getFileName()+" File size : "+df.format(megabytes)+"MB");
        System.out.println("\nPerformance speed "+ df.format((megabytes*1000000*1000)/output)+"MB/sec");
                System.out.println("---------------------------------------------------------------------------------");
        }
	 	}
		catch(Exception e)
                {
                        System.out.println(e.getMessage());
                }
	

	}	
	private void sendObject2AllPeer(Query query)
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
			//System.out.println(e.getMessage());
		}
		}
		
	}

	private void sendObject2Peer(Query query){
		try 
		{
			socket=new Socket(query.getIpAddr(),query.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(query);
		} 
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
		
	
	//@SuppressWarnings("resource")
	
	//This function receive the file and its content from the client which has the reuested file.	
	private int receiveFile(Query query) throws UnknownHostException, IOException
	{
			
			query.setQueryType(QueryType.Download);
			//peerConversation=true;
			System.out.println("sending request to peer");
			sendObject2Peer(query);
			FileOutputStream OS=null; 
			InputStream IS=null;
			// have to either get file or file not found
			IS=socket.getInputStream();
			OS= new FileOutputStream(cpath+"/output_files/"+query.getFileName());
			int bytesRead=0;
			byte[] b=new byte[512000]; 
			while((bytesRead=IS.read(b, 0, b.length))!=-1)
			{ 
				OS.write(b, 0, bytesRead);	
			}
			OS.close();
			return 0;
	}		
}
