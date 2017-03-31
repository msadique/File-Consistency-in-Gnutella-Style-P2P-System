/*******************************************************************************
 *  FILE NAME    : Query.Java
 *
 *  DESCRIPTION  : This class have all information for Query.
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;


public class Query implements Serializable { //uses JAVA Serializable package

	public final String getIpAddr() { //Returns IP-Address InetAddress object which is set by void setIpAddr(InetAddress ipAddr)  function.
		return ipAddr;
	}
	public final void setIpAddr(String ipAddr) { //et InetAddress object IP-Address using the passed parameter ipAddr.
		this.ipAddr = ipAddr;
	}
	public final int getPort() {  //Returns the port number.
		return port;
	}
	public final void setPort(int port) {  //Sets the port number to the port value passed to the function.
		this.port = port;
	}
	public final int getMessageID() {  //Returns the MessageID.
		return messageID;
	}
	public final void setMessageID(int messageID) {  //Sets the MessageID.
		this.messageID = messageID;
	}
	public final String getFileName() {  //Return the name of the filename set by void setFileName(String fileName) function.
		return fileName;
	}
	public final void setFileName(String fileName) {  //Set fileName
		this.fileName = fileName;
	}
	public final QueryType getQueryType() { //Return RequestType enum value identifying the type of request made by peers.
		return queryType;
	}
	public final void setQueryType(QueryType queryType) { //Set the RequestType enum value identifying the type of request made by peers.
		this.queryType = queryType;
	}
	public Query( Query query)
	{
		this.queryType = query.queryType;
		this.messageID = query.messageID;
		this.tTL = query.tTL;
		this.fileName = query.fileName;
		this.ipAddr = query.ipAddr;
		this.port = query.port;
		/*--------- start change ----------*/ 
		this.time =query.time;
		this.version =query.version;
		/*--------- end change ----------*/ 
	}
	public final int getTtl() {  //Returns time to leave value.
                return tTL;
        }
        public final void setTtl(int tTl) {  //Sets time to leave value
                this.tTL = tTl;
        }
	
	public final long getTime() {  //Returns last modified time
                return time;
        }
        public final void setTime(long time) {  //Sets the last modified time
                this.time = time;
        }
        /*--------- start change ----------*/ 
	public final double getVersion() {  //Return the version number of a particular file
		return version;
	}
	public final void setVersion(double Version) {  //sets the version number of a particular file
		this.version = Version;
	}
 public final int getTTR() {  //Returns the Time to Refresh value.
                return TTR;
        }
        public final void setTTR(int tTR) {  //Sets Time to Refresh value
                this.TTR = tTR;
        }
	 public final String getAuthor() {  //Return the Author of a particular file
                return author;
        }
        public final void setAuthor(String author) {  //Set the Author of a particular file
                this.author = author;
        }
        /*--------- end change ----------*/ 
	public Query(){}	
	private int TTR;
	/*--------- start change ----------*/ 
	private String author;
	private QueryType queryType;
	/*--------- end change ----------*/
	private int messageID;
	private int tTL;
	private String fileName;
	private String ipAddr;
	private int port;
	/*--------- start change ----------*/
	private long time;
	private double version;
	/*--------- end change ----------*/
}


