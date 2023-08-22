package plugins.Enscan.GUI.Root;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import plugins.Enscan.Config.GlobalConfig;
import plugins.Enscan.DataType.Advanced;
import plugins.Enscan.DataType.CookieLabel;
import plugins.Enscan.DataType.EnscanResult;
import plugins.Enscan.DataType.Website;
import plugins.Enscan.util.*;

import java.io.File;
import java.util.*;

public class IcpSearchPage {

    private TextArea textAreaInput = new TextArea();
    ;
    private TextArea textAreaOutput = new TextArea();
    ;
    private Map<String, Advanced> advanceds = new HashMap<>();

    // 高级设置区域的子项
    private Node item(String title) {

        CheckBox checkBox = new CheckBox(title);
        if (advanceds.get(title) != null) {
            checkBox.setSelected(advanceds.get(title).getStatus());
        } else {
            Advanced advanced = new Advanced();
            advanced.setName(title);
            advanceds.put(title, advanced);
        }

        // 监听事件，用于保存状态
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // 直接存储 newValue 的状态，他是 Bool 类型的
            advanceds.get(title).setStatus(newValue);
            Serialize.serializeObject(advanceds, GlobalConfig.AdvancedPath);
        });

        HBox hBoxItem = new HBox(10);
        hBoxItem.getChildren().addAll(checkBox);
        hBoxItem.setAlignment(Pos.CENTER);
        hBoxItem.setPadding(new Insets(10));
        return hBoxItem;
    }

    // 高级设置区域的子项
    private Node itemTextPlus(String title) {
//        Advanced advanced = new Advanced();
        CheckBox checkBox = new CheckBox(title);
        if (advanceds.get(title) != null) {
            checkBox.setSelected(advanceds.get(title).getStatus());
        } else {
            Advanced advanced = new Advanced();
            advanced.setName(title);
            advanceds.put(title, advanced);
        }
        checkBox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            advanceds.get(title).setStatus(newValue);
            Serialize.serializeObject(advanceds, GlobalConfig.AdvancedPath);
        }));


        TextField textField = new TextField();
        if (advanceds.get(title) != null) {
            textField.setText(advanceds.get(title).getValue());
        }
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            advanceds.get(title).setValue(newValue);
            Serialize.serializeObject(advanceds, GlobalConfig.AdvancedPath);
        }));


        HBox hBox = new HBox(10);
