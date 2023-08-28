package plugins.Enscan.DataType.result;

public class Partner {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getReg_cap() {
        return reg_cap;
    }

    public void setReg_cap(String reg_cap) {
        this.reg_cap = reg_cap;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    private String name;
    private String pid;
    private String reg_cap;
    private String scale;

}
