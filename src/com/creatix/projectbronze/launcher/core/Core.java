package com.creatix.projectbronze.launcher.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.creatix.projectbronze.launcher.config.Config;
import com.creatix.projectbronze.launcher.log.Logger;
import com.creatix.projectbronze.minecraft.Modpack;

public class Core {	
	public static boolean debug, onlyconsole;
	public static boolean root;
	public static List<String> l1 = new ArrayList<String>();
	public static List<String> l2 = new ArrayList<String>();
	public static void main(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].equals("debug"))
				debug = true;
			if(args[i].equals("only-console"))
				onlyconsole = true;
		}
		Config.initConfig();
		Logger.initLogFile();
		Modpack.initModpacks();
		BronzeLauncher.start(debug, onlyconsole);
		
	}
	
	private static String initializeSystem()
	{
		String ret = "";
		if(System.getProperty("os.name").toLowerCase().contains("windows"))//1
			ret += "Windows::";
		if(System.getProperty("os.name").toLowerCase().contains("linux") || System.getProperty("os.name").toLowerCase().contains("unix") || System.getProperty("os.name").toLowerCase().contains("steam"))
			ret += "Linux::";
		if(System.getProperty("os.name").toLowerCase().contains("mac"))
			ret += "macOS::";
		ret += System.getProperty("os.version")+"::";//2
		if(System.getProperty("os.arch").toLowerCase().contains("i3"))//3
			ret += "x32::";
		if(System.getProperty("os.arch").toLowerCase().contains("64"))
			ret += "x64::";
		ret += System.getProperty("java.version")+"::";//4
		ret += System.getProperty("java.home")+"::";//5
		ret += System.getProperty("user.name")+"::";//6
		ret += System.getProperty("user.home");//7
		return ret;
	}
	public static String getSystemProperty(SystemProperty prop)
	{
		String[] props = initializeSystem().split("::");
		switch(prop){
			case OSNAME:{
				return props[0]; 
			}
			case OSVERSION:{
				return props[1];
			}
			case OSARCH:{
				return props[2];
			}
			case JVERSION:{
				return props[3];
			}
			case JHOME:{
				return props[4];
			}
			case UNAME:{
				return props[5];
			}
			case UHOME:{
				return props[6];
			}
		}
		return null;
	}
	public static void print(Object ...objects ){
		String ret = "";
		for(Object o : objects)
		{
			ret += o+" ";
		}
		Logger.put(objects);
		System.out.println(ret);
	}
	public static boolean isWindows()
	{
		return getSystemProperty(SystemProperty.OSNAME).equals("Windows");
	}
}
