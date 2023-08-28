package plugins.Enscan.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    /**
     * 读取文本文件内容
     *
     * @param path 输入路径
     * @return 返回读取的内容
     */
    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    /**
     * 写入文本文件内容
     *
     * @param filePath 要写入的文件路径
     * @param content  要写入的内容
     */
    public static void writeFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(filePath)), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除文件
     * @param folder 传入文件夹的名字
     */
    public static void deleteFiles(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归删除文件夹里的文件
                    deleteFiles(file);
                } else {
                    file.delete();
                }
            }
        }
    }


    public static List<String> getJsonFileNames(File folder) {
        List<String> jsonFileNames = new ArrayList<>();
        File[] files = folder.listFiles();

        if (files != null){
            for(File file : files){
                if (file.isFile() && file.getName().toLowerCase().endsWith(".json")){
                    String fileName = file.getName();
                    jsonFileNames.add(fileName);

//                    String jsonFileName = fileName.substring(0, fileName.lastIndexOf(".json"));
//                    jsonFileNames.add(jsonFileName);
                }
            }
        }
        return jsonFileNames;
    }
}
