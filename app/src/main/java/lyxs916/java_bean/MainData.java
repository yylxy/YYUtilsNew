package lyxs916.java_bean;

/**
 * 说明:
 * 作者： 阳2012; 创建于：  2017-05-27  17:25
 */
public class MainData {
    private int number;
    private int color;
    private String describe;
    private String className;

    public MainData(int number, String describe, int color, String className) {
        this.number = number;
        this.describe = describe;
        this.color = color;
        this.className = className;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
