package com.creatix.projectbronze.minecraft;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import com.creatix.projectbronze.launcher.config.Config;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.core.SystemProperty;
import com.creatix.projectbronze.launcher.utils.FileUtils;

public class Modpack {
	public static final List<Modpack> modpacks = new ArrayList<Modpack>();
	public static final char sep = File.separatorChar;
	public String id, name, version, mcversion;
	public URL url;
	public File folder;
	public Icon logo;
	public String description;
	public Modpack(String id, String name, String version,String mcversion ,String url)
	{
		this.id = id;
		this.name = name;
		this.mcversion = mcversion;
		this.version = version;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(!new File(Core.tmpfolder).exists())
			new File(Core.tmpfolder).mkdirs();
		this.folder = new File(Config.mcDir+Config.sep+id);
		this.createfolder();
		/*try
		{
			FileUtils.download(this.url, new File(Core.tmpfolder+sep+id+".zip"));
		}
		catch (IOException e)
		{
			Core.log.error("Unable to dowload mopack");
			e.printStackTrace(Core.log);
		}
		try
		{
			FileUtils.unzip(folder, new File(Core.tmpfolder+sep+id+".zip"));
		}
		catch (IOException e)
		{
			Core.log.error("unable to dowload modpack");
			e.printStackTrace(Core.log);
		}
*/		this.createIcon();
		this.createDescription();
	}
	public static void createfolder(Modpack modpack)
	{
			if(!modpack.folder.exists())
				modpack.folder.mkdir();
	}
	public void createfolder()
	{
		if(!this.folder.exists())
			this.folder.mkdirs();
	}
	
	private void createIcon()
	{
		try
		{
			logo = new ImageIcon(ImageIO.read(new File(folder, "logo.png")));
		}
		catch (IOException e)
		{
			Core.log.warning("Unable to read icon for modpack " + name + ". Did modpack folder contain icon.png file?");
		}
	}
	
	private void createDescription()
	{
		try
		{
			description = readHtml(new File(folder, "desc.txt"), true, false);
		}
		catch (IOException e)
		{
			Core.log.warning("Unable to read description for modpack " + name + ". Did modpack forlder containt desc.txt file");
		}
	}
	
	public static String readHtml(File file, boolean addPrefix, boolean addDoctype) throws IOException
	{
		String src = FileUtils.readFile(file);
		if(src.startsWith("<!DOCTYPE html>") && !addDoctype)
		{
			src = src.substring(16);
		}
		if(src.startsWith("<html>") && !addPrefix)
		{
			src = src.substring(7);
		}
		if(!src.substring(0, src.length() - 1).endsWith("</html>"))
		{
			if(addPrefix)
			{
				src = "<html>" + src;
			}
			if(addDoctype)
			{
				src = "<!DOCTYPE html>" + src;
			}
			src += "</html>";
			src = src.replaceAll("\n", "<br>");
		}
		return src;
	}
	
	private static void add(Modpack ...modpack)
	{
		for(Modpack m : modpack)
		{
			modpacks.add(m);
		}
	}
	public static void initModpacks()
	{
		ModpackChecker.getModpackList();
		add(circle, wom, tfg);
	}
	public static final Modpack circle = new Modpack("circle", "Circle", "...", "1.7.10", "http://localhost/circle.zip");
	public static final Modpack wom = new Modpack("wom", "World Of Magic", "...", "1.7.10", "http://localhost/wom.zip");
	public static final Modpack tfg = new Modpack("tfg", "TerraFirmaGreg", "...", "1.7.10", "http://localhost/tfg.zip");
}
