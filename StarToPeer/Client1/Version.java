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

    	public final String getAuthor() {  //Return the author of a particular file
		return author;
	}
	public final void setAuthor(String author) {  //Set author of a particular filename
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
	public final int getCTTR() {  //Returns counter for TTR.
                return cTTR;
        }
        public final void setCTTR(int cTTR) {  //Sets counter for TTR
                this.cTTR = cTTR;
        }
	public final int getTTR() {  //Returns TTL in sec
                return tTR;
        }
        public final void setTTR(int tTR) {  //Sets TTL in sec
                this.tTR = tTR;
        }
	
	public final long getTime() {  //Returns last modified time of a file.
                return lastTime;
        }
        public final void setTime(long time) {  //Sets last modified time of a file.
                this.lastTime = time;
        }

	public final long getValid() {  //Returns 0 or 1 indicating whether a particular file is valid or not .
                return valid;
        }
        public final void setValid(int valid) {  //Sets 0 or 1 indicating whether a particular file is valid or not.
                this.valid = valid;
        }
	public final double getVersion() {  //Return the version number of a particular file.
		return version;
	}
	public final void setVersion(double version) {  //Set the version number of the file when modified.
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


