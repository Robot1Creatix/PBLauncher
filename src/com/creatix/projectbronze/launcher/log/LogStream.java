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
	
	private static String sender()
	{
		StackTraceElement e = Thread.currentThread().getStackTrace()[4];
		return "[" + e.getClassName() + "#" + e.getMethodName() + "(Line:" + e.getLineNumber() + ")" + "]";
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
	public void print(String s)
	{
		Logger.put(s);
		super.print(s);
	}
	
	private void info(String s)
	{
		println(time() + info + s);
	}
	
	private void debug(String s)
	{
		if(Core.debug)
		println(time() + debug + sender() + s);
	}
	
	private void warning(String s)
	{
		println(time() + warning + s);
	}
	
	private void error(String s)
	{
		println(time() + error + s);
	}
	
	private void fatal(String s, boolean kill, int exitcode)
	{
		println(time() + fatal + s);
		if(kill)
		{
			System.exit(exitcode);
		}
	}
	
	public void info(Object s)
	{
		info(s.toString());
	}
	
	public void debug(Object s)
	{
		debug(s.toString());
	}
	
	public void warning(Object s)
	{
		warning(s.toString());
	}
	
	public void error(Object s)
	{
		error(s.toString());
	}
	
	public void fatal(Object s, boolean kill, int exitcode)
	{
		fatal(s.toString(), kill, exitcode);
	}
	
}
