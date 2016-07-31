package com.creatix.projectbronze.launcher.core;

public enum SystemProperty {
	OSNAME((byte)0), OSVERSION((byte)1), OSARCH((byte) 2), JVERSION((byte) 3), JHOME((byte) 4), UNAME((byte) 5), UHOME((byte) 6);
	//Need be change to short if we have more than 127 values)))
	private byte idx;
	private SystemProperty(byte idx)
	{
		this.idx = idx;
	}
	
	public int toInt()
	{
		return idx;
	}
}
