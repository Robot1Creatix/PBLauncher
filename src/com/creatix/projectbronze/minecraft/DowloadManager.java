package com.creatix.projectbronze.minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
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

public class DowloadManager
{
	private static File list = new File(Core.coreDir, "mp.list");
	private static boolean dowloaded = false;

	public static void dowloadNatives()
	{
		
	}
	
	public static File getModpackList()
	{
		if (!dowloaded)
		{
			Core.log.debug("dowloading list", DowloadManager.class);
			if (list.exists())
			{
				Core.log.debug("deleting old list", DowloadManager.class);
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

	public static void downloadModpack(Modpack m)
	{
		Core.log.info("Dowloading modpack " + m.name);
		try
		{
			URL pack = new URL("http://projectbronze.comli.com/launcher/mps/" + m.id + "/pack.zip");
			File archive;
			FileUtils.download(pack, archive = new File(Core.tmpfolder, m.id + "pack.zip"));
			Core.log.info("Download");
			File dest = new File(Config.mcDir, m.id + File.separator + "pack");
			FileUtils.deleteDir(dest);
			Core.log.info("Deleted old version");
			dest.mkdirs();
			FileUtils.unzip(dest, archive);
			Core.log.info("Unzipping complete, dowload succes");
		}
		catch (IOException e)
		{
			Core.log.error("Unable to download modpack");
			e.printStackTrace(Core.log);
		}
	}

	public static void downloadDefs()
	{
		Core.log.debug("downloadDefs", DowloadManager.class);
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
			e.printStackTrace(Core.log);
		}
		return null;
	}

	public static JsonObject[] getModpackDefs()
	{
		ArrayList<String> modpacks = new ArrayList<String>();
		try
		{
			BufferedReader list = FileUtils.createReader(getModpackList());
			for (String tmp = list.readLine(); tmp != null; tmp = list.readLine())
			{
				modpacks.add(tmp);
			}
			JsonObject[] ret = new JsonObject[modpacks.size()];
			for (int i = 0; i < modpacks.size(); i++)
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

	public static String getDownloadedVersion(Modpack m)
	{
		String ret = null;
		File ver = new File(new File(m.folder, "pack"), "ver.txt");
		try
		{
			FileUtils.initFile(ver);
			BufferedReader r = FileUtils.createReader(ver);
			ret = r.readLine();
		}
		catch (IOException e)
		{
			Core.log.error("Unable to get version");
			e.printStackTrace(Core.log);
		}
		return ret;
	}
}
