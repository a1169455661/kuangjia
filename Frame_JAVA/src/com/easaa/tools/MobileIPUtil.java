package com.easaa.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**通过GPRS获取手机ip*/
public class MobileIPUtil {
	public static String getLocalIpAddressByGprs()  
	{  
		try  
		{  
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)  
			{  
				NetworkInterface intf = en.nextElement();  
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)  
				{  
					InetAddress inetAddress = enumIpAddr.nextElement();  
					if (!inetAddress.isLoopbackAddress())  
					{  
						return inetAddress.getHostAddress().toString();  
					}  
				}  
			}  
		}  
		catch (SocketException ex)  
		{  
			Log.e("WifiPreference IpAddress", ex.toString());  
		}  
		return null;  
	}  

	/**通过WIFI获取手机ip*/
	public static String getLocalIpAddressByWifi(Context context) {
		//获取wifi服务  
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
		//判断wifi是否开启  
		if (!wifiManager.isWifiEnabled()) {  
			wifiManager.setWifiEnabled(true);    
		}  
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return intToIp(wifiInfo.getIpAddress());
	}

	private static String intToIp(int i) {       

		return (i & 0xFF ) + "." +       
				((i >> 8 ) & 0xFF) + "." +       
				((i >> 16 ) & 0xFF) + "." +       
				( i >> 24 & 0xFF) ;  
	}   
}
