package com.gt22.gui.panel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.minecraft.Modpack;
import com.gt22.gui.component.ModpackList;
import com.gt22.gui.frame.MainFrame;
import com.gt22.gui.render.ModpackRenderer;

public class MainPanel extends JPanel
{
	private JLabel icon = new JLabel(), name = new JLabel(), version = new JLabel(), mcversion = new JLabel(), desc = new JLabel();
	private static BufferedImage back, playimg, openimg;
	JList<Modpack> modpacks;
	JScrollPane modpackselector;
	JButton play, openfolder;
	public MainPanel(MainFrame instance)
	{
		loadImages();
		initFont();
		initComponents();
		initListners();
		//Fast way to init text on components
		setModpakc(null);
	}
	
	private void initComponents()
	{
		/*JComboBox<String> modpacks = new JComboBox<String>(createModpacksArray());
		modpacks.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setModpakc(getModpackByName((String) modpacks.getSelectedItem()));
			}
		});*/
		play = new JButton(new ImageIcon(playimg));
		//Нужна текстурка
		//openfolder = new JButton(new ImageIcon(openimg));
		setLayout(null);
		add(modpackselector = new JScrollPane(modpacks = new ModpackList()));
		add(desc);
		add(icon);
		add(mcversion);
		add(name);
		add(play);
		add(version);
		modpacks.setCellRenderer(new ModpackRenderer());
		modpackselector.setBounds(0, 0, 655, 500);
		modpackselector.getVerticalScrollBar().setUI(new BasicScrollBarUI());
		modpackselector.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		modpackselector.setBorder(BorderFactory.createEmptyBorder());
		icon.setBounds(721, 44, 126, 126);
		name.setBounds(670, 220, 240, 40);
		version.setBounds(670, 260, 240, 40);
		mcversion.setBounds(670, 300, 240, 40);
		desc.setBounds(670, 340, 240, 320);
		name.setVerticalAlignment(SwingConstants.TOP);
		version.setVerticalAlignment(SwingConstants.TOP);
		mcversion.setVerticalAlignment(SwingConstants.TOP);
		desc.setVerticalAlignment(SwingConstants.TOP);
		play.setBounds(709, 500, 150, 40);
	}
	
	private void initListners()
	{
		modpacks.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				setModpakc(modpacks.getSelectedValue());
			}
		});
	}
	
	private void initFont()
	{
		try
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font.ttf")));
			Font f = new Font("Minecraft Evenings", Font.PLAIN, 25);
			name.setFont(f);
			version.setFont(f);
			mcversion.setFont(f);
			desc.setFont(f);
		}
		catch (FontFormatException | IOException e1)
		{
			Core.log.error("Unable to create font");
			e1.printStackTrace(Core.log);
		}
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
