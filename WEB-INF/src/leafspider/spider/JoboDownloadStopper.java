package leafspider.spider;

import java.io.File;
import java.util.ArrayList;

import net.matuschek.spider.WebRobot;

import leafspider.util.*;

public class JoboDownloadStopper implements net.matuschek.spider.WebRobotCallback
{
	File downloadFolder = null;
	int max;
	WebRobot robot = null;
	ArrayList supportedFileTypes;

	public JoboDownloadStopper(int max, ArrayList supportedFileTypes, String downloadFolder, WebRobot robot)
	{
		this.max = max;
		this.supportedFileTypes = supportedFileTypes;
		this.downloadFolder = new File(downloadFolder);
		this.robot = robot;
	}

	// ignore these methods
	public void webRobotDone()
	{}

//	static int numSupportedFilesDownloaded = 0;		// jmh 2006-07-14
	private int numSupportedFilesDownloaded = 0;
	/**
	 * Increases the number of retrieved documents and stops
	 * the robot, if the number has reached the maximum
	 */
	public void webRobotRetrievedDoc(String url, int size)
	{
//		int numSupportedFilesDownloaded = Util.getSupportedFileList(downloadFolder, supportedFileTypes).length;
		numSupportedFilesDownloaded++;
		
//		if (numSupportedFilesDownloaded >= (max - 1))		// jmh 2007-02-08
//		if (numSupportedFilesDownloaded >= (max-1))
		if (numSupportedFilesDownloaded >= (max))		// jmh 2010-08-20
		{
			Log.infoln("Maximum number of documents (" + max + ") reached.  Stopping Spider");
			robot.stopRobot();
		}
	}

	public void webRobotSleeping(boolean sleeping)
	{}

	public void webRobotUpdateQueueStatus(int length)
	{}
}