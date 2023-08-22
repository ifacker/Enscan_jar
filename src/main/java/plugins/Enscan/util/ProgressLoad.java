package plugins.Enscan.util;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import java.util.function.Consumer;

public class ProgressLoad {
    /**
     * 用于展示加载状态
     * @param progressBar 传入 Progress 这个参数
     * @param action 可以传入匿名函数
     *               如：showProgress(progress, (p) -> {});
     *
     */
    public static Thread showProgress(ProgressIndicator progressBar, Consumer<ProgressIndicator> action){
        progressBar.setVisible(true);

        // 创建并启动任务
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call(){
                action.accept(progressBar);
                return null;
            }
        };
        task.setOnSucceeded(e->{
            progressBar.setVisible(false);
        });
        return new Thread(task);
    }
}
