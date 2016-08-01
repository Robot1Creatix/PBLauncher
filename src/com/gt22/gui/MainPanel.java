package com.gt22.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import com.creatix.projectbronze.minecraft.Modpack;

public class MainPanel extends PanelBase
{
	private JLabel icon = new JLabel(), name = new JLabel("Name: "), version = new JLabel("Version: "), mcversion = new JLabel("Minecraft version: "), desc = new JLabel("Description:");
	public MainPanel(MainFrame instance)
	{
		icon.setPreferredSize(new Dimension(252, 252));
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
		JButton play = new JButton("play");
		play.setPreferredSize(new Dimension(100, 50));
		add(modpacks, 0, 0);
		gc.weighty = 0.1;
		add(icon, 0, 2);
		add(name, 2, 1);
		add(version, 2, 2);
		add(mcversion, 2, 3);
		add(desc, 3, 1);
		gc.anchor = GridBagConstraints.SOUTH;
		add(play, 3, 3);
	}
	
	private void setModpakc(Modpack m)
	{
		if(m == null)
		{
			icon.setIcon(null);
			name.setText("Name: ");
			version.setText("Version: ");
			mcversion.setText("Minecraft version: ");
			desc.setText("Description:");
		}
		else
		{
			icon.setIcon(m.logo);
			name.setText("Name: " + ((m.name == null) ? "" : m.name));
			version.setText("Version: " + ((m.version == null) ? "" : m.version));
			mcversion.setText("Minecraft version: " + ((m.mcversion == null) ? "" : m.mcversion));
			desc.setText("<html>Description:<br><br>" + ((m.description == null) ? "" : m.description));
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
}
