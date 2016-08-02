package com.creatix.projectbronze.minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import com.creatix.projectbronze.launcher.config.Config;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.utils.FileUtils;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class ModpackChecker
{
	private static File list = new File(Core.coreDir, "mp.list");
	private static boolean dowloaded = false;

	public static File getModpackList()
	{
		if (!dowloaded)
		{
			if (list.exists())
			{
				list.delete();
			}
			try
			{
				FileUtils.initFile(list);
				FileUtils.download(new URL("http://projectbronze.comli.com/launcher/mp.list"), list);
			}
			catch (IOException e)
			{
				Core.log.error("Unable to download modpack list");
				e.printStackTrace(Core.log);
			}
			dowloaded = true;
		}
		return list;
	}

	public static void downloadDefs()
	{
		try
		{
			BufferedReader list = FileUtils.createReader(getModpackList());
			for (String id = list.readLine(); id != null; id = list.readLine())
			{
				try
				{
					URL def = new URL("http://projectbronze.comli.com/launcher/mps/" + id + "/def.zip");
					File archive;
					FileUtils.download(def, archive = new File(Core.tmpfolder, id + "def.zip"));
					FileUtils.unzip(new File(Config.mcDir, id), archive);
				}
				catch (IOException e)
				{
					Core.log.error("Unable to dowload defenition for modpack " + id + ", check your internet");
				}
			}
		}
		catch (IOException e)
		{
			Core.log.error("Unable to download modpack defenitions");
			e.printStackTrace(Core.log);
		}

	}

	public static JsonObject getModpackDef(File folder) throws IOException
	{
		return new JsonParser().parse(FileUtils.createReader(new File(folder, "mpdef.json"))).getAsJsonObject();
	}

	public static JsonObject getModpackDef(Modpack m)
	{
		try
		{
			return getModpackDef(m.folder);
		}
		catch (Exception e)
		{
			Core.log.error("Unable to read modpack defenition for modpack " + m.name);
			e.printStackTrace();
		}
		return null;
	}

	public static JsonObject[] getModpackDefs()
	{
		ArrayList<String> modpacks = new ArrayList<String>();
		try
		{
			BufferedReader list = FileUtils.createReader(getModpackList());
			for(String tmp = list.readLine(); tmp != null; tmp = list.readLine())
			{
				modpacks.add(tmp);
			}
			JsonObject[] ret = new JsonObject[modpacks.size()];
			for(int i = 0; i < modpacks.size(); i++)
			{
				ret[i] = getModpackDef(new File(Config.mcDir, modpacks.get(i)));
			}
			return ret;
		}
		catch (IOException e)
		{
			Core.log.error("Unable to get modpacks defenitions");
			e.printStackTrace(Core.log);
		}
		
		return null;
	}
}
