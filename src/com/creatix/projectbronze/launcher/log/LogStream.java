package com.creatix.projectbronze.launcher.log;

import java.io.PrintStream;

public class LogStream extends PrintStream
{
	public LogStream()
	{
		super(System.out);
	}
	
	@Override
	public void println(String x)
	{
		Logger.put(x, '\n');
		super.println(x);
	}
	
	@Override
	public void print(String s)
	{
		Logger.put(s);
		super.print(s);
	}
	
	
	
}
