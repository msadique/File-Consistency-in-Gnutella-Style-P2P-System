/*******************************************************************************
 *  FILE NAME    : NPeers.Java
 *
 *  DESCRIPTION  : This class have all information for Request.
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ****************************************************************************/
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;


public class NPeers implements Serializable { //uses JAVA Serializable package

	public NPeers(String ipAddr, int port)
	{
		this.ipAddr = ipAddr;
		this.port = port;
	}
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
	
	private String ipAddr;
	private int port;
}