//        hBox.getChildren().addAll(checkBox);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));

        hBox.getChildren().addAll(checkBox, textField);
        return hBox;
    }

    // 高级设置区域的子项
    private Node itemCheckBoxPlus(String title) {

        Label label = new Label(title);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                CookieLabel.AIQICHA.getName(),
                CookieLabel.TIANYANCHA.getName(),
                CookieLabel.TYCID.getName(),
                CookieLabel.ALDZS.getName(),
                CookieLabel.QIMAI.getName(),
                CookieLabel.CHINAZ.getName(),
                CookieLabel.VERYVP.getName()
        );
        comboBox.setValue(CookieLabel.AIQICHA.getName());

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label, comboBox);
        return hBox;
    }

    // 高级设置区域
    private Node advancedContent() {

        // 判断配置文件是否存在
        if (new File(GlobalConfig.AdvancedPath).isFile()) {

            // 加载高级选项的配置文件
            advanceds = (Map<String, Advanced>) Serialize.unSerializeObject(GlobalConfig.AdvancedPath);
        }


        FlowPane flowPane1 = new FlowPane();
        flowPane1.getChildren().addAll(
                item(CookieLabel.AIQICHA.getName()),
                item(CookieLabel.TIANYANCHA.getName()),
                item(CookieLabel.ALDZS.getName()),
                item(CookieLabel.QIMAI.getName()),
                item(CookieLabel.CHINAZ.getName()),
                item(CookieLabel.VERYVP.getName())
        );

        FlowPane flowPane2 = new FlowPane();
        flowPane2.getChildren().addAll(itemTextPlus("demo1"));

        FlowPane flowPane3 = new FlowPane();
        flowPane3.getChildren().addAll(itemCheckBoxPlus("demo2"));

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));

        // 这里添加
        vBox.getChildren().addAll(flowPane1);

        return vBox;
    }

    // 搜索
    private void search() {

        // 删除文件夹里的所有文件
        FileIO.deleteFiles(new File(GlobalConfig.JsonPath));

        if ("".equals(textAreaInput.getText().trim())) {
            return;
        }

        // 把输入存入缓存文件
        FileIO.writeFile(GlobalConfig.JsonPath + "input.txt", textAreaInput.getText());

        // 组装查询来源
        String type = "";
        for (Map.Entry<String, Advanced> entry : advanceds.entrySet()) {
            String key = entry.getKey();
            Advanced advanced = entry.getValue();
            switch (key) {
                case "爱企查":
                    if (advanced.getStatus() && !"".equals(GlobalConfig.yamlConfig.getCookies().get(CookieLabel.AIQICHA.getKey()))) {
                        type += "aqc,";
                    }
                    break;
                case "天眼查":
                    if (advanced.getStatus() && !"".equals(GlobalConfig.yamlConfig.getCookies().get(CookieLabel.TIANYANCHA.getKey()))) {
                        type += "tyc,";
                    }
                    break;
                case "阿拉丁":
                    if (advanced.getStatus() && !"".equals(GlobalConfig.yamlConfig.getCookies().get(CookieLabel.ALDZS.getKey()))) {
                        type += "aldzs,";
                    }
                    break;
                case "七麦数据":
                    if (advanced.getStatus() && !"".equals(GlobalConfig.yamlConfig.getCookies().get(CookieLabel.QIMAI.getKey()))) {
                        type += "qimai,";
                    }
                    break;
                case "站长之家":
                    if (advanced.getStatus() && !"".equals(GlobalConfig.yamlConfig.getCookies().get(CookieLabel.CHINAZ.getKey()))) {
                        type += "chinaz,";
                    }
                    break;
                // 这里的 veryvp 官方给出的工具存在问题，目前 0.0.15 有问题
//                case "veryvp":
//                    type+=""
            }
        }
        type = type.replaceAll(",+$", "");
        // 组装命令
        String cmd = GlobalConfig.ExecPath + "enscan_m -json -field icp -type " + type + " -o " + GlobalConfig.JsonPath + " -f " + GlobalConfig.JsonPath + "input.txt";

        // 执行命令
        Cmd.run(cmd);

        // 获取 json 文件的文件名
        List<String> fileNames = FileIO.getJsonFileNames(new File(GlobalConfig.JsonPath));
        if (fileNames.size() != 1) {
            FileIO.deleteFiles(new File(GlobalConfig.JsonPath));
            return;
        }

        // 读取 json 串 并反序列化
        String json = FileIO.readFile(GlobalConfig.JsonPath + fileNames.get(0));
        EnscanResult enscanResult = JacksonJson.unSerialization(json);

        textAreaOutput.clear();

        Set<String> results = new HashSet<>();

        // 获取 icp 信息，并提取域名
        if (enscanResult.getIcp() != null) {
            for (Website website : enscanResult.getIcp()) {
                results.add(website.getDomain() + "\n");
//                textAreaOutput.appendText(website.getDomain() + "\n");
//                System.out.printf(website.getDomain());
            }
        } else {
//            textAreaOutput.setText("未查到有关 icp 备案域名信息！");
            results.add("未查到有关 icp 备案域名信息！");
        }
        for (String result : results) {
            textAreaOutput.appendText(result);
            try {
                Thread.sleep(10);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public Node show() {

        // 顶部内容
        // 高级设置
        TitledPane titledPane = new TitledPane("高级选项", advancedContent());
        titledPane.setCollapsible(true); // 允许崩溃
        titledPane.setExpanded(false);     // 设置最初扩展

        // 中部内容
        // 输入框
//        textAreaInput = new TextArea();
        textAreaInput.setPromptText("请输入需要信息收集的公司名，如：小米");

        // 输出框
//        textAreaOutput = new TextArea();
        textAreaOutput.setEditable(false);

        HBox hBoxTextArea = new HBox(10);
        hBoxTextArea.setPadding(new Insets(10, 10, 0, 10));
        hBoxTextArea.setAlignment(Pos.CENTER);
        hBoxTextArea.getChildren().addAll(textAreaInput, textAreaOutput);

        // 底部进度条
        ProgressIndicator progressBar = new ProgressIndicator();
        progressBar.setVisible(false);
        progressBar.setPrefSize(30, 30);

        // 底部按钮
        Button buttonStart = new Button("查询");
        buttonStart.setOnAction(event -> {
            Platform.runLater(() -> {

                Thread t = ProgressLoad.showProgress(progressBar, (p) -> {
                    search();
                });
                t.start();
            });
        });

        HBox hboxProgress = new HBox(10);
        hboxProgress.setPadding(new Insets(10));
        hboxProgress.setAlignment(Pos.CENTER_LEFT);
        hboxProgress.getChildren().addAll(progressBar);

        HBox hBoxButtons = new HBox(10);
        hBoxButtons.setPadding(new Insets(10));
        hBoxButtons.setAlignment(Pos.CENTER_RIGHT);
        hBoxButtons.getChildren().addAll(buttonStart);


        // 底部 BorderPane
        BorderPane borderPaneBottom = new BorderPane();
        borderPaneBottom.setRight(hBoxButtons);
        borderPaneBottom.setLeft(hboxProgress);

        // Create layout
        BorderPane root = new BorderPane();
        root.setBottom(borderPaneBottom);
        root.setCenter(hBoxTextArea);
        root.setTop(titledPane);

        return root;
    }
}
