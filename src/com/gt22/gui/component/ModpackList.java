package com.gt22.gui.component;

import java.awt.Color;
import javax.swing.JList;
import com.creatix.projectbronze.minecraft.Modpack;
import com.gt22.gui.panel.ModpackPanel;

public class ModpackList extends JList<Modpack>
{
	public ModpackList()
	{
		super(Modpack.getModpacks());
		setCellRenderer((l, modpack, i, selected, f) -> new ModpackPanel(modpack, selected));
		setBackground(new Color(59, 59, 59));
	}
}
