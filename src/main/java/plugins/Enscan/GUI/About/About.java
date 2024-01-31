package plugins.Enscan.GUI.About;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import plugins.Enscan.Config.GlobalConfig;

public class About {
    public Node show(Stage primaryStage) {

        // 版本号
        String content = String.format("Enscan_jar Version: %s\nEnscan Version: %s", GlobalConfig.Version, GlobalConfig.Enscan_Version);
        Label label = new Label(content);

        BorderPane root = new BorderPane();
        root.setCenter(label);
        return root;
    }
}
