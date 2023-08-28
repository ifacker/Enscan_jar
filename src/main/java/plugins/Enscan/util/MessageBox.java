package plugins.Enscan.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.function.Consumer;

public class MessageBox {

    /**
     * 错误提示
     *
     * @param title   显示标题
     * @param message 显示提示的内容
     */
    public static void showErrorAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }


    /**
     * 展示二次确认信息，使用方法：
     * showConfiramtion("title", "message", response -> {
     *      if (response == ButtonType.OK) {
     *              System.out.println("用户点击了确定按钮。");
     *      } else {
     *              System.out.println("用户点击了取消按钮。");
     *      }
     * });
     *
     * @param title
     * @param message
     * @param action  传入的方法
     */
    public static void showConfirmation(String title, String message, Consumer<ButtonType> action) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait().ifPresent(action);
        });
    }

    public static void showInformation(String title,String header, String content){
        Platform.runLater(() -> {

            Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
            informationAlert.setTitle(title);
            informationAlert.setHeaderText(header);
            informationAlert.setContentText(content);
            informationAlert.showAndWait();
        });
    }
}
