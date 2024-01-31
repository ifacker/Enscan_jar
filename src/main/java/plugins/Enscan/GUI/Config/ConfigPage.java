package plugins.Enscan.GUI.Config;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plugins.Enscan.Config.GlobalConfig;
import plugins.Enscan.Control.Exe;
import plugins.Enscan.DataType.Common;
import plugins.Enscan.DataType.ConfigSer;
import plugins.Enscan.DataType.YamlConfig;
import plugins.Enscan.util.FileIO;
import plugins.Enscan.util.JacksonYaml;
import plugins.Enscan.DataType.CookieLabel;
import plugins.Enscan.util.S2L;
import plugins.Enscan.util.Serialize;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class ConfigPage {

//    private YamlConfig yamlConfig;

    public Node show(Stage primaryStage) {

        // 获取配置文件
        loadConfig();

        VBox contentVBox = new VBox();
        contentVBox.setSpacing(10);

        VBox vBoxComboBox = new VBox(10);
        vBoxComboBox.setPadding(new Insets(10));

        // 选择源地址
        Label label = new Label("选择源：");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("gitee", "github");
        comboBox.setPrefWidth(10000);
        comboBox.getSelectionModel().select(GlobalConfig.configSer.getSrcMapIndex());
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            GlobalConfig.configSer.setSrcMapIndex(newValue);
            Serialize.serializeObject(GlobalConfig.configSer, GlobalConfig.AdvancedPath);
        });

        vBoxComboBox.getChildren().addAll(label, comboBox);

        contentVBox.getChildren().add(vBoxComboBox);

        reSet(contentVBox);

        // 创建按钮
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER_RIGHT);

        Button buttonReset = new Button("重置");
        buttonReset.setOnAction(event -> {
            loadConfig();
            contentVBox.getChildren().clear();
            reSet(contentVBox);
        });
        Button buttonSave = new Button("保存");
        buttonSave.setOnAction(event -> {
            String body = JacksonYaml.serialization(GlobalConfig.yamlConfig);
            FileIO.writeFile(GlobalConfig.ConfigPath, body);
        });

        hBox.getChildren().addAll(buttonReset, buttonSave);

        // 显示版本号
        Label labelVersion = new Label();
        if (GlobalConfig.yamlConfig != null ){
            labelVersion.setText("Version: " + GlobalConfig.yamlConfig.getVersion());
        }

        HBox hBoxVersion = new HBox(10);
        hBoxVersion.setAlignment(Pos.CENTER_LEFT);
        hBoxVersion.setPadding(new Insets(10));
        hBoxVersion.getChildren().add(labelVersion);

        BorderPane versionAndButton = new BorderPane();
        versionAndButton.setLeft(hBoxVersion);
        versionAndButton.setRight(hBox);


        // Create layout

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(contentVBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true); // 自动调整ScrollPane的宽度以适应场景

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(versionAndButton);


        return borderPane;
    }

    private Node labelAndText(String name, String text){
        return labelAndText(name, text, null);
    }

    private Node labelAndText(String name, String text, CookieLabel cookieLabel) {
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));

        Label label = new Label(name);

        TextArea textArea = new TextArea();
        textArea.setText(text);
        textArea.setWrapText(true);
        textArea.setPrefHeight(100);
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {

            if (cookieLabel != null) {
                switch (cookieLabel){
                    case AIQICHA:
                        GlobalConfig.yamlConfig.getCookies().put(CookieLabel.AIQICHA.getKey(), newValue);
                        break;
                    case TIANYANCHA:
                        GlobalConfig.yamlConfig.getCookies().put(CookieLabel.TIANYANCHA.getKey(), newValue);
                        break;
                    case TYCID:
                        GlobalConfig.yamlConfig.getCookies().put(CookieLabel.TYCID.getKey(), newValue);
                        break;
                    case ALDZS:
                        GlobalConfig.yamlConfig.getCookies().put(CookieLabel.ALDZS.getKey(), newValue);
                        break;
                    case QIMAI:
                        GlobalConfig.yamlConfig.getCookies().put(CookieLabel.QIMAI.getKey(), newValue);
                        break;
                    case CHINAZ:
                        GlobalConfig.yamlConfig.getCookies().put(CookieLabel.CHINAZ.getKey(), newValue);
                        break;
                    case VERYVP:
                        GlobalConfig.yamlConfig.getCookies().put(CookieLabel.VERYVP.getKey(), newValue);
                        break;
                }
            }else if (name.equals("默认导出路径：")){
                GlobalConfig.yamlConfig.getCommon().setOutput(newValue);
            } else if (name.equals("查询字段，如：[\"website\"]：")){
                GlobalConfig.yamlConfig.getCommon().setField(S2L.Str2List(newValue));
            }

        });


        vBox.getChildren().addAll(label, textArea);

        return vBox;
    }

    private void loadConfig() {
        if (Files.exists(Paths.get(GlobalConfig.ConfigPath))){
            GlobalConfig.yamlConfig = JacksonYaml.unSerialization(FileIO.readFile(GlobalConfig.ConfigPath));
        } else {

            Common common = new Common();
            common.setField(null);
            common.setOutput("");

            Map<String, String> cookies = new HashMap<>();
            cookies.put("veryvp", "");
            cookies.put("chinaz", "");
            cookies.put("qimai", "");
            cookies.put("aldzs", "");
            cookies.put("tycid", "");
            cookies.put("tianyancha", "");
            cookies.put("aiqicha", "");

            YamlConfig yamlConfig = new YamlConfig();
            yamlConfig.setVersion(0.4);
            yamlConfig.setCommon(common);
            yamlConfig.setCookies(cookies);

            String yamlStr = JacksonYaml.serialization(yamlConfig);
            FileIO.writeFile(GlobalConfig.ConfigPath, yamlStr);
            loadConfig();
        }
    }

    private void reSet(VBox contentVBox) {
        // output
        if (GlobalConfig.yamlConfig != null) {
//            contentVBox.getChildren().add(labelAndText("默认导出路径：", GlobalConfig.yamlConfig.getCommon().getOutput()));
//            contentVBox.getChildren().add(labelAndText("查询字段，如：[\"website\"]：", GlobalConfig.yamlConfig.getCommon().getField().toString()));


            // 遍历 cookie
            for (Map.Entry<String, String> cookie : GlobalConfig.yamlConfig.getCookies().entrySet()) {
                switch (cookie.getKey()) {
                    case "aiqicha":
//                        CookieLabel aiqicha = CookieLabel.AIQICHA;
                        contentVBox.getChildren().add(labelAndText(CookieLabel.AIQICHA.getValue(), cookie.getValue(), CookieLabel.AIQICHA));
                        break;
                    case "tianyancha":
//                        CookieLabel tianyancha = CookieLabel.TIANYANCHA;
                        contentVBox.getChildren().add(labelAndText(CookieLabel.TIANYANCHA.getValue(), cookie.getValue(), CookieLabel.TIANYANCHA));
                        break;
                    case "tycid":
//                        CookieLabel tycid = CookieLabel.TYCID;
                        contentVBox.getChildren().add(labelAndText(CookieLabel.TYCID.getValue(), cookie.getValue(),CookieLabel.TYCID));
                        break;
                    case "aldzs":
//                        CookieLabel aldzs = CookieLabel.ALDZS;
                        contentVBox.getChildren().add(labelAndText(CookieLabel.ALDZS.getValue(), cookie.getValue(),CookieLabel.ALDZS));
                        break;
                    case "qimai":
//                        CookieLabel qimai = CookieLabel.QIMAI;
                        contentVBox.getChildren().add(labelAndText(CookieLabel.QIMAI.getValue(), cookie.getValue(),CookieLabel.QIMAI));
                        break;
                    case "chinaz":
//                        CookieLabel chinaz = CookieLabel.CHINAZ;
                        contentVBox.getChildren().add(labelAndText(CookieLabel.CHINAZ.getValue(), cookie.getValue(),CookieLabel.CHINAZ));
                        break;
                    case "veryvp":
//                        CookieLabel veryvp = CookieLabel.VERYVP;
                        contentVBox.getChildren().add(labelAndText(CookieLabel.VERYVP.getValue(), cookie.getValue(),CookieLabel.VERYVP));
                        break;
                }
            }
        }
    }
}
