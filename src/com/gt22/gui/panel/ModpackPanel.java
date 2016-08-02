package com.gt22.gui.panel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.minecraft.Modpack;

public class ModpackPanel extends PanelBase
{
	public static final int xsize = 655, ysize = 162;
	JLabel back = new JLabel(), logo = new JLabel(), name = new JLabel(), ver = new JLabel(), mcver = new JLabel();
	Modpack m;
	public ModpackPanel(Image back, Modpack m)
	{
		this.m = m;
		this.back.setIcon(new ImageIcon(back));
		initFont();
		initComponents();
	}
	
	private void initComponents()
	{
		logo.setIcon(m.logo);
		logo.setBorder(BorderFactory.createEmptyBorder(17, 17, 17, 0));
		name.setText(m.name);
		ver.setText(m.version);
		mcver.setText(m.mcversion);
		name.setBorder(BorderFactory.createEmptyBorder(0, 170, 120, 0));
		ver.setBorder(BorderFactory.createEmptyBorder(0, 170, 0, 0));
		mcver.setBorder(BorderFactory.createEmptyBorder(130, 170, 0, 0));
		add(back, 0, 0);
		add(logo, 0, 0);
		add(name, 0, 0);
		add(ver, 0, 0);
		add(mcver, 0, 0);
		setComponentZOrder(back, 4);
		setComponentZOrder(name, 1);
		setComponentZOrder(ver, 2);
		setComponentZOrder(mcver, 3);	
	}
	
	private void initFont()
	{
		Font f = new Font("Minecraft Evenings", Font.PLAIN, 25);
		name.setFont(f);
		ver.setFont(f);
		mcver.setFont(f);
	}
}
