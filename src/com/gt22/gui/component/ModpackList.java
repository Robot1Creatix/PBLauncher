package com.gt22.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JList;
import com.creatix.projectbronze.minecraft.Modpack;
import com.gt22.gui.render.ModpackRenderer;

public class ModpackList extends JList<Modpack>
{
	public ModpackList()
	{
		super(Modpack.getModpacks());
		setCellRenderer(new ModpackRenderer());
		setBackground(new Color(59, 59, 59));
	}
}
