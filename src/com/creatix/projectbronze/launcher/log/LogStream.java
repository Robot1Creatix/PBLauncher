package com.creatix.projectbronze.launcher.log;

import java.io.PrintStream;
import com.creatix.projectbronze.launcher.core.Core;
import com.creatix.projectbronze.launcher.utils.MiscUtils;

public class LogStream extends PrintStream
{
	
	public static final String info = " [INFO] ";
	public static final String debug = " [DEBUG] ";
	public static final String warning = " [WARNING] ";
	public static final String error = " [ERROR] ";
	public static final String fatal = " [FATALERROR] ";
	private static String time()
	{
		return "[" + MiscUtils.getTime() + "]";
	}
	
	private static String sender(Class<?> s)
	{
		return "[" + s.getName() + "]";
	}
	
	public LogStream()
	{
		super(System.out);
	}
	
	/**
	 * Sloud only be used by stacktraces or internaly
	 */
	@Deprecated
	@Override
	public void println(String x)
	{
		Logger.put(x, '\n');
		super.println(x);
	}
	
	/**
	 * Sloud only be used by stacktraces or internaly
	 */
	@Deprecated
	@Override
	public void print(String s)
	{
		Logger.put(s);
		super.print(s);
	}
	
	public void info(String s)
	{
		println(time() + info + s);
	}
	
	public void debug(String s, Class<?> sender)
	{
		if(Core.debug)
		println(time() + debug + sender(sender) + s);
	}
	
	public void warning(String s)
	{
		println(time() + warning + s);
	}
	
	public void error(String s)
	{
		println(time() + error + s);
	}
	
	public void fatal(String s)
	{
		println(time() + fatal + s);
	}
	
	public void info(Object s)
	{
		info(s.toString());
	}
	
	public void debug(Object s, Class<?> sender)
	{
		debug(s.toString(), sender);
	}
	
	public void warning(Object s)
	{
		warning(s.toString());
	}
	
	public void error(Object s)
	{
		error(s.toString());
	}
	
	public void fatal(Object s)
	{
		fatal(s.toString());
	}
	
}
