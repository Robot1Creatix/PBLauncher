package com.creatix.projectbronze.launcher.core;

import java.util.Scanner;

import com.creatix.projectbronze.launcher.GUI.LauncherFrame;

public class BronzeLauncher {
	public static void start(boolean debug,boolean onlyconsole)
	{
		Core.print("Launching with arguments : Only-Console "+onlyconsole+", Debug "+debug);
		if(!onlyconsole)
			LauncherFrame.open();
		else
			console();
	}
	private static void console(){
	}
	
}
