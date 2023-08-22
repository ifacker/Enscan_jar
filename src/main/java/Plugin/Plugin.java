package Plugin;


import javafx.scene.Node;

// 插件接口
public interface Plugin {

    // 获取插件名
    String getName();

    // 获取插件内容
    Node getContent();
}
