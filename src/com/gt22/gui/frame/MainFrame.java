package com.gt22.gui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import com.creatix.projectbronze.launcher.utils.Const;
import com.gt22.gui.component.ModpackComponent;
import com.gt22.gui.panel.MainPanel;

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
		setResizable(false);
	}
	
	private void initComponents()
	{
		add(mainpanel = new MainPanel(this), BorderLayout.CENTER);
	}
}
