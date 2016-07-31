package com.creatix.projectbronze.launcher.core;

import com.gt22.gui.GUI;

public class BronzeLauncher {
	public static void start(boolean debug,boolean onlyconsole)
	{
		Core.log.println("Launching with arguments : Only-Console "+onlyconsole+", Debug "+debug);
		if(!onlyconsole)
			GUI.init();
		else
			console();
	}
	private static void console(){
	}
	
}
