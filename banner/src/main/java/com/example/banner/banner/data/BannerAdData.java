package com.example.banner.banner.data;

public class BannerAdData {

    /*
     * "activity_id":62, "img_url":"/upload/201510/19/201510190930380866.jpg"
     *
     * "id":17,
            "type":1,//1.利星行机械(首页)2.利星行机械(模块)3.配件商城4.积分商城5.二手设备
            "title":"利星行机械(首页)轮播图2",
            "img_url":"/upload/201610/27/201610271808203550.jpg",
            "content_id":0,//content_type=1产品id,content_type2=分类id，自身为0不跳转
            "content_type":2//1商品，2分类
     */
    private int id;
    private int type;
    private int content_id;
    private int content_type;

    private String img_url;

    public BannerAdData() {
    }

    public BannerAdData(String img_url) {
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getContent_id() {
        return content_id;
    }

    public int getContent_type() {
        return content_type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "BannerAdData{" +
                "id=" + id +
                ", type=" + type +
                ", content_id=" + content_id +
                ", content_type=" + content_type +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
