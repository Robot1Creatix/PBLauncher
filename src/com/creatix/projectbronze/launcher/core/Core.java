package com.creatix.projectbronze.launcher.core;

import java.io.File;
import java.io.IOException;
import com.creatix.projectbronze.launcher.config.Config;
import com.creatix.projectbronze.launcher.log.LogStream;
import com.creatix.projectbronze.launcher.log.Logger;
import com.creatix.projectbronze.launcher.utils.FileUtils;
import com.creatix.projectbronze.launcher.utils.JavaVersion;
import com.creatix.projectbronze.minecraft.Modpack;
import com.creatix.projectbronze.minecraft.DowloadManager;

public class Core {	
	public static boolean debug, onlyconsole;
	public static boolean root;
	public static File coreDir, tmpfolder;
	public static final LogStream log = new LogStream();
	public static void main(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].equals("debug"))
				debug = true;
			if(args[i].equals("only-console"))
				onlyconsole = true;
		}
		coreDir = new File(getSystemProperty(SystemProperty.UHOME) + File.separatorChar + "ProjectBronze");
		tmpfolder = new File(coreDir, "Temp");
		Logger.initLogFile();
		log.debug(initializeSystem(), Core.class);
		Config.initConfig();
		DowloadManager.downloadDefs();
		Modpack.initModpacks();
		BronzeLauncher.start(debug, onlyconsole);
		
	}
	
	private static void initFolders()
	{
		try
		{
			FileUtils.initFile(coreDir);
			FileUtils.initFile(tmpfolder);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static String initializeSystem()
	{
		String ret = "";
		if(System.getProperty("os.name").toLowerCase().contains("windows"))//0
		{
			ret += "Windows::";
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux") || System.getProperty("os.name").toLowerCase().contains("unix") || System.getProperty("os.name").toLowerCase().contains("steam"))
		{
			ret += "Linux::";
		}
		else if(System.getProperty("os.name").toLowerCase().contains("mac"))
		{
			ret += "macOS::";
		}
		else
		{
			ret += "undefined::";
		}
		ret += System.getProperty("os.version")+"::";//1
		if(System.getProperty("os.arch").toLowerCase().contains("i3"))//2
		{
			ret += "x32::";
		}
		else if(System.getProperty("os.arch").toLowerCase().contains("64"))
		{
			ret += "x64::";
		}
		else
		{
			ret += "undefined::";
		}
		ret += System.getProperty("java.version")+"::";//3
		ret += System.getProperty("java.home")+"::";//4
		ret += System.getProperty("user.name")+"::";//5
		ret += System.getProperty("user.home") + "::";//6
		ret += System.getProperty("line.separator");//7
		return ret;
	}
	public static String getSystemProperty(SystemProperty prop)
	{
		String[] props = initializeSystem().split("::");
		return props[prop.toInt()];
	}

	public static boolean isValidJava()
	{
		return !JavaVersion.createJavaVersion(getSystemProperty(SystemProperty.JVERSION)).isOlder("1.8");
	}

	public static boolean isWindows()
	{
		return getSystemProperty(SystemProperty.OSNAME).equals("Windows");
	}
}
