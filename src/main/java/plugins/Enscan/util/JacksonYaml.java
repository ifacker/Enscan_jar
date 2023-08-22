package plugins.Enscan.util;

import plugins.Enscan.DataType.YamlConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class JacksonYaml {
    public static String serialization(YamlConfig yamlConfig){
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            String yamlString = objectMapper.writeValueAsString(yamlConfig);
//            System.out.println(yamlString);
            return yamlString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static YamlConfig unSerialization(String yamlString){
        yamlString = yamlString.replaceAll("\t", "    ");
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            YamlConfig yamlConfig = objectMapper.readValue(yamlString, YamlConfig.class);
            return yamlConfig;
            // 使用yourObject来处理您的数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
