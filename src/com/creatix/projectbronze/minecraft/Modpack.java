package com.creatix.projectbronze.minecraft;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.creatix.projectbronze.launcher.config.Config;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.core.SystemProperty;

public class Modpack {
	public static List<Modpack> list = new ArrayList<Modpack>();
	public static final char sep = File.separatorChar;
	public String id, name, version, mcversion;
	public URL url;
	public File folder;
	private static String tmpfolder = Core.getSystemProperty(SystemProperty.UHOME) + sep + "ProjectBronze" + sep + "Temp";
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
		this.folder = new File(Config.mcDir+Config.sep+id);
	}
	public static void createfolder(Modpack modpack)
	{
		try {
			if(!modpack.folder.exists())
				modpack.folder.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void createfolder()
	{
		try {
			if(!this.folder.exists())
				this.folder.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void download(Modpack modpack)
	{
		try{
			 InputStream in = new BufferedInputStream(modpack.url.openStream());
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 byte[] buf = new byte[1024];
			 int n = 0;
			 while ((n=in.read(buf)) != -1)
			 {
			    out.write(buf, 0, n);
			 }
			 out.close();
			 in.close();
			 byte[] response = out.toByteArray();
			 FileOutputStream fos = new FileOutputStream(tmpfolder+sep+modpack.id+".zip");
			 fos.write(response);
			 fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public  void download()
	{
		if(new File(tmpfolder+sep+id+".zip").exists())
			new File(tmpfolder+sep+id+".zip").delete();
		try{
			 InputStream in = new BufferedInputStream(url.openStream());
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 byte[] buf = new byte[1024];
			 int n = 0;
			 while ((n=in.read(buf)) != -1)
			 {
			    out.write(buf, 0, n);
			 }
			 out.close();
			 in.close();
			 byte[] response = out.toByteArray();
			 FileOutputStream fos = new FileOutputStream(tmpfolder+sep+id+".zip");
			 fos.write(response);
			 fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void add(Modpack ...modpack)
	{
		for(Modpack m : modpack)
		{
			list.add(m);
		}
	}
	public static void initModpacks()
	{
		add(circle, wom, tfg);
	}
	public static final Modpack circle = new Modpack("circle", "Circle", "...", "1.7.10", "http://localhost/circle.zip");
	public static final Modpack wom = new Modpack("wom", "World Of Magic", "...", "1.7.10", "http://localhost/wom.zip");
	public static final Modpack tfg = new Modpack("tfg", "TerraFirmaGreg", "...", "1.7.10", "http://localhost/tfg.zip");
}
