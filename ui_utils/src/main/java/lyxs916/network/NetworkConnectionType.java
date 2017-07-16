package lyxs916.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 说明:网络类型的判断
 * 作者： 杨阳; 创建于：  2017-06-06  17:55
 */
public class NetworkConnectionType {

    public static void onReceive(Context context) {
        try {
            // 手机连接网络管理类
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 从手机连通�?�里面获取网络状态信息?
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            // 判断网路状态类型?
            if (netInfo != null && netInfo.isAvailable()) {
                // WiFi网络
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    NetworkType.NET_WORK_TYPE.setType(NetworkType.WIFI.getType());
                    // 有线网络
                } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                    NetworkType.NET_WORK_TYPE.setType(NetworkType.ETHERNET.getType());
                    // 移动网络
                } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    NetworkType.NET_WORK_TYPE.setType(NetworkType.MOBILE.getType());
                }
                // 无网络?
            } else {
                NetworkType.NET_WORK_TYPE.setType(NetworkType.NULL.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
