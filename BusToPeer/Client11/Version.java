/*******************************************************************************
 *  FILE NAME    : Version.Java
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


public class Version implements Serializable { //uses JAVA Serializable package

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

    	public final String getAuthor() {  //Return the name of the filename set by void setFileName(String fileName) function.
		return author;
	}
	public final void setAuthor(String author) {  //Set fileName
		this.author = author;
	}
	public final String getFileName() {  //Return the name of the filename set by void setFileName(String fileName) function.
		return fileName;
	}
	public final void setFileName(String fileName) {  //Set fileName
		this.fileName = fileName;
	}
	public Version( Version version)
	{
		this.author = version.author;
		this.valid = version.valid;
		this.tTR = version.tTR;
		this.ipAddr = version.ipAddr;
		this.port = version.port;
		this.fileName = version.fileName;
		this.version = version.version;
		this.lastTime =version.lastTime;
		this.cTTR = version.cTTR;
	}
	public Version()
	{}
	public final int getCTTR() {  //Returns the port number.
                return cTTR;
        }
        public final void setCTTR(int cTTR) {  //Sets the port number to the port value passed to the function.
                this.cTTR = cTTR;
        }
	public final int getTTR() {  //Returns the port number.
                return tTR;
        }
        public final void setTTR(int tTR) {  //Sets the port number to the port value passed to the function.
                this.tTR = tTR;
        }
	
	public final long getTime() {  //Returns the port number.
                return lastTime;
        }
        public final void setTime(long time) {  //Sets the port number to the port value passed to the function.
                this.lastTime = time;
        }

	public final long getValid() {  //Returns the port number.
                return valid;
        }
        public final void setValid(int valid) {  //Sets the port number to the port value passed to the function.
                this.valid = valid;
        }
	public final double getVersion() {  //Return the name of the filename set by void setFileName(String fileName) function.
		return version;
	}
	public final void setVersion(double version) {  //Set fileName
		this.version = version;
	}
	 private String ipAddr;
	         private int port;
		 private String fileName;
	private String author;
	private int valid;
	private int tTR; // TTL in sec;
	private int cTTR;   //counter for TTR
	private long lastTime;  // Last Modified Time
	private double version;
}


