package lyxs916.network;

/**
 * 说明:网络的类型
 * 作者： 杨阳; 创建于：  2017-06-06  18:07
 */
public enum NetworkType {
    NET_WORK_TYPE("没有网络"), WIFI("wifi网络"), ETHERNET("有线网络"), MOBILE("移动网络"), NULL("没有网络");

    private String type;

    NetworkType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
