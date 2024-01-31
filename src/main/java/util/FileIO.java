package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
        try {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent()); // 创建文件夹，包括父文件夹
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path), StandardCharsets.UTF_8))) {
                writer.write(content);
            }
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

    // 创建文件夹
    public static void createFolders(String path) {
        // 根据系统获取路径分隔符
        String separator = File.separator;

        // 替换路径分隔符，以适应当前系统
        path = path.replace("/", separator).replace("\\", separator);

        // 创建文件夹
        File folder = new File(path);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("文件夹创建成功！");
            } else {
                System.err.println("文件夹创建失败。");
            }
        }
//        else {
//            System.out.println("文件夹已经存在。");
//        }
    }


    /**
     * 判断文件是否存在
     * @param path 文件路径
     * @return 返回是否存在
     */
    public static Boolean isFile(String path){
        return new File(path).exists();
    }

}
