package plugins.Enscan.util;

import Internet.ProxyInternet;
import okhttp3.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Download {

    /**
     * 下载文件
     * @param url 需要下载的 URL
     * @param savePath 保存路径
     * @return 如果下载成功，返回 true，否则，返回 false；
     */
    public static boolean DownloadFile(String url, String savePath) {
        OkHttpClient client = new ProxyInternet().newClient();

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);

        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                System.out.println("文件下载失败，HTTP状态吗：" + response.code());
                return false;
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                System.out.println("文件下载失败，相应体为空");
                return false;
            }

            BufferedInputStream inputStream = new BufferedInputStream(responseBody.byteStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));

            byte[] buffer = new byte[8192];
            int byteRead;
            while ((byteRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }

            fileOutputStream.close();
            inputStream.close();

//            System.out.println("下载完成");
            return true;
        } catch (IOException e) {
            System.out.println("文件下载失败\n" + e);
        }
        return false;
    }
}
