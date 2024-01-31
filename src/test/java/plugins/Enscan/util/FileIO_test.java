package plugins.Enscan.util;

import plugins.Enscan.Config.GlobalConfig;
import plugins.Enscan.DataType.YamlConfig;

import java.io.File;

public class FileIO_test {
    public static void main(String[] args) {
//        String path = "/Users/jiangmengwei/files/code/IdeaProjects/ToolsKing/Plugin/Enscan_jar/exec/config.yaml";
//        String content = FileIO.readFile(path);
//        System.out.println(content);
//
//        YamlConfig yamlConfig = JacksonYaml.unSerialization(content);
//        System.out.println();


//        getJsonFileNames_test();

//            createFolder_test();

        writeFile_test();

    }

    private static void getJsonFileNames_test(){
        FileIO.getJsonFileNames(new File(GlobalConfig.JsonPath));
    }

    private static void createFolder_test(){
        FileIO.createFolders("a/d/f/g/vc/");
    }

    private static void writeFile_test(){
        FileIO.writeFile("exec/outs/a/a/a/a/input_test.txt", "test");
    }

}


