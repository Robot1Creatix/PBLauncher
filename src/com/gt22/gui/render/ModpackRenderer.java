package com.gt22.gui.render;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.minecraft.Modpack;
import com.gt22.gui.panel.ModpackPanel;

public class ModpackRenderer extends JPanel implements ListCellRenderer<Modpack>
{
	private static BufferedImage back;
	public ModpackRenderer()
	{
		setOpaque(true);
		if(back == null)
		{
			try
			{
				back = ImageIO.read(getClass().getResourceAsStream("/mpback.png"));
			}
			catch (IOException e)
			{
				Core.log.error("Unable to load background for mopack");
				e.printStackTrace(Core.log);
			}
		}
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Modpack> list, Modpack value, int index, boolean isSelected, boolean cellHasFocus)
	{
		return new ModpackPanel(back, value);
	}
}
