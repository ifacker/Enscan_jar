package plugins.Enscan.DataType;

import java.io.Serializable;

public class Advanced implements Serializable {
    private boolean status;
    private CookieLabel cookieLabel;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CookieLabel getCookieLabel() {
        return cookieLabel;
    }

    public void setCookieLabel(CookieLabel cookieLabel) {
        this.cookieLabel = cookieLabel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
