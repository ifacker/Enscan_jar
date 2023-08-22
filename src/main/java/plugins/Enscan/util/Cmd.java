package plugins.Enscan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmd {

    // 直接运行
    public static void run(String command){
        try {
            // 创建ProcessBuilder对象并设置要执行的命令
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

            // 重定向错误输出流到标准输出流
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = null;
            process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
//                    appendOutputToTextArea(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("命令执行完毕，退出码：" + exitCode);
        } catch (IOException | InterruptedException e) {
//                appendOutputToTextArea("输入的命令有误，请重试！");
            e.printStackTrace();
        }
    }


    // 后台运行
    public static void bgRun(String command){
        new Thread(() -> {
            run(command);
        }).start();
    }
}
