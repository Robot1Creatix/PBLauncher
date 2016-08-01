package com.gt22.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.minecraft.Modpack;

public class MainPanel extends PanelBase
{
	private JLabel icon = new JLabel(), name = new JLabel(), version = new JLabel(), mcversion = new JLabel(), desc = new JLabel();
	private static BufferedImage back, playimg;
	JButton play;
	public MainPanel(MainFrame instance)
	{
		loadImages();
		initComponents();
		//Fast way to init text on components
		setModpakc(null);
	}
	
	private void initComponents()
	{
		icon.setPreferredSize(new Dimension(126, 126));
		icon.setSize(126, 126);
		JComboBox<String> modpacks = new JComboBox<String>(createModpacksArray());
		modpacks.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setModpakc(getModpackByName((String) modpacks.getSelectedItem()));
			}
		});
		play = new JButton(new ImageIcon(playimg));
		play.setPreferredSize(new Dimension(150, 40));
		setLayout(null);
		add(modpacks);
		add(desc);
		add(icon);
		add(mcversion);
		add(name);
		add(play);
		add(version);
		/*try
		{
			name.setFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font.ttf")));
		}
		catch (FontFormatException | IOException e1)
		{
			Core.log.error("Unable to create font");
			e1.printStackTrace(Core.log);
		}*/
		Insets i = getInsets();
		modpacks.setBounds(0, 0, 200, 50);
		icon.setBounds(721, 44, 126, 126);
		icon.setBackground(new Color(255, 255, 255));
		name.setBounds(702, 220, 190, 20);
		version.setBounds(702, 240, 190, 20);
		mcversion.setBounds(702, 260, 190, 20);
		desc.setBounds(702, 280, 190, 320);
		desc.setVerticalAlignment(SwingConstants.TOP);
	}
	
	private void setModpakc(Modpack m)
	{
		if(m == null)
		{
			icon.setIcon(null);
			name.setText("");
			version.setText("");
			mcversion.setText("");
			desc.setText("");
			play.setEnabled(false);
		}
		else
		{
			icon.setIcon(m.logo);
			name.setText(((m.name == null) ? "" : m.name));
			version.setText(((m.version == null) ? "" : m.version));
			mcversion.setText(((m.mcversion == null) ? "" : m.mcversion));
			desc.setText(((m.description == null) ? "" : m.description));
			play.setEnabled(true);
		}
	}
	
	private static Modpack getModpackByName(String name)
	{
		for(Modpack m : Modpack.modpacks)
		{
			if(m.name.equals(name))
			{
				return m;
			}
		}
		return null;
	}
	
	private static String[] createModpacksArray()
	{
		
		String[] ret = new String[Modpack.modpacks.size() + 1];
		ret[0] = "none";
		for(int i = 1; i < ret.length; i++)
		{
			ret[i] = Modpack.modpacks.get(i - 1).name;
		}
		return ret;
	}
	
	private static void loadImages()
	{
		try
		{
			back = ImageIO.read(MainPanel.class.getResourceAsStream("/back.png"));
		}
		catch (IOException e)
		{
			Core.log.error("Unable to load background image");
			e.printStackTrace(Core.log);
		}
		try
		{
			playimg = ImageIO.read(MainPanel.class.getResourceAsStream("/play.png"));
		}
		catch (IOException e)
		{
			Core.log.error("Unable to load background image");
			e.printStackTrace(Core.log);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		if(back == null)
		{
			super.paintComponent(g);
		}
		else
		{
			g.drawImage(back, 0, 0, null);
		}
	}
}
