package com.creatix.projectbronze.launcher.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MiscUtils {
	
	public static Calendar calendar = Calendar.getInstance();
	public static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat datacode = new SimpleDateFormat("HHmmddMM");
	
	public static String getDataCode(){
		return datacode.format(calendar.getTime());
	}
	public static String getTime()
	{
		return time.format(calendar.getTime());
	}
}
