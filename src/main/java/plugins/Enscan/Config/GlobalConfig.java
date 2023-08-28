package plugins.Enscan.Config;

import plugins.Enscan.DataType.ConfigSer;
import plugins.Enscan.DataType.YamlConfig;

import java.util.HashMap;
import java.util.Map;

public class GlobalConfig {

    public static YamlConfig yamlConfig;

    public static ConfigSer configSer = new ConfigSer();

    public static Map<String, String> giteeSrcMap;
    static {
        giteeSrcMap = new HashMap<>();
        giteeSrcMap.put("enscan-0.0.15-darwin-amd64", "https://gitee.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-darwin-amd64");
        giteeSrcMap.put("enscan-0.0.15-darwin-arm64", "https://gitee.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-darwin-arm64");
        giteeSrcMap.put("enscan-0.0.15-windows-amd64.exe", "https://gitee.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-windows-amd64.exe");
        giteeSrcMap.put("enscan-0.0.15-linux-amd64", "https://gitee.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-amd64");
        giteeSrcMap.put("enscan-0.0.15-linux-arm64","https://gitee.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-arm64" );
        giteeSrcMap.put("enscan-0.0.15-linux-arm", "https://gitee.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-arm");
        giteeSrcMap.put("enscan-0.0.15-linux-386", "https://gitee.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-386");
    }

    public static Map<String, String> githubSrcMap;
    static {
        githubSrcMap = new HashMap<>();
        githubSrcMap.put("enscan-0.0.15-darwin-amd64", "https://github.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-darwin-amd64");
        githubSrcMap.put("enscan-0.0.15-darwin-arm64", "https://github.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-darwin-amd64");
        githubSrcMap.put("enscan-0.0.15-windows-amd64.exe", "https://github.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-windows-amd64.exe");
        githubSrcMap.put("enscan-0.0.15-linux-amd64", "https://github.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-amd64");
        githubSrcMap.put("enscan-0.0.15-linux-arm64","https://github.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-arm64" );
        githubSrcMap.put("enscan-0.0.15-linux-arm", "https://github.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-arm");
        githubSrcMap.put("enscan-0.0.15-linux-386", "https://github.com/ifacker/Enscan_jar/releases/download/v1.0/enscan-0.0.15-linux-386");
    }


    // 配置文件的路径
    public static String ConfigPath = "plugins/Enscan/exec/config.yaml";

    // json 文件的路径
    public static String JsonPath = "plugins/Enscan/exec/outs/";

    // 可执行文件路径
    public static String ExecPath = "plugins/Enscan/exec/";

    // 高级设置的保存路径
    public static String AdvancedPath = "plugins/Enscan/exec/config.ser";

    // test
//    public static String AdvancedPath = "exec/config.ser";
//    public static String ExecPath = "exec/";
//    public static String JsonPath = "exec/outs/";
//    public static String ConfigPath = "exec/config.yaml";


}
