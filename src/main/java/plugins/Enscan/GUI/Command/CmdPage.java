package plugins.Enscan.GUI.Command;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdPage {
    private TextField commandTextField;
    private TextArea outputTextArea;

    // 存放历史命令
//    private List<String> cmdHistory;

    // 存放历史命令的索引
//    private int cmdHistoryIndex;

    public Node showCmd() {

        //初始化
//        cmdHistory = new ArrayList<>();
//        cmdHistoryIndex = cmdHistory.size();

        // 输入命令
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setWrapText(true);
        outputTextArea.setPrefHeight(6000);


        // 输入命令
        commandTextField = new TextField();
        commandTextField.setPromptText("在此输入命令，按回车执行...");


        // Handle user input
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String command = commandTextField.getText().trim();
                if ("".equals(command)) {
                    return;
                }
//                cmdHistory.add(command);
                processCommand(command);
                commandTextField.clear();
//            } else if (event.getCode() == KeyCode.UP) {
//                System.out.println("up yes");
//                // 处理向上方向键的逻辑
//                if (cmdHistoryIndex == cmdHistory.size()) {
//                    cmdHistoryIndex--; // 第一次按上方向键时，从最近的历史命令开始回滚
//                }
//
//                if (cmdHistoryIndex > 0) {
//                    cmdHistoryIndex--; // 继续回滚历史命令
//                    String previousCommand = cmdHistory.get(cmdHistoryIndex);
//                    commandTextField.setText(previousCommand);
//                }
//            } else if (event.getCode() == KeyCode.DOWN) {
//                System.out.println("down yes");
//                // 处理向下方向键的逻辑
//                if (cmdHistoryIndex < cmdHistory.size() - 1) {
//                    cmdHistoryIndex++; // 增加索引，获取下一个历史命令
//                    String nextCommand = cmdHistory.get(cmdHistoryIndex);
//                    commandTextField.setText(nextCommand);
//                } else if (cmdHistoryIndex == cmdHistory.size() - 1) {
//                    commandTextField.clear(); // 如果已经到达历史命令列表的末尾，则清空文本字段
//                }
            }
        });

        // 终止按钮
        Button buttonStop = new Button("终止");
        buttonStop.setOnAction(event -> {

        });


        // 清除按钮
        Button buttonClear = new Button("清除");
        buttonClear.setOnAction(event -> {
            outputTextArea.setText("");
        });

        // 输入栏和清除按钮
        HBox.setHgrow(commandTextField, Priority.ALWAYS);
        HBox hBoxInputAndClearButton = new HBox(10);
        hBoxInputAndClearButton.getChildren().addAll(commandTextField, buttonStop, buttonClear);

        // Create layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(outputTextArea, hBoxInputAndClearButton);

        return root;
    }

    private void processCommand(String command) {
        // 在这里处理命令

        appendOutputToTextArea("\n" + System.getProperty("user.name") + " " + System.getProperty("user.dir") + "\n↓ ");

        new Thread(() -> {
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
                    appendOutputToTextArea(line);
                }

                // 等待命令执行完成
                int exitCode = process.waitFor();
                System.out.println("命令执行完毕，退出码：" + exitCode);
            } catch (IOException | InterruptedException e) {
                appendOutputToTextArea("输入的命令有误，请重试！");
                e.printStackTrace();
            }
        }).start();
    }

    private void appendOutputToTextArea(String output) {

        outputTextArea.appendText("\n" + output);
    }
}
