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
		if(!new File(tmpfolder).exists())
			new File(tmpfolder).mkdirs();
		this.folder = new File(Config.mcDir+Config.sep+id);
		this.createfolder();
		this.createIcon();
		this.createDescription();
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
		if(!this.folder.exists())
			this.folder.mkdirs();
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
			 unzip();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void unzip(){
		try{
			File destDir = folder;
	        if (!destDir.exists()) {
	            destDir.mkdirs();
	        }
	        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(tmpfolder+sep+id+".zip"));
	        ZipEntry entry = zipIn.getNextEntry();
	        while (entry != null) {
	            String filePath = folder.getAbsolutePath() + File.separator + entry.getName();
	            if (!entry.isDirectory()) {
	                extractFile(zipIn, filePath);
	            } else {
	                File dir = new File(filePath);
	                dir.mkdir();
	            }
	            zipIn.closeEntry();
	            entry = zipIn.getNextEntry();
	        }
	        zipIn.close();
	        new File(tmpfolder+sep+id+".zip").delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
	
	private void createIcon()
	{
		try
		{
			logo = new ImageIcon(ImageIO.read(new File(folder, "logo.png")));
		}
		catch (IOException e)
		{
			Core.log.println("Unable to read icon for modpack " + name + ". Did modpack folder contain icon.png file?");
		}
	}
	
	private void createDescription()
	{
		try
		{
			description = readHtml(new File(folder, "desc.txt"), false);
		}
		catch (IOException e)
		{
			Core.log.println("Unable to read description for modpack " + name + ". Did modpack forlder containt desc.txt file");
		}
	}
	
	public static String readHtml(File file, boolean addPrefix) throws IOException
	{
		String src = FileUtils.readFile(file);
		if(src.startsWith("<!DOCTYPE html>") && !addPrefix)
		{
			src = src.substring(16);
		}
		if(src.startsWith("<html>") && !addPrefix)
		{
			src = src.substring(7);
		}
		if(!src.substring(0, src.length() - 1).endsWith("</html>"))
		{
			if(addPrefix){
				src = "<!DOCTYPE html><html>" + src;
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
		add(circle, wom, tfg);
	}
	public static final Modpack circle = new Modpack("circle", "Circle", "...", "1.7.10", "http://localhost/circle.zip");
	public static final Modpack wom = new Modpack("wom", "World Of Magic", "...", "1.7.10", "http://localhost/wom.zip");
	public static final Modpack tfg = new Modpack("tfg", "TerraFirmaGreg", "...", "1.7.10", "http://localhost/tfg.zip");
}
