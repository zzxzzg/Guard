package com.guard.httpstest;

import android.content.Context;

/**
 * Created by yxwang on 15-12-3.
 */
public class HttpsClientTest extends BaseHttpsTest {
    public static String HTTPS_TEST_1="https://www.baidu.com";
    public static String HTTPS_TEST_2="https://certs.cac.washington.edu/CAtest/";

    @Override
    public void connect() {
        //DefaultHttpClient client;
    }

    @Override
    public void connectAllTrust() {
//        int timeOut = 30 * 1000;
//        HttpParams param = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(param, timeOut);
//        HttpConnectionParams.setSoTimeout(param, timeOut);
//        HttpConnectionParams.setTcpNoDelay(param, true);
//
//        SchemeRegistry registry = new SchemeRegistry();
//        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        registry.register(new Scheme("https", TrustAllSSLSocketFactory.getDefault(), 443));
//        ClientConnectionManager manager = new ThreadSafeClientConnManager(param, registry);
//        DefaultHttpClient client = new DefaultHttpClient(manager, param);
//
//        HttpGet request = new HttpGet("https://certs.cac.washington.edu/CAtest/");
//        // HttpGet request = new HttpGet("https://www.alipay.com/");
//        HttpResponse response = client.execute(request);
//        HttpEntity entity = response.getEntity();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
//        StringBuilder result = new StringBuilder();
//        String line = "";
//        while ((line = reader.readLine()) != null) {
//            result.append(line);
//        }
//        Log.e("HTTPS TEST", result.toString());

    }

    @Override
    public void connectSelfSign(Context context) {
//        int timeOut = 30 * 1000;
//        HttpParams param = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(param, timeOut);
//        HttpConnectionParams.setSoTimeout(param, timeOut);
//        HttpConnectionParams.setTcpNoDelay(param, true);
//
//        SchemeRegistry registry = new SchemeRegistry();
//        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        registry.register(new Scheme("https", TrustCertainHostNameFactory.getDefault(this), 443));
//        ClientConnectionManager manager = new ThreadSafeClientConnManager(param, registry);
//        DefaultHttpClient client = new DefaultHttpClient(manager, param);
//
//        // HttpGet request = new
//        // HttpGet("https://certs.cac.washington.edu/CAtest/");
//        HttpGet request = new HttpGet("https://www.alipay.com/");
//        HttpResponse response = client.execute(request);
//        HttpEntity entity = response.getEntity();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
//        StringBuilder result = new StringBuilder();
//        String line = "";
//        while ((line = reader.readLine()) != null) {
//            result.append(line);
//        }
//        Log.e("HTTPS TEST", result.toString());
    }



//    package com.example.httpstest;
//
//    import java.io.IOException;
//    import java.lang.reflect.Field;
//    import java.net.InetAddress;
//    import java.net.Socket;
//    import java.net.UnknownHostException;
//    import java.security.KeyManagementException;
//    import java.security.KeyStoreException;
//    import java.security.NoSuchAlgorithmException;
//    import java.security.UnrecoverableKeyException;
//    import java.security.cert.CertificateException;
//    import java.security.cert.X509Certificate;
//
//    import javax.net.ssl.SSLContext;
//    import javax.net.ssl.SSLException;
//    import javax.net.ssl.SSLSession;
//    import javax.net.ssl.SSLSocket;
//    import javax.net.ssl.TrustManager;
//    import javax.net.ssl.X509TrustManager;
//
//    import org.apache.http.conn.scheme.SocketFactory;
//    import org.apache.http.conn.ssl.SSLSocketFactory;
//    import org.apache.http.conn.ssl.X509HostnameVerifier;
//
//    import android.os.Build;
//
//    public class TrustAllSSLSocketFactory extends SSLSocketFactory {
//        private javax.net.ssl.SSLSocketFactory factory;
//        private static TrustAllSSLSocketFactory instance;
//
//        private TrustAllSSLSocketFactory() throws KeyManagementException, UnrecoverableKeyException,
//                NoSuchAlgorithmException, KeyStoreException {
//            super(null);
//
//            SSLContext context = SSLContext.getInstance("TLS");
//            context.init(null, new TrustManager[] { new TrustAllManager() }, null);
//            factory = context.getSocketFactory();
//            setHostnameVerifier(new X509HostnameVerifier() {
//
//                @Override
//                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public void verify(String host, X509Certificate cert) throws SSLException {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public void verify(String host, SSLSocket ssl) throws IOException {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public boolean verify(String host, SSLSession session) {
//                    // TODO Auto-generated method stub
//                    return true;
//                }
//            });
//        }
//
//        public static SocketFactory getDefault() {
//            if (instance == null) {
//                try {
//                    instance = new TrustAllSSLSocketFactory();
//                } catch (KeyManagementException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (UnrecoverableKeyException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (NoSuchAlgorithmException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (KeyStoreException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            return instance;
//        }
//
//        @Override
//        public Socket createSocket() throws IOException {
//            return factory.createSocket();
//        }
//
//        @Override
//        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
//                throws IOException, UnknownHostException {
//            if (Build.VERSION.SDK_INT < 11) { // 3.0
//                injectHostname(socket, host);
//            }
//
//            return factory.createSocket(socket, host, port, autoClose);
//        }
//
//        private void injectHostname(Socket socket, String host) {
//            try {
//                Field field = InetAddress.class.getDeclaredField("hostName");
//                field.setAccessible(true);
//                field.set(socket.getInetAddress(), host);
//            } catch (Exception ignored) {
//            }
//        }
//
//
//    }
//
//
//    package com.example.httpstest;
//
//    import java.io.IOException;
//    import java.io.InputStream;
//    import java.net.Socket;
//    import java.net.UnknownHostException;
//    import java.security.KeyManagementException;
//    import java.security.KeyStore;
//    import java.security.KeyStoreException;
//    import java.security.NoSuchAlgorithmException;
//    import java.security.UnrecoverableKeyException;
//    import java.security.cert.Certificate;
//    import java.security.cert.CertificateFactory;
//
//    import org.apache.http.conn.ssl.SSLSocketFactory;
//
//    import android.content.Context;
//
//    public class TrustCertainHostNameFactory extends SSLSocketFactory {
//
//        private static TrustCertainHostNameFactory mInstance;
//
//        public TrustCertainHostNameFactory(KeyStore truststore) throws NoSuchAlgorithmException,
//                KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//            super(truststore);
//        }
//
//        public static TrustCertainHostNameFactory getDefault(Context context) {
//            KeyStore keystore = null;
//            try {
//                CertificateFactory cf = CertificateFactory.getInstance("X.509");
//                InputStream in;
//                in = context.getAssets().open("load-der.crt");
//                Certificate ca = cf.generateCertificate(in);
//
//                keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//                keystore.load(null, null);
//                keystore.setCertificateEntry("ca", ca);
//
//                if (null == mInstance) {
//                    mInstance = new TrustCertainHostNameFactory(keystore);
//                }
//            } catch (Exception e) {
//
//            }
//            return mInstance;
//        }
//
//        @Override
//        public Socket createSocket() throws IOException {
//            return super.createSocket();
//        }
//
//        @Override
//        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
//                throws IOException, UnknownHostException {
//            return super.createSocket(socket, host, port, autoClose);
//        }
//
//    }


















}
