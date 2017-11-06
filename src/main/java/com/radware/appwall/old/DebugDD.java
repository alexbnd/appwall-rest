package com.radware.appwall.old;

public class DebugDD {

	private static boolean debug= false;
	
	public static boolean isOn() {
        return debug ? true : false;
    }

    public static void turnOn() {
        debug = true;
    }
    
}
