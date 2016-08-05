package com.gt22.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.creatix.projectbronze.launcher.core.Core;
import com.gt22.gui.frame.MainFrame;
import com.gt22.gui.panel.JavaErrorFrame;

public class GUI
{
	public static void init()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			Core.log.error("Unable set system look and feel, something went realy wrong.");
			e.printStackTrace(Core.log);
		}
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				if (Core.isValidJava())
				{
					new MainFrame();
				}
				else
				{
					new JavaErrorFrame();
				}
			}
		});
	}
}
