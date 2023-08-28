package plugins.Enscan.DataType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConfigSer implements Serializable {

    public Map<String, Advanced> getAdvanceds() {
        return advanceds;
    }

    public void setAdvanceds(Map<String, Advanced> advanceds) {
        this.advanceds = advanceds;
    }


    private Map<String, Advanced> advanceds = new HashMap<>();

    public String getSrcMapIndex() {
        return srcMapIndex;
    }

    public void setSrcMapIndex(String srcMapIndex) {
        this.srcMapIndex = srcMapIndex;
    }

    private String srcMapIndex = "gitee";

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    private String outputPath = "";
}
