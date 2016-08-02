package com.creatix.projectbronze.minecraft;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.core.SystemProperty;

public class MinecraftLauncher
{
	private static final String sep = File.separator;
	private static String gameDirectory;
	private static StringBuilder cpb;

	public static Process runMinecraft(String modpackFolder, List<File> classpath, String mainClass, int width, int height) throws IOException
	{
		//TODO Научится запускать майн
		cpb = new StringBuilder();
		gameDirectory = modpackFolder;
		File gameDir = new File(modpackFolder);
		initClasspath(classpath);
		List<String> arguments = setupArgs(gameDir, mainClass, width, height);
		ProcessBuilder builder = createProcess(arguments, gameDir);
		return builder.start();
	}

	private static ProcessBuilder createProcess(List<String> args, File gameDir)
	{
		ProcessBuilder ret = new ProcessBuilder(args);
		ret.directory(gameDir);
		ret.redirectErrorStream(true);
		return ret;
	}
	
	private static void initClasspath(List<File> classpath)
	{
		for (File f : classpath)
		{
			cpb.append(File.pathSeparatorChar);
			cpb.append(f.getAbsolutePath());
		}
		cpb.deleteCharAt(0);
	}

	private static List<String> setupArgs(File gameDir, String mainClass, int width, int height)
	{
		List<String> arguments = new ArrayList<String>();
		arguments.add(Core.getSystemProperty(SystemProperty.JHOME));
		/*
		 	TODO Настроить загрузку native файлов для библиотек
		 	arguments.add("-Djava.library.path=" + nativesDir.getAbsolutePath());
			arguments.add("-Dorg.lwjgl.librarypath=" + nativesDir.getAbsolutePath());
			arguments.add("-Dnet.java.games.input.librarypath=" + nativesDir.getAbsolutePath());
		 */
		arguments.add("-Duser.home=" + gameDir.getParentFile().getAbsolutePath());
		if (Core.isWindows())
		{
			arguments.add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
		}
		arguments.add("-Djava.net.preferIPv4Stack=true");
		arguments.add("-cp");
		arguments.add(cpb.toString());
		arguments.add(mainClass);
		arguments.add("--width");
		arguments.add(width + "");
		arguments.add("--height");
		arguments.add(height + "");
		return arguments;
	}
}
