package Config;


import Type.ConfigType;
//import Type.TabIdType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfig {
    public static ConfigType configTypeNow;
    public static Label flag ;
    // 创建选项卡面板
    public static TabPane tabPane;

//    public static List<TabIdType> tabIdTypes = new ArrayList<>();


    // 官方源
    public static String[] authoritySources = {
            "https://gitee.com/ifacker/ToolsKingPluginLib/raw/master/flag.json",
            "https://raw.githubusercontent.com/ifacker/ToolsKingPluginLib/master/flag.json"
    };

    // 配置文件路径
    public static String[] configPath = {
            "config",
            "config/config.json"
    };

    // 插件路径
//    public static String pluginPath = "src/main/java/plugins/";
    public static String pluginPath = "plugins/";


}
