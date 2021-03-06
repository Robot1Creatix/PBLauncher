package com.gt22.gui.panel;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.minecraft.Modpack;

public class ModpackPanel extends PanelBase
{
	public static final int xsize = 655, ysize = 162;
	JLabel back = new JLabel(), logo = new JLabel(), name = new JLabel(), ver = new JLabel(), mcver = new JLabel();
	Modpack m;
	public ModpackPanel(Modpack m, boolean selected)
	{
		this.m = m;
		try
		{
			String filename = selected ? "mpback.png" : "mpbackSelected.png";
			this.back.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/" + filename))));
		}
		catch (IOException e)
		{
			Core.log.error("Unable to load backgound icon for modpacks");
			e.printStackTrace(Core.log);
		}
		initFont();
		initComponents();
	}
	
	private void initComponents()
	{
		
		logo.setIcon(new ImageIcon(createResizedCopy(m.icon, 57, 57, false)));
		name.setText(m.name);
		logo.setBorder(BorderFactory.createEmptyBorder(6, 5, 7, 0));
		name.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
		add(back, 0, 0);
		add(logo, 0, 0);
		add(name, 0, 0);
		setComponentZOrder(back, 2);
		setComponentZOrder(name, 1);
	}
	
	private void initFont()
	{
		Font f = new Font("Minecraft Evenings", Font.PLAIN, 25);
		name.setFont(f);
	}
	
	public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha)
    {
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;
    }
}
