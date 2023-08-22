package plugins.Enscan.Config;

import plugins.Enscan.DataType.YamlConfig;

public class GlobalConfig {

    public static YamlConfig yamlConfig;

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
