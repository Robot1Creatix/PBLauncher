package com.creatix.projectbronze.launcher.core;

import com.creatix.projectbronze.launcher.GUI.LauncherFrame;
import com.creatix.projectbronze.launcher.core.*;

public class BronzeLauncher {
	public static void start(boolean debug,boolean onlyconsole)
	{
		Core.log.println("Launching with arguments : Only-Console "+onlyconsole+", Debug "+debug);
		if(!onlyconsole)
			LauncherFrame.open();
		else
			console();
	}
	private static void console(){
	}
	
}
