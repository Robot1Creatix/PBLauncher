package com.creatix.projectbronze.launcher.core;

import com.creatix.projectbronze.launcher.config.Config;
import com.creatix.projectbronze.launcher.log.LogStream;
import com.creatix.projectbronze.launcher.log.Logger;
import com.creatix.projectbronze.minecraft.Modpack;

public class Core {	
	public static boolean debug, onlyconsole;
	public static boolean root;
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
		Logger.initLogFile();
		Config.initConfig();
		Modpack.initModpacks();
		BronzeLauncher.start(debug, onlyconsole);
		
	}
	
	private static String initializeSystem()
	{
		String ret = "";
		if(System.getProperty("os.name").toLowerCase().contains("windows"))//1
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
		ret += System.getProperty("os.version")+"::";//2
		if(System.getProperty("os.arch").toLowerCase().contains("i3"))//3
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
		ret += System.getProperty("java.version")+"::";//4
		ret += System.getProperty("java.home")+"::";//5
		ret += System.getProperty("user.name")+"::";//6
		ret += System.getProperty("user.home");//7
		return ret;
	}
	public static String getSystemProperty(SystemProperty prop)
	{
		String[] props = initializeSystem().split("::");
		return props[prop.toInt()];
	}


	public static boolean isWindows()
	{
		return getSystemProperty(SystemProperty.OSNAME).equals("Windows");
	}
}
