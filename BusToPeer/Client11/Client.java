/*******************************************************************************
 *  FILE NAME    : Server.Java
 *
 *  DESCRIPTION  : This class has the Client side main() function, which intern class 
				   updateThread Class object to keep checking the status of the files 
				   present at the Client associated and update automatically when 
				   there is a change in the file system. It also create peerClientThread 
				   and peerServerThread class   object to take and process the client request.
				   This class contains the main() function, it generate a server thread 
				  
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
public class Client {
	protected static int ClientPort;
	protected static String ClientIP;
	protected static String ClientName;
	protected static int messageID;
	public final static ArrayList<NPeers> peers = new ArrayList<NPeers>(); 	
	public final static ArrayList<Query> peersResponse = new ArrayList<Query>(); 	
	public final static ArrayList<Query> queryList = new ArrayList<Query>(); 
	public final static ArrayList<Version> versionList = new ArrayList<Version>(); 
	public final static ArrayList<Version> versionList2 = new ArrayList<Version>(); 
	protected static int TTL=10;	
	protected static int flushTime=1;	
	protected static int Repeat=1;
	protected static int WaitTime = 1;
	protected static int TTR =10;
	protected static int PNP =1;
		static ServerSocket ClientServerSock;
	static Socket requestClientSoc;
	Socket socket;
	protected static long sTime=0,lTime=0,tTotalTime=0,avgTime=0;	
	//boolean peerConversation=false;
	
	public static void main(String[] args) throws IOException
	{
		TTL = ThreadLocalRandom.current().nextInt(100, 1000);
		System.out.println("Input Format : java Client <Client Name> <TTL> <Flush Time>");
		String fileName ="../conf.txt",line;
		versionList.clear();
		versionList2.clear();
		Loadfile();  // Load file version to array list
		String clientIP,clientPort,ip,port;
		new updateThread(1);
		if(args[4].equals("on")){
			PNP =1;
			new versionControl(1);
			System.out.println("\n---------------------\nPULL & PUSH ON\n---------------------\n");
		}
		else{
		PNP =0;
			System.out.println("\n---------------------\nPULL & PUSH OFF\n---------------------\n");
		}
	        try {

			FileReader fileReader = new FileReader(fileName);
        	    	PrintWriter writer = new PrintWriter("file.txt", "UTF-8");
            		// Always wrap FileReader in BufferedReader.
            		BufferedReader bufferedReader = new BufferedReader(fileReader);
            		int flag = 1;
            		while((line = bufferedReader.readLine()) != null) {
                		StringTokenizer st = new StringTokenizer(line);
                		while (st.hasMoreElements()) {
                			ip = (String)st.nextElement();
					port = (String)st.nextElement();
					String str = (String)st.nextElement();
					if(flag == 1&&str.equals("available")){
						str = "taken"; 
						flag=0;
						Client.ClientIP = ip;
						Client.ClientPort = Integer.parseInt(port);
					}
					else 
					{
						NPeers peer = new NPeers(ip,Integer.parseInt(port));
						peers.add(peer);
					}
                   		    	System.out.println(ip+" "+port+" "+str);
                    			writer.println(ip+" "+port+" "+str);
                		}
			}
            		writer.close();
            		// Always close files.
            		bufferedReader.close();
			Process p = Runtime.getRuntime().exec("mv file.txt "+fileName);
        	}
        	catch(FileNotFoundException ex) {
            	System.out.println("Unable to open file '" +fileName + "'");
        	}	
		for(int i=0;i<peers.size();i++)
		{
			System.out.println((i+1)+". " + peers.get(i).getIpAddr() + peers.get(i). getPort());
		}	
                System.out.println(ClientIP+"  "+ClientPort);
		Client.ClientName = args[ 0 ];
		Client.TTL = Integer.parseInt(args[1]);
		Client.flushTime =  Integer.parseInt(args[2]);
		Client.TTR = (Integer.parseInt(args[3])) * 60 ;  //Convert from minutes into secs
		new updateThread(Client.flushTime);
		try 
		{
			peerClientThread requestThread = new peerClientThread();
        		requestThread.start();
			
			ClientServerSock = new ServerSocket(ClientPort);
			System.out.println("\n Client listening on "+ClientPort);
			while( true )
			{ //wait for connection
				requestClientSoc=ClientServerSock.accept();
				peerServerThread thread=new peerServerThread(requestClientSoc, ClientName);
				thread.start();
			}
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
		
	public void run() throws IOException 
	{
	}
	public static void Loadfile()
	{	
		versionList.clear();
		try {
            		FileReader fileReader = new FileReader("fileVersion.txt");
           		BufferedReader bufferedReader = new BufferedReader(fileReader);
            		String line;
          		String fileName,author;
            		while((line = bufferedReader.readLine()) != null) {
                		StringTokenizer st = new StringTokenizer(line);
                		while ( st.hasMoreElements() ) {
                    			Version ver = new Version();
                    			ver.setFileName((String)st.nextElement());
                    			ver.setAuthor((String)st.nextElement());
                                        ver.setTime(Long.parseLong((String)st.nextElement()));
                                        ver.setVersion(Double.parseDouble((String)st.nextElement()));
                                        ver.setTTR(Integer.parseInt((String)st.nextElement()));
                                        ver.setValid(Integer.parseInt((String)st.nextElement()));
                                        ver.setIpAddr((String)st.nextElement());
                                        ver.setPort(Integer.parseInt((String)st.nextElement()));
                                        ver.setCTTR(ver.getTTR());
                                	versionList.add(ver);
                		}
            		}
		 	bufferedReader.close();
		}
	 	catch(Exception e)
        	{
             		System.out.println(e.getMessage());
        	}

	}
}

