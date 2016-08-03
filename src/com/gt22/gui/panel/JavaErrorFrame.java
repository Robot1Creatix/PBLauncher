package com.gt22.gui.panel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.utils.Const;

public class JavaErrorFrame extends JFrame
{
	GridBagConstraints gc;

	public JavaErrorFrame()
	{
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		add(new JLabel("<html><p>Java 8 is required to run the launcher</p><p style='margin-left: 78px;'></html>"), 0, 0);
		JLabel download = new JLabel("<html><a href='https://java.com'>Dowload</a></html>");
		JButton ok = new JButton("OK");
		//Cannot use lambda becouse if we here, no java 8 detected
		ok.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Core.log.fatal("Detected java older than 1.8, launcher is unable to run", true, Const.EXITCODE_INVALID_JAVA);
			}
		});
		download.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if (Desktop.isDesktopSupported())
				{
					try
					{
						Desktop.getDesktop().browse(new URI("http://www.java.com"));
					}
					catch (Exception ex)
					{
						Core.log.error("Unable to open java.com");
						ex.printStackTrace(Core.log);
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}
		});
		download.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(download, 0, 1);
		add(ok, 0, 2);
		setResizable(false);
		setSize(Const.jerrorwidth, Const.jerrorheight);
		setVisible(true);
	}

	protected void add(Component c, int x, int y)
	{
		gc.gridx = x;
		gc.gridy = y;
		add(c, gc);
	}
}
