package plugins.Enscan.DataType;

public enum CookieLabel {
    AIQICHA("aiqicha", "爱企查 cookie：", "爱企查"),

    TIANYANCHA("tianyancha", "天眼查 cookie：", "天眼查"),

    TYCID("tycid","天眼查 CApi ID(capi.tianyancha.com)：", "天眼查 CApi"),

    ALDZS("aldzs", "阿拉丁 TOKEN(see README)：", "阿拉丁"),

    QIMAI("qimai", "七麦数据 cookie：", "七麦数据"),

    CHINAZ("chinaz", "站长之家 cookie：" , "站长之家"),

    VERYVP("veryvp", "veryvp cookie：", "veryvp");

    private String key;

    private String value;
    private String name;
    CookieLabel(String key, String value, String name){
        this.key = key;
        this.value= value;
        this.name = name;
    }

    public String getKey(){
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getName(){return name;}
}
