package Internet;

import Config.GlobalConfig;
import Type.ProxyType;
import com.google.common.collect.ImmutableList;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.List;

public class ProxyInternet {
    /**
     * 此方法仅用于设置代理
     * @param type
     * @param proxyType
     * @return
     */
    public OkHttpClient newProxy(Proxy.Type type, ProxyType proxyType) {

        // 创建自定义的 TrustManager，信任所有证书
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }};

        // 创建 SSLContext，并使用自定义的 TrustManager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        // 创建 OkHttp 客户端
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier((hostname, session) -> true)
                .proxySelector(new ProxySelector() {
                    @Override
                    public List<Proxy> select(java.net.URI uri) {
                        Proxy tmpProxy = new Proxy(type, new InetSocketAddress(proxyType.getIp(), Integer.parseInt(proxyType.getPort())));
                        return ImmutableList.of(tmpProxy);
                    }

                    @Override
                    public void connectFailed(java.net.URI uri, java.net.SocketAddress sa, IOException ioe) {
                        System.out.println("连接失败：" + uri);
                    }
                })
                .proxyAuthenticator((route, response) -> {
                    String credential = Credentials.basic(proxyType.getUserName(), proxyType.getPassword());
                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                })
                .build();
    }

    /**
     * 程序内用到的所有 http 请求，请使用该函数
     * @return OkHttpClient
     */
    public OkHttpClient newClient(){
        if (GlobalConfig.configTypeNow.getProxyType().getStatus().equals("启用")){
            Proxy.Type type = Proxy.Type.SOCKS;
            if (GlobalConfig.configTypeNow.getProxyType().getTransport() == 0) {
                type = Proxy.Type.HTTP;
            }
            return newProxy(type, GlobalConfig.configTypeNow.getProxyType());
        }else {
            // 创建自定义的 TrustManager，信任所有证书
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }};

            // 创建 SSLContext，并使用自定义的 TrustManager
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }

            // 创建 OkHttp 客户端
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        }
    }
}

