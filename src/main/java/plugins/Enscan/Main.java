package plugins.Enscan;

import Plugin.Plugin;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plugins.Enscan.GUI.Config.ConfigPage;
import plugins.Enscan.GUI.Root.RootPage;


public class Main implements Plugin {
    @Override
    public String getName() {
        return "Enscan";
    }

    @Override
    public Node getContent(Stage primaryStage) {
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

        // 命令行 tab
//        Tab tabCmd = new Tab("命令行操作");
//        tabCmd.setClosable(false);
//        tabCmd.setContent(new Cmd().showCmd());

//        tabPane.getTabs().addAll(tabRoot, tabCmd);

        tabPane.getTabs().addAll(tabRoot, tabConfig);

        content.getChildren().addAll(tabPane);

        return content;
    }
}
