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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import plugins.Enscan.Config.GlobalConfig;
import plugins.Enscan.DataType.*;
import plugins.Enscan.DataType.result.Website;
import plugins.Enscan.util.*;

import java.io.File;
import java.util.*;

public class IcpSearchPage {

    private TextArea textAreaInput = new TextArea();

    private TextArea textAreaOutput = new TextArea();

    private Map<String, Advanced> advanceds = new HashMap<>();
    private String outputPath;


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
            GlobalConfig.configSer.setAdvanceds(advanceds);
            Serialize.serializeObject(GlobalConfig.configSer, GlobalConfig.AdvancedPath);
        });

        HBox hBoxItem = new HBox(10);
        hBoxItem.getChildren().addAll(checkBox);
        hBoxItem.setAlignment(Pos.CENTER);
        hBoxItem.setPadding(new Insets(10));
        return hBoxItem;
    }

    // 高级设置区的子项
    private Node itemSpinner(String title, Integer min, Integer max, Integer def, Integer step) {

        Spinner<Integer> spinner = new Spinner<>(min, max, def, step);
        spinner.setPrefWidth(75);
        advanceds.get(title).setValue(Integer.toString(def));
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            advanceds.get(title).setValue(Integer.toString(newValue));
            GlobalConfig.configSer.setAdvanceds(advanceds);
            Serialize.serializeObject(GlobalConfig.configSer, GlobalConfig.AdvancedPath);
        });

        HBox hbox = (HBox) item(title);
        hbox.getChildren().add(spinner);
        return hbox;
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
        flowPane2.getChildren().addAll(
                item("供应商"),
                item("集团"),
                item("控股"),
                item("深度查询分支机构（数据量巨大）"),
                item("未公示投资公司（无法确定占股比例）")
        );

        FlowPane flowPane3 = new FlowPane();
        flowPane3.getChildren().addAll(
                itemSpinner("投资比例", 1, 100, 51, 1),
                itemSpinner("最大延迟时间（秒），将会在1-n间随机延迟", 1, 100, 0, 1),
                itemSpinner("递归搜索N层公司", 1, 100, 1, 1),
                itemSpinner("超时时间（分钟）", 1, 20, 1, 1)

        );

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));

        // 这里添加
        vBox.getChildren().addAll(flowPane1, flowPane2, flowPane3);

        return vBox;
    }

    // 根据对应系统，下载 enscan
    private void downloadEnscan(String enscanName) {
        if (!new File(GlobalConfig.ExecPath + enscanName).isFile()) {
            Map<String, String> downloadMap = new HashMap<>();
            switch (GlobalConfig.configSer.getSrcMapIndex()) {
                case "gitee":
                    downloadMap = GlobalConfig.giteeSrcMap;
                    break;
                case "github":
                    downloadMap = GlobalConfig.githubSrcMap;
            }
            MessageBox.showInformation("插件暂未下载", "正在下载插件", "请稍后...");
            if (!Download.DownloadFile(downloadMap.get(enscanName),
                    GlobalConfig.ExecPath + enscanName)) {
                MessageBox.showErrorAlert("error", "文件下载失败，可能是超时导致，请确认网络链接是否有效，或者在配置中切换下载源地址");
                return;
            }
            if (!System.getProperty("os.name").contains("win")) {
                Cmd.run("chmod +x " + GlobalConfig.ExecPath + enscanName);
            }
        }
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
        // 使用的查询源
        String type = "";
        // 供应商
        String supplier = "";
        // 集团
        String isGroup = "";
        // 控股
        String hold = "";
        // 投资比例
        String invest = "";
        // 最大延迟时间
        String delay = "";
        // 递归搜索 n 层公司
        String deep = "";
        // 深度查询分支机构（数据量巨大）
        String isBranch = "";
        // 每个请求默认超时时间（默认1分钟）
        String timeout = "";
        // 包括未公示投资公司（无法确定占股比例）
        String uncertainInvest = "";
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
                case "供应商":
                    if (advanced.getStatus()) {
                        supplier = " -supplier";
                    }
                    break;
                case "集团":
                    if (advanced.getStatus()) {
                        isGroup = " -is-group";
                    }
                    break;
                case "控股":
                    if (advanced.getStatus()) {
                        hold = " -hold";
                    }
                    break;
                case "投资比例":
                    if (advanced.getStatus()) {
                        System.out.println(advanced.getValue());
                        invest = " -invest " + advanced.getValue();
                    }
                    break;
                case "最大延迟时间（秒），将会在1-n间随机延迟":
                    if (advanced.getStatus() && !"0".equals(advanced.getValue())) {
                        delay = " -delay " + advanced.getValue();
                    }
                    break;
                case "递归搜索N层公司":
                    if (advanced.getStatus()) {
                        deep = " -deep "+ advanced.getValue();
                    }
                    break;
                case "深度查询分支机构（数据量巨大）":
                    if (advanced.getStatus()){
                        isBranch = " -is-branch";
                    }
                    break;
                case "超时时间（默认1分钟）":
                    if (advanced.getStatus()){
                        timeout = " -timeout " + advanced.getValue();
                    }
                    break;
                case "未公示投资公司（无法确定占股比例）":
                    if (advanced.getStatus()){
                        uncertainInvest = " -uncertain-invest";
                    }
                    break;
            }
        }
        // 去掉最后一位上的逗号
        type = type.replaceAll(",+$", "");

        // 获取系统信息和架构信息
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        String enscanName = "";
        if (os.contains("mac")) {
            if (arch.contains("x86_64")) {
                enscanName = "enscan-0.0.15-darwin-amd64";
                downloadEnscan(enscanName);
            } else if (arch.contains("arm64")) {
                enscanName = "enscan-0.0.15-darwin-arm64";
                downloadEnscan(enscanName);
            }
        } else if (os.contains("win")) {
            if (arch.contains("amd64")) {
                enscanName = "enscan-0.0.15-windows-amd64.exe";
                downloadEnscan(enscanName);
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            if (arch.contains("amd64")) {
                enscanName = "enscan-0.0.15-linux-amd64";
                downloadEnscan(enscanName);
            } else if (arch.contains("arm64")) {
                enscanName = "enscan-0.0.15-linux-arm64";
                downloadEnscan(enscanName);
            } else if (arch.contains("arm")) {
                enscanName = "enscan-0.0.15-linux-arm";
                downloadEnscan(enscanName);
            } else if (arch.contains("386")) {
                enscanName = "enscan-0.0.15-linux-386";
                downloadEnscan(enscanName);
            }

        } else {
            MessageBox.showErrorAlert("error", "对不起，暂不支持您的系统架构！");
        }
        // 组装命令
        String cmd = GlobalConfig.ExecPath + enscanName + " -json -field icp -type " + type + " -o " + GlobalConfig.JsonPath +
                " -f " + GlobalConfig.JsonPath + "input.txt" + supplier + isGroup + hold + invest + delay + deep + isBranch + timeout + uncertainInvest;
        System.out.println(cmd);
        // 执行命令
        System.setProperty("file.encoding", "UTF-8");
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
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    // 查询导出至xlsx
    private void searchAndOutput() {



        // 删除文件夹里的所有文件
        FileIO.deleteFiles(new File(GlobalConfig.JsonPath));

        if ("".equals(textAreaInput.getText().trim())) {
            return;
        }

        // 把输入存入缓存文件
        FileIO.writeFile(GlobalConfig.JsonPath + "input.txt", textAreaInput.getText());

        // 组装查询来源
        // 使用的查询源
        String type = "";
        // 供应商
        String supplier = "";
        // 集团
        String isGroup = "";
        // 控股
        String hold = "";
        // 投资比例
        String invest = "";
        // 最大延迟时间
        String delay = "";
        // 递归搜索 n 层公司
        String deep = "";
        // 深度查询分支机构（数据量巨大）
        String isBranch = "";
        // 每个请求默认超时时间（默认1分钟）
        String timeout = "";
        // 包括未公示投资公司（无法确定占股比例）
        String uncertainInvest = "";
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
                case "供应商":
                    if (advanced.getStatus()) {
                        supplier = " -supplier";
                    }
                    break;
                case "集团":
                    if (advanced.getStatus()) {
                        isGroup = " -is-group";
                    }
                    break;
                case "控股":
                    if (advanced.getStatus()) {
                        hold = " -hold";
                    }
                    break;
                case "投资比例":
                    if (advanced.getStatus()) {
                        invest = " -invest " + advanced.getValue();
                    }
                    break;
                case "最大延迟时间（秒），将会在1-n间随机延迟":
                    if (advanced.getStatus() && !"0".equals(advanced.getValue())) {
                        delay = " -delay " + advanced.getValue();
                    }
                    break;
                case "递归搜索N层公司":
                    if (advanced.getStatus()) {
                        deep = " -deep "+ advanced.getValue();
                    }
                    break;
                case "深度查询分支机构（数据量巨大）":
                    if (advanced.getStatus()){
                        isBranch = " -is-branch";
                    }
                    break;
                case "超时时间（默认1分钟）":
                    if (advanced.getStatus()){
                        timeout = " -timeout " + advanced.getValue();
                    }
                    break;
                case "未公示投资公司（无法确定占股比例）":
                    if (advanced.getStatus()){
                        uncertainInvest = " -uncertain-invest";
                    }
                    break;
            }
        }
        // 去掉最后一位上的逗号
        type = type.replaceAll(",+$", "");

        // 获取系统信息和架构信息
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        String enscanName = "";
        if (os.contains("mac")) {
            if (arch.contains("x86_64")) {
                enscanName = "enscan-0.0.15-darwin-amd64";
                downloadEnscan(enscanName);
            } else if (arch.contains("arm64")) {
                enscanName = "enscan-0.0.15-darwin-arm64";
                downloadEnscan(enscanName);
            }
        } else if (os.contains("win")) {
            if (arch.contains("amd64")) {
                enscanName = "enscan-0.0.15-windows-amd64.exe";
                downloadEnscan(enscanName);
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            if (arch.contains("amd64")) {
                enscanName = "enscan-0.0.15-linux-amd64";
                downloadEnscan(enscanName);
            } else if (arch.contains("arm64")) {
                enscanName = "enscan-0.0.15-linux-arm64";
                downloadEnscan(enscanName);
            } else if (arch.contains("arm")) {
                enscanName = "enscan-0.0.15-linux-arm";
                downloadEnscan(enscanName);
            } else if (arch.contains("386")) {
                enscanName = "enscan-0.0.15-linux-386";
                downloadEnscan(enscanName);
            }

        } else {
            MessageBox.showErrorAlert("error", "对不起，暂不支持您的系统架构！");
        }
        // 组装命令
        String cmd = GlobalConfig.ExecPath + enscanName + " -field icp -type " + type + " -o " + GlobalConfig.configSer.getOutputPath() +
                " -f " + GlobalConfig.JsonPath + "input.txt" + supplier + isGroup + hold + invest + delay + deep + isBranch + timeout + uncertainInvest;
        System.out.println(cmd);
        // 执行命令
        System.setProperty("file.encoding", "UTF-8");
        Cmd.run(cmd);

        textAreaOutput.clear();
        textAreaOutput.appendText("导出至 -> " + GlobalConfig.configSer.getOutputPath());

    }

    public Node show(Stage primaryStage) {

        // 判断配置文件是否存在
        if (new File(GlobalConfig.AdvancedPath).isFile()) {

            // 加载配置文件
            GlobalConfig.configSer = (ConfigSer) Serialize.unSerializeObject(GlobalConfig.AdvancedPath);
            // 加载高级选项的配置文件
            if (GlobalConfig.configSer != null) {
                advanceds = GlobalConfig.configSer.getAdvanceds();
                outputPath = GlobalConfig.configSer.getOutputPath();
            }
        } else {
            Serialize.serializeObject(GlobalConfig.configSer, GlobalConfig.AdvancedPath);
        }

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
        Button buttonStartAndOutput = new Button("查询导出至xlsx");
        buttonStartAndOutput.setOnAction(event -> {

            if ("".equals(textAreaInput.getText())) {
                textAreaOutput.setText("请输入要查询的公司名称！");
                return;
            }

            // 打开目录选择器
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("文件保存至...");
            if (!GlobalConfig.configSer.getOutputPath().isEmpty()){
                // 设置上次使用过的路径
                directoryChooser.setInitialDirectory(new File(GlobalConfig.configSer.getOutputPath()));
            }
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null){
                // 保存路径
                GlobalConfig.configSer.setOutputPath(selectedDirectory.getPath());
                Serialize.serializeObject(GlobalConfig.configSer, GlobalConfig.AdvancedPath);
                System.out.printf(selectedDirectory.getPath());
            }

            Platform.runLater(() -> {

                Thread t = ProgressLoad.showProgress(progressBar, (p) -> {
                    searchAndOutput();
                });
                t.start();
            });
        });

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
        hBoxButtons.getChildren().addAll(buttonStartAndOutput, buttonStart);


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
