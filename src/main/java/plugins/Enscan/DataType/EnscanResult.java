package plugins.Enscan.DataType;

import plugins.Enscan.DataType.result.*;

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

    public List<Supplier> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<Supplier> supplier) {
        this.supplier = supplier;
    }

    public List<Invest> getInvest() {
        return invest;
    }

    public void setInvest(List<Invest> invest) {
        this.invest = invest;
    }

    public List<Partner> getPartner() {
        return partner;
    }

    public void setPartner(List<Partner> partner) {
        this.partner = partner;
    }

    public List<Holds> getHolds() {
        return holds;
    }

    public void setHolds(List<Holds> holds) {
        this.holds = holds;
    }

    private List<Enterprise> enterprise_info;
    private List<Website> icp;
    private List<Supplier> supplier;
    private List<Invest> invest;
    private List<Partner> partner;
    private List<Holds> holds;
}
