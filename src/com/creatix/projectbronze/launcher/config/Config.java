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
import com.creatix.projectbronze.launcher.utils.FileUtils;

public class Config
{

	public static char sep = File.separatorChar;
	private static File configFile;
	private static Properties prop = new Properties();

	public static int MinRam, MaxRam;
	public static String mcDir;

	public static void initConfig()
	{
		createConfig();
		loadConfig();
	}

	private static void createConfig()
	{
		configFile = new File(Core.coreDir, "config.pbconfig");
		try
		{
			FileUtils.initFile(configFile);
		}
		catch (IOException e)
		{
			Core.log.error("Unable to create config file");
			e.printStackTrace(Core.log);
		}
		try
		{
			genConfig();
		}
		catch (IOException e)
		{
			Core.log.error("Unable to write config");
			e.printStackTrace(Core.log);
		}
	}

	private static void loadConfig()
	{
		try
		{
			InputStream in = new FileInputStream(configFile);
			prop.load(in);
			MinRam = new Integer(prop.getProperty("MinRam"));
			MaxRam = new Integer(prop.getProperty("MaxRam"));
			mcDir = prop.getProperty("MinecraftFolder");
		}
		catch (Exception e)
		{
			Core.log.error("Unable to read config");
			e.printStackTrace(Core.log);
		}
	}

	private static void deleteConfig()
	{
		if (configFile.exists())
			configFile.delete();
	}

	private static void regenConfig()
	{
		deleteConfig();
		createConfig();
	}

	private static void genConfig() throws IOException
	{
		OutputStream outs = new FileOutputStream(configFile);
		prop.setProperty("MinRam", "1024");
		prop.setProperty("MaxRam", "2048");
		prop.setProperty("MinecraftFolder", new File(Core.coreDir, "Minecraft").getAbsolutePath());
		prop.store(outs, "BronzeLauncher Config");
	}
}
