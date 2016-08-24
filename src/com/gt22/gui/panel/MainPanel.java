package com.gt22.gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.minecraft.DowloadManager;
import com.creatix.projectbronze.minecraft.Modpack;
import com.gt22.gui.component.ModpackList;
import com.gt22.gui.frame.MainFrame;

public class MainPanel extends JPanel
{
	private JLabel icon = new JLabel(), name = new JLabel(), version = new JLabel(), mcversion = new JLabel(), desc = new JLabel();
	private static BufferedImage back, playimg, openimg;
	ModpackList modpacks;
	JScrollPane modpackselector;
	JButton play, openfolder, update;
	public MainPanel(MainFrame instance)
	{
		initComponents();
		//Fast way to init text on components
		setModpack(null);
	}
	
	private void initComponents()
	{
		loadImages();
		initFont();
		addElements();
		initListners();
		initBoudns();
		initMisc();
	}
	
	private void addElements()
	{
		setLayout(null);
		add(modpackselector = new JScrollPane(modpacks = new ModpackList()));
		add(desc);
		add(icon);
		add(mcversion);
		add(name);
		add(play = new JButton(new ImageIcon(playimg)));
		add(version);
		//Нужны текстурки
		add(openfolder = new JButton("Open folder"));
		add(update = new JButton("Update"));
	}
	
	private void initMisc()
	{
		name.setVerticalAlignment(SwingConstants.TOP);
		version.setVerticalAlignment(SwingConstants.TOP);
		mcversion.setVerticalAlignment(SwingConstants.TOP);
		desc.setVerticalAlignment(SwingConstants.TOP);
		modpackselector.getVerticalScrollBar().setUI(new BasicScrollBarUI());
		modpackselector.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		modpackselector.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
	}
	
	private void initBoudns()
	{
		icon.setBounds(715, 57, 126, 126);
		name.setBounds(665, 260, 240, 30);
		version.setBounds(665, 290, 240, 20);
		mcversion.setBounds(665, 310, 240, 20);
		desc.setBounds(665, 330, 240, 265);
		modpackselector.setBounds(0, 0, 418, 500);
		update.setBounds(160, 510, 150, 40);
		openfolder.setBounds(320, 510, 150, 40);
		play.setBounds(5, 510, 150, 40);
	}
	
	private void initListners()
	{
		modpacks.addListSelectionListener((e) -> setModpack(modpacks.getSelectedValue()));
		update.addActionListener((e) -> dowloadPack());
		play.addActionListener((e) -> {
			dowloadPack();
			//MinecraftLauncher.runMinecraft();
		});
	}
	
	private void dowloadPack()
	{
		DowloadManager.downloadModpack(modpacks.getSelectedValue());
	}
	
	private void initFont()
	{
		try
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font.ttf")));
			Font f = new Font("Minecraft Evenings", Font.PLAIN, 25);
			name.setFont(f);
			//version.setFont(f);
			//mcversion.setFont(f);
			//desc.setFont(f);
		}
		catch (FontFormatException | IOException e1)
		{
			Core.log.error("Unable to create font");
			e1.printStackTrace(Core.log);
		}
	}
	
	private void setModpack(Modpack m)
	{
		if(m == null)
		{
			icon.setIcon(null);
			name.setText("");
			version.setText("");
			mcversion.setText("");
			desc.setText("");
			play.setEnabled(false);
			update.setEnabled(false);
			openfolder.setEnabled(false);
		}
		else
		{
			icon.setIcon(m.logo);
			name.setText(((m.name == null) ? "" : m.name));
			version.setText(((m.version == null) ? "..." : (DowloadManager.getDownloadedVersion(m) == null ? "..." : DowloadManager.getDownloadedVersion(m)) + ":" + m.version));
			mcversion.setText(((m.mcversion == null) ? "" : m.mcversion));
			desc.setText(((m.description == null) ? "" : m.description));
			play.setEnabled(true);
			update.setEnabled(true);
			openfolder.setEnabled(true);
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
