package com.creatix.projectbronze.minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import com.creatix.projectbronze.launcher.config.Config;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.utils.FileUtils;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class ModpackChecker
{
	private static File list = new File(Core.coreDir, "modpacklist.txt");

	public static File getModpackList()
	{
		File archive = new File(Core.tmpfolder, "mpl.zip");
		try
		{
			FileUtils.download(new URL("http://projectbronze.comli.com/launcher/mpl.zip"), archive);
		}
		catch (IOException e)
		{
			Core.log.error("Unable to download modpack list");
			e.printStackTrace(Core.log);
		}
		try
		{
			FileUtils.unzip(new File(Config.mcDir), archive);
		}
		catch (IOException e)
		{
			Core.log.error("Unable to unzip modpack list");
			e.printStackTrace(Core.log);
		}
		return list;
	}
	
	public static JsonObject getModpackDef(Modpack m)
	{
		try
		{
			return new JsonParser().parse(FileUtils.createReader(new File(m.folder, "mpdef.json"))).getAsJsonObject();
		}
		catch (Exception e)
		{
			Core.log.error("Unable to read modpack defenition for modpack " + m.name);
			e.printStackTrace();
		}
		return null;
	}
}
