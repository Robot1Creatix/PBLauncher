package com.gt22.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	private MainPanel mainpanel;
	public static final int startwidth = 700, startheight = 500;
	public MainFrame()
	{
		setSize(startwidth, startheight);
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
