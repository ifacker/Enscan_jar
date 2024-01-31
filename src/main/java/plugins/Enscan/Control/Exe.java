package plugins.Enscan.Control;

import plugins.Enscan.Config.GlobalConfig;
import plugins.Enscan.util.Cmd;
import plugins.Enscan.util.Download;
import plugins.Enscan.util.MessageBox;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Exe {

    // 识别对应系统的enscan
    public String scanEnscan(){
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
        return enscanName;
    }

    // 根据对应系统，下载 enscan
    public void downloadEnscan(String enscanName) {
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
        Cmd.run(GlobalConfig.ExecPath + enscanName + " -v");
    }
}
