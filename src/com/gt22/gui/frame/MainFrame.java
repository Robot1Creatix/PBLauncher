package com.gt22.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.utils.Const;
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
		if(Core.isValidJava())
		{
			add(mainpanel = new MainPanel(this), BorderLayout.CENTER);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Java 8 is required to run the launcher", "Incompatble java", JOptionPane.PLAIN_MESSAGE);
			Core.log.fatal("Dected java version lesser tha 1.8.0, launcher cannot work with this java version", true, 1);
		}
	}
}
