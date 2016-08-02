package com.gt22.gui.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.minecraft.Modpack;

public class ModpackComponent extends JComponent
{
	
	private static Modpack modpack;
	public ModpackComponent(Modpack m)
	{
		modpack = m;
	}
	
	public static Modpack getModpack()
	{
		return modpack;
	}
}
