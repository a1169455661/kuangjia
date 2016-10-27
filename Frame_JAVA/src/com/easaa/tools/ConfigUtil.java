package com.easaa.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.easaa.main.TApplication;

public class ConfigUtil {

	private static SharedPreferences getSharedPreferecnes() {
		Context context = TApplication.getInstance().getBaseContext();
		return context.getSharedPreferences("sige",
				Context.MODE_PRIVATE);
	}

	public static void putBoolean(String tag, boolean value) {
		Editor editor = getSharedPreferecnes().edit();
		editor.putBoolean(tag, value);
		editor.commit();
	}

	public static boolean getBoolean(String tag, boolean defaultValue) {
		SharedPreferences shared = getSharedPreferecnes();
		return shared.getBoolean(tag, defaultValue);
	}

	public static void putInt(String tag, int value) {
		Editor editor = getSharedPreferecnes().edit();
		editor.putInt(tag, value);
		editor.commit();
	}

	public static int getInt(String tag, int defaultValue) {
		SharedPreferences shared = getSharedPreferecnes();
		return shared.getInt(tag, defaultValue);
	}

	public static void putLong(String tag, long value) {
		Editor editor = getSharedPreferecnes().edit();
		editor.putLong(tag, value);
		editor.commit();
	}


	public static long getLong(String tag, long defaultValue) {
		SharedPreferences shared = getSharedPreferecnes();
		return shared.getLong(tag, defaultValue);
	}

	public static void putString(String tag, String value) {
		Editor editor = getSharedPreferecnes().edit();
		editor.putString(tag, value);
		editor.commit();
	}

	public static String getString(String tag, String defaultValue) {
		SharedPreferences shared = getSharedPreferecnes();
		return shared.getString(tag, defaultValue);
	}

	public static void clear_sharePref(String key) {
		SharedPreferences preferences = getSharedPreferecnes();
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}
}
