package com.dex.util;

public final class StringUtil {
	private StringUtil() {
	}
	
	public static boolean isNull(String s) {
		return s == null;
	}
	
	public static boolean isNullOrEmpty(String s) {
		return isNull(s) || s.length() == 0;
	}
	
	public static boolean isNullOrEmptyTrim(String s) {
		return isNull(s) || s.trim().length() == 0;
	}
	
	public static String substring(String s, int start, int count) {
		return s == null ? null : s.substring(start, start+count);
	}
}