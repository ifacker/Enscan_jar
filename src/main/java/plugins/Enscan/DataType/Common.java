package plugins.Enscan.DataType;

import java.util.List;

public class Common {
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }

    private String output;
    private List<String> field;
}
