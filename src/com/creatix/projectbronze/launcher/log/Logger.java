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
	public static final String ln = System.getProperty("line.separator");
	public static final char sep = File.separatorChar;
	private static File logFile;
	private static File logDir = new File(Core.coreDir, "Logs");
	public static void initLogFile()
	{
		try{
			FileUtils.recreateFile(logFile = new File(logDir, MiscUtils.getDataCode()+".log"));
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
