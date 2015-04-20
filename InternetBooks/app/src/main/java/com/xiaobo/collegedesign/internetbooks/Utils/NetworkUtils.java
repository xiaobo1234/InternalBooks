package com.xiaobo.collegedesign.internetbooks.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by chenhong on 15/1/12.
 */
public class NetworkUtils {

    /** Network type is unknown */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /** Current network is GPRS */
    public static final int NETWORK_TYPE_GPRS = 1;
    /** Current network is EDGE */
    public static final int NETWORK_TYPE_EDGE = 2;
    /** Current network is UMTS */
    public static final int NETWORK_TYPE_UMTS = 3;
    /** Current network is CDMA: Either IS95A or IS95B*/
    public static final int NETWORK_TYPE_CDMA = 4;
    /** Current network is EVDO revision 0*/
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /** Current network is EVDO revision A*/
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /** Current network is 1xRTT*/
    public static final int NETWORK_TYPE_1xRTT = 7;
    /** Current network is HSDPA */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /** Current network is HSUPA */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /** Current network is HSPA */
    public static final int NETWORK_TYPE_HSPA = 10;
    /** Current network is iDen */
    public static final int NETWORK_TYPE_IDEN = 11;
    /** Current network is EVDO revision B*/
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /** Current network is LTE */
    public static final int NETWORK_TYPE_LTE = 13;
    /** Current network is eHRPD */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /** Current network is HSPA+ */
    public static final int NETWORK_TYPE_HSPAP = 15;


    /** Unknown network class. {@hide} */
    public static final String NETWORK_CLASS_UNKNOWN = "ACCESS_TYPE_OTHER";
    /** Class of broadly defined "2G" networks. {@hide} */
    public static final String NETWORK_CLASS_2_G = "ACCESS_TYPE_2G";
    /** Class of broadly defined "3G" networks. {@hide} */
    public static final String NETWORK_CLASS_3_G = "ACCESS_TYPE_3G";
    /** Class of broadly defined "4G" networks. {@hide} */
    public static final String NETWORK_CLASS_4_G = "ACCESS_TYPE_4G";

    public static String  getNetWorkType(){
        String access_type = "";
        //网络类型

        TelephonyManager telephonyManager  = (TelephonyManager) ApplicationRunTime.getAppContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        ConnectivityManager cm = (ConnectivityManager) ApplicationRunTime.getAppContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null)//必须进行非空判断,可能此时没有联网
        {
            access_type = info.getTypeName();
            if (access_type.equalsIgnoreCase("WIFI")) {
                access_type = "ACCESS_TYPE_WIFI";
            } else if (info != null && access_type.equalsIgnoreCase("MOBILE")) {
                access_type = getNetworkClass(telephonyManager.getNetworkType());
            } else {
                access_type = "ACCESS_TYPE_OTHER";
            }
        }else {
            access_type = "ACCESS_TYPE_OTHER";//没有联网
        }
        return access_type;
    }

    /**
     * Return general class of network type, such as "3G" or "4G". In cases
     * where classification is contentious, this method is conservative.
     */
    private static String getNetworkClass(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }
}
