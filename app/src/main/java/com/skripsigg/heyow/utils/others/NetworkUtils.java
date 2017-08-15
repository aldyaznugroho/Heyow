package com.skripsigg.heyow.utils.others;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Created by Aldyaz on 12/28/2016.
 */

public class NetworkUtils {
    private static NetworkUtils instance;

    public static NetworkUtils getInstance() {
        if (instance == null) {
            instance = new NetworkUtils();
        }
        return instance;
    }

    /**
     * Get network info.
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * Check connection availability.
     * @param context
     * @return
     */
    public boolean isConnectingToInternet(Context context) {
        NetworkInfo networkInfo = NetworkUtils.getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /**
     * Check whether app is connected by WiFi.
     * @param context
     * @return
     */
    public boolean isConnectedByWifi(Context context) {
        NetworkInfo networkInfo = NetworkUtils.getNetworkInfo(context);
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Check whether app is connected by Mobile Network.
     * @param context
     * @return
     */
    public boolean isConnectedByMobile(Context context) {
        NetworkInfo networkInfo = NetworkUtils.getNetworkInfo(context);
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * Check if there is fast connectivity.
     * @param context
     * @return
     */
    public boolean isConnectedFast(Context context) {
        NetworkInfo networkInfo = NetworkUtils.getNetworkInfo(context);
        return networkInfo != null &&
                networkInfo.isConnected() &&
                NetworkUtils.isConnectionFast(networkInfo.getType(), networkInfo.getSubtype());
    }

    public int checkWifiSpeed(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getLinkSpeed();
    }

    /**
     * Check if the connection is fast.
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps

                /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false; // Unknown
                default:
                    return false;
            }
        } else {
            return false;
        }
    }
}
