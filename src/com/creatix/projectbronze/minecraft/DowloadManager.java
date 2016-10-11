package com.creatix.projectbronze.minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
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
import com.gt22.gui.GUI;

public class DowloadManager
{
	private static final File list = new File(Core.coreDir, "mp.list");
	private static boolean dowloaded = false;

	public static void dowloadNatives()
	{

	}

	public static File getModpackList()
	{
		if (!dowloaded)
		{
			Core.log.info("Downloading modpack list");
			if (list.exists())
			{
				list.delete();
				Core.log.info("Old list detected. Deleting");
			}
			try
			{
				Core.log.info("Staring dowload");
				FileUtils.initFile(list);
				FileUtils.download(new URL("http://projectbronze.comli.com/launcher/mp.list"), list, null, null).join();
			}
			catch (Exception e)
			{
				Core.log.error("Unable to download modpack list");
				e.printStackTrace(Core.log);
				return list;//To not display succes message
			}
			Core.log.info("List dowloaded");
			dowloaded = true;
		}
		return list;
	}

	public static void downloadModpack(Modpack m)
	{
		Core.log.info("Dowloading modpack " + m.name);
		File archive = new File(Core.tmpfolder, m.id + "pack.zip");
		URL pack;
		try
		{
			pack = new URL("http://projectbronze.comli.com/launcher/mps/" + m.id + "/pack.zip");
			FileUtils.download(pack, archive, () -> GUI.setFrameEnabled(false), () ->
			{
				Core.log.info("Downloaded modpack " + m.id);
				unzipModpack(m.id, archive);
				GUI.setFrameEnabled(true);
			});
		}
		catch (Exception e)
		{
			Core.log.error("Unable to dowload modpack archive");
			e.printStackTrace(Core.log);
			return;
		}

	}

	private static void unzipModpack(String id, File archive)
	{
		try
		{
			File dest = new File(Config.mcDir, id + File.separator + "pack");
			if (dest.exists())
			{
				FileUtils.deleteDir(dest);
				Core.log.info("Deleted old version");
			}
			dest.mkdirs();
			FileUtils.unzip(dest, archive);
			Core.log.info("Unzipping complete, dowload succes");
		}
		catch (IOException e)
		{
			Core.log.error("Unable to unzip modpack");
			e.printStackTrace(Core.log);
		}
	}

	public static void downloadDefs()
	{
		try
		{
			Core.log.info("Starting to dowload modpack definitions");
			BufferedReader list = FileUtils.createReader(getModpackList());
			for (String id = list.readLine(); id != null; id = list.readLine())
			{
				final String fid = id;//Becouse lambda cannot work with non-final variable
				File archive = new File(Core.tmpfolder, id + "def.zip");
				try
				{
					URL def = new URL("http://projectbronze.comli.com/launcher/mps/" + id + "/def.zip");
					FileUtils.download(def, archive, () -> GUI.setFrameEnabled(false), () ->
					{
						try
						{
							FileUtils.unzip(new File(Config.mcDir, fid), archive);
						}
						catch (IOException e)
						{
							Core.log.error("Unable to unzip defenition for modpack " + fid);
						}
						GUI.setFrameEnabled(true);
					});
				}
				catch (Exception e)
				{
					Core.log.error("Unable to dowload defenition for modpack " + fid + ", check your internet");
					e.printStackTrace(Core.log);
				}
			}
		}
		catch (IOException e)
		{
			Core.log.error("Unable to download modpack defenitions");
			e.printStackTrace(Core.log);
		}
		Core.log.info("Dowloaded modpack defenitions");
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
		catch (IOException e)
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
