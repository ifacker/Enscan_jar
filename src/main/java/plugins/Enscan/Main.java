package plugins.Enscan;

import Plugin.Plugin;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plugins.Enscan.Config.GlobalConfig;
import plugins.Enscan.GUI.About.About;
import plugins.Enscan.GUI.Config.ConfigPage;
import plugins.Enscan.GUI.Root.RootPage;
import plugins.Enscan.util.FileIO;


public class Main implements Plugin {

    // 初始化目录
    private void init(){
        FileIO.createFolders(GlobalConfig.JsonPath);
    }

    @Override
    public String getName() {
        return "Enscan";
    }

    @Override
    public Node getContent(Stage primaryStage) {

        // 初始化目录
        init();

        // 创建插件的内容
        VBox content = new VBox(10);

        // 创建选项卡
        TabPane tabPane = new TabPane();
        // 设置每个 tab 最少 50 这么宽
        tabPane.setTabMinWidth(50);

        // GUI 主页面
        Tab tabRoot = new Tab("主界面");
        tabRoot.setClosable(false);
        tabRoot.setContent(new RootPage().show(primaryStage));

        // 配置页面
        Tab tabConfig = new Tab("配置");
        tabConfig.setClosable(false);
        tabConfig.setContent(new ConfigPage().show(primaryStage));

        // about 页面
        Tab tabAbout = new Tab("关于");
        tabAbout.setClosable(false);
        tabAbout.setContent(new About().show(primaryStage));

        // 命令行 tab
//        Tab tabCmd = new Tab("命令行操作");
//        tabCmd.setClosable(false);
//        tabCmd.setContent(new Cmd().showCmd());

//        tabPane.getTabs().addAll(tabRoot, tabCmd);

        tabPane.getTabs().addAll(tabRoot, tabConfig, tabAbout);

        content.getChildren().addAll(tabPane);

        return content;
    }
}
