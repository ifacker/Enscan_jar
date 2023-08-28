package plugins.Enscan.GUI.Root;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RootPage {

    public Node show(Stage primaryStage){

        // 顶部
        Tab tabIcp = new Tab("ICP查询");
        tabIcp.setClosable(false);
        tabIcp.setContent(new IcpSearchPage().show(primaryStage));

//        Tab tabAdvanced = new Tab("高级查询");
//        tabAdvanced.setClosable(false);

        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(50);
        tabPane.getTabs().addAll(tabIcp);

        // Create layout
        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        return root;
    }
}
