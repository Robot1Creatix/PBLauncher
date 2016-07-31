package com.creatix.projectbronze.launcher.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MiscUtils {
	
	public static Calendar calendar = Calendar.getInstance();
	public static SimpleDateFormat format = new SimpleDateFormat("HHmmddMM");
	
	public static String getTime(){
		return format.format(calendar.getTime());
	}
}
