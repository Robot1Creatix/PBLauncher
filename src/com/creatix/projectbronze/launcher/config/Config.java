package com.creatix.projectbronze.launcher.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.core.SystemProperty;

public class Config {
	
	public static char sep = File.separatorChar;
	private static File configDir = new File(Core.getSystemProperty(SystemProperty.UHOME)+sep+"ProjectBronze");
	private static File configFile = new File(Core.getSystemProperty(SystemProperty.UHOME)+sep+"ProjectBronze"+sep+"config.pbconfig");
	private static Properties prop = new Properties();
	
	public static int MinRam, MaxRam;
	public static String mcDir;
	
	public static void initConfig()
	{
		if(!configFile.exists())
			createConfig();
		loadConfig();
	}
	private static void createConfig()
	{
		try{
			if(!configDir.exists())
				configDir.mkdirs();
			if(!configFile.exists())
				configFile.createNewFile();
			genConfig();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	private static void loadConfig(){
		try {
			InputStream in = new FileInputStream(configFile);
			prop.load(in);
			MinRam = new Integer(prop.getProperty("MinRam"));
			MaxRam = new Integer(prop.getProperty("MaxRam"));
			mcDir = prop.getProperty("MinecraftFolder");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void deleteConfig()
	{
		if(configFile.exists())
			configFile.delete();
	}
	private static void regenConfig()
	{
		deleteConfig();
		createConfig();
	}
	private static void genConfig(){
		OutputStream outs = null;
		try{
			outs = new FileOutputStream(configFile);
			prop.setProperty("MinRam", "1024");
			prop.setProperty("MaxRam", "2048");
			prop.setProperty("MinecraftFolder", System.getProperty("user.home")+sep+"ProjectBronze"+sep+"Minecraft");
			prop.store(outs, "BronzeLauncher Config");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
