/*******************************************************************************
 *  FILE NAME    : cRequest.Java
 *
 *  DESCRIPTION  : This class have all information for Request.
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
		this.time =query.time;
		this.version =query.version;
	}
	public final int getTtl() {  //Returns the port number.
                return tTL;
        }
        public final void setTtl(int tTl) {  //Sets the port number to the port value passed to the function.
                this.tTL = tTl;
        }
	
	public final long getTime() {  //Returns the port number.
                return time;
        }
        public final void setTime(long time) {  //Sets the port number to the port value passed to the function.
                this.time = time;
        }

	public final double getVersion() {  //Return the name of the filename set by void setFileName(String fileName) function.
		return version;
	}
	public final void setVersion(double Version) {  //Set fileName
		this.version = Version;
	}
 public final int getTTR() {  //Returns the port number.
                return TTR;
        }
        public final void setTTR(int tTR) {  //Sets the port number to the port value passed to the function.
                this.TTR = tTR;
        }
	 public final String getAuthor() {  //Return the name of the filename set by void setFileName(String fileName) function.
                return author;
        }
        public final void setAuthor(String author) {  //Set fileName
                this.author = author;
        }

	public Query(){}	
	private int TTR;
	private String author;
	private QueryType queryType;
	private int messageID;
	private int tTL;
	private String fileName;
	private String ipAddr;
	private int port;
	private long time;
	private double version;
}


