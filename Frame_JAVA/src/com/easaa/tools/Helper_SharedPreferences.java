package com.easaa.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.renn.rennsdk.codec.Base64;


public class Helper_SharedPreferences {

	/* JSON(String) Object */
	public static void set_json_sp(String key, JSONObject value, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value.toString());
		editor.commit();
	}

	public static JSONObject get_json_sp(String key, Context context) {
		JSONObject _rtn = new JSONObject();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String JSONStr = preferences.getString(key, null) == null ? ""
				: preferences.getString(key, null);
		if (JSONStr.length() > 0) {
			try {
				_rtn = new JSONObject(JSONStr);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} else
			return null;

		return _rtn;
	}

	public static JSONObject get_json_sp(String key, Boolean clearBool,
			Context context) {
		JSONObject _rtn = new JSONObject();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String JSONStr = preferences.getString(key, null) == null ? ""
				: preferences.getString(key, null);
		if (clearBool) {
			clear_sharePref(key, context);
		}
		try {
			_rtn = new JSONObject(JSONStr);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return _rtn;
	}

	/* String */
	public static void set_str_sp(String key, String value, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		String data = new String(Base64.encodeBase64(value.getBytes()));
		editor.putString(key, data);
		editor.commit();
	}

	public static String get_str_sp(String key, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		if (preferences.getString(key, null) != null) {
			String value = new String(Base64.decodeBase64(preferences
					.getString(key, null).getBytes()));
			return value;
		}
		return null;
	}

	public static String get_str_sp(String key, Boolean clearBool,
			Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		// Check what return if casting null to integer
		String rtn = preferences.getString(key, null) == null ? ""
				: preferences.getString(key, null);
		if (clearBool) {
			clear_sharePref(key, context);
		}
		return rtn;
	}

	/* Integer */
	public static void set_int_sp(String key, Integer value, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	@SuppressWarnings("null")
	public static int get_int_sp(String key, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		// Check what return if casting null to integer
		return preferences.getInt(key, (Integer) null);
	}

	public static int get_int_sp(String key, Boolean clearBool, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		// Check what return if casting null to integer
		@SuppressWarnings("null")
		Integer rtn = preferences.getInt(key, (Integer) null);
		if (clearBool) {
			clear_sharePref(key, context);
		}
		return rtn;
	}

	/*
	 * 
	 * Object 此方法可用作存取 session
	 */
	public static void set_obj_sp(String[] keys, Object[] values,
			Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor sharedata = prefs.edit();

		if (keys != null && values != null) {
			if (keys.length == values.length) {
				for (int i = 0; i < keys.length; i++) {
					String key = keys[i];
					Object value = values[i];
					if (value instanceof String) {
						sharedata.putString(key, (String) value);
					} else if (value instanceof Integer) {
						sharedata.putInt(key, (Integer) value);
					} else if (value instanceof Long) {
						sharedata.putLong(key, (Long) value);
					} else if (value instanceof Float) {
						sharedata.putFloat(key, (Float) value);
					} else if (value instanceof Boolean) {
						sharedata.putBoolean(key, (Boolean) value);
					} else if (value instanceof java.io.Serializable) {
						try {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ObjectOutputStream oos = new ObjectOutputStream(
									baos);
							oos.writeObject(value);
							String personBase64 = new String(
									Base64.encodeBase64(baos.toByteArray()));
							sharedata.putString(key, personBase64);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
				sharedata.commit();
			}

		}

	}

	/**保存用户的登入状态Bean*/
	public static void set_obj_sp(String key, Object values, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor sharedata = prefs.edit();

		if (key != null && values != null) {

			if (values instanceof String) {
				sharedata.putString(key, (String) values);
			} else if (values instanceof Integer) {
				sharedata.putInt(key, (Integer) values);
			} else if (values instanceof Long) {
				sharedata.putLong(key, (Long) values);
			} else if (values instanceof Float) {
				sharedata.putFloat(key, (Float) values);
			} else if (values instanceof Boolean) {
				sharedata.putBoolean(key, (Boolean) values);
			} else if (values instanceof java.io.Serializable) {
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(values);
					String personBase64 = new String(Base64.encodeBase64(baos
							.toByteArray()));
					sharedata.putString(key, personBase64);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			sharedata.commit();

		}
	}

	public static Object get_obj_sp(String key, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		String personBase64 = prefs.getString(key, "");

		if (personBase64.equals("")) {
			return null;
		}

		try {
			byte[] base64Bytes = Base64.decodeBase64(personBase64.getBytes());
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object obj = ois.readObject();
			return obj;
		} catch (ClassNotFoundException e) {
			String[] keys = { key, "guserid" };

			if (keys != null && keys.length > 0) {
				Editor sharedata = prefs.edit();
				for (int i = 0; i < keys.length; i++) {
					String key1 = keys[i];
					if (key1 != null && key1.trim().length() > 0) {
						sharedata.remove(key1);
					}
				}
				sharedata.commit();
			}

			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* Long */
	public static void set_long_sp(String key, Long value, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	@SuppressWarnings("null")
	public static Long get_long_sp(String key, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		// Check what return if casting null to integer
		return preferences.getLong(key, (Long) null);
	}

	public static Long get_long_sp(String key, Boolean clearBool,
			Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		// Check what return if casting null to integer
		@SuppressWarnings("null")
		Long rtn = preferences.getLong(key, (Long) null);
		if (clearBool) {
			clear_sharePref(key, context);
		}
		return rtn;
	}

	/* Boolean */
	public static void set_bool_sp(String key, Boolean value, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean get_bool_sp(String key, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getBoolean(key, false);
	}

	public static boolean get_bool_sp(String key, Boolean clearBool,
			Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Boolean rtn = preferences.getBoolean(key, false);
		if (clearBool) {
			clear_sharePref(key, context);
		}
		return rtn;
	}

	/**清除用户的登入状态，即清楚Bean*/
	public static void clear_sharePref(String key, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}

	/* Float */

	/* String Set */
}
