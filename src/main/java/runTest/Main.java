package runTest;

import Config.GlobalConfig;
import Config.SaveConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage)  {

        // 创建主容器
        BorderPane root = new BorderPane();

        // 加载配置文件
        GlobalConfig.configTypeNow = new SaveConfig().load(GlobalConfig.configPath[1]);

        // 将选项卡面板添加到主容器的中心位置
        plugins.Enscan.Main test = new plugins.Enscan.Main();
        root.setCenter(test.getContent());

        // 创建场景并将主容器添加到场景中
        Scene scene = new Scene(root, 700, 550);

        // 设置舞台的标题和场景，并显示舞台
        primaryStage.setTitle(test.getName());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
