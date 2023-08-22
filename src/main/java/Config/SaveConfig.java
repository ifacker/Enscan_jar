package Config;

import Type.ConfigType;

import java.io.File;

public class SaveConfig {

    public SaveConfig() {
        // 文件夹
        File folder = new File(GlobalConfig.configPath[0]);

        // 文件
        File file = new File(GlobalConfig.configPath[1]);

        // 如果文件不存在，并且不是文件夹
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdir();
        }
        // 如果文件不存在，并且不是文件
        if (!file.exists() && folder.isDirectory()) {
            ConfigType configType = new ConfigType();
            util.Serialize.serializeObject(configType, GlobalConfig.configPath[1]);
        }

        // 判断插件文件夹是否存在，并且是文件夹，如果不存在，就创建一个
        File pluginsFile = new File(GlobalConfig.pluginPath);
        if (!pluginsFile.exists()) {
            pluginsFile.mkdir();
        } else if ( !pluginsFile.isDirectory()){
            pluginsFile.mkdir();
        }
    }

    /**
     * 加载配置文件
     *
     * @param configPath 配置文件路径
     */
    public ConfigType load(String configPath) {
        return (ConfigType) util.Serialize.deserializeObject(configPath);
    }

    /**
     * 保存现有的配置文件
     */
    public static void save() {
        new File(GlobalConfig.configPath[1]).delete();
        util.Serialize.serializeObject(GlobalConfig.configTypeNow, GlobalConfig.configPath[1]);
    }
}
