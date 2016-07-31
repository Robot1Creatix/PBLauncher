package com.creatix.projectbronze.launcher.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.Line;

import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.core.SystemProperty;
import com.creatix.projectbronze.launcher.utils.MiscUtils;

public class Logger {
	public static final String ln = System.getProperty("line.separator");
	public static final char sep = File.separatorChar;
	private static File logFile;
	private static File logDir = new File(
			Core.getSystemProperty(SystemProperty.UHOME) + sep + "ProjectBronze" + sep + "Logs");

	public static void initLogFile()
	{
		try{
			if(!logDir.exists())
				logDir.mkdirs();
			logFile = new File(logDir.getAbsolutePath()+sep+MiscUtils.getDataCode()+".log");
			if(logFile.exists())
				logFile.delete();
			logFile.createNewFile();
		}
		catch(Exception e){
			e.printStackTrace(Core.log);
		}
	}
	public static void put(Object ...objects)
	{
		for(Object o : objects)
		{
			try {
				FileWriter logW = new FileWriter(logFile);
				logW.append("["+MiscUtils.getTime()+"]"+o.toString()+ln);
				logW.close();
			} catch (Exception e) {
				e.printStackTrace(Core.log);
			}
		}
	}
}
