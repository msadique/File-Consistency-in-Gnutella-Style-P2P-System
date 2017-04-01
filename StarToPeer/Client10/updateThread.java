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



public class updateThread {
	Timer timer;
    
	public updateThread(int seconds) {    //in constructor make list of files in input folder
        timer = new Timer();
        timer.schedule(new RemindTask(),0, seconds*1000);
	}

	// this class runs in every second set by client
    class RemindTask extends TimerTask {
    	public void run() {

	if (Client.queryList.size()>10)
	{
		for(int i =0;i<Client.queryList.size()-10;i++)
		{
			Client.queryList.remove(0);
		}
	}	
//	System.out.println("SAM");
    }
}
}
