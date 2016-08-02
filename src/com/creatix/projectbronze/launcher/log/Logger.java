package com.creatix.projectbronze.launcher.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.sound.sampled.Line;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.core.SystemProperty;
import com.creatix.projectbronze.launcher.utils.FileUtils;
import com.creatix.projectbronze.launcher.utils.MiscUtils;

public class Logger {
	public static final String ln = Core.getSystemProperty(SystemProperty.LINE);
	public static final char sep = File.separatorChar;
	private static File logFile;
	private static File logDir = new File(Core.coreDir, "Logs");
	public static void initLogFile()
	{
		try{
			logFile = new File(logDir, "PBLauncherLog-0-lasted.log");
			File log3 = new File(logDir, "PBLauncherLog-3.log"), log2 = new File(logDir, "PBLauncherLog-2.log"), log1 = new File(logDir, "PBLauncherLog-1.log");
			if(log3.exists())
			{
				log3.delete();
			}
			if(log2.exists())
			{
				log2.renameTo(log3);
			}
			if(log1.exists())
			{
				log1.renameTo(log2);
			}
			if(logFile.exists())
			{
				logFile.renameTo(log1);
			}
			FileUtils.initFile(logFile);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Should be used only by {@link LogStream}
		@param objects
	 */
	@Deprecated
	public static void put(Object ...objects)
	{
		for(Object o : objects)
		{
			try {
				FileUtils.addLine(logFile, o.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
