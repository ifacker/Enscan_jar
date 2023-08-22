package plugins.Enscan.DataType;

import java.util.List;

public class EnscanResult {

    public List<Enterprise> getEnterprise_info() {
        return enterprise_info;
    }

    public void setEnterprise_info(List<Enterprise> enterprise_info) {
        this.enterprise_info = enterprise_info;
    }

    public List<Website> getIcp() {
        return icp;
    }

    public void setIcp(List<Website> icp) {
        this.icp = icp;
    }

    private List<Enterprise> enterprise_info;
    private List<Website> icp;
}
