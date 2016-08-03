package com.gt22.gui.frame;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import sun.java2d.loops.ProcessPath.ProcessHandler;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.utils.Const;
import com.gt22.gui.panel.JavaErrorFrame;
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
