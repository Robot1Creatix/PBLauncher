package com.gt22.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import com.creatix.projectbronze.launcher.utils.Const;

public class MainFrame extends JFrame
{
	private MainPanel mainpanel;
	public MainFrame()
	{
		setSize(Const.startwidth, Const.startheight);
		setTitle("Project bronze launcher");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}
	
	private void initComponents()
	{
		add(mainpanel = new MainPanel(this), BorderLayout.CENTER);
	}
}
