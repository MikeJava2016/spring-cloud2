package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**  HttpClient 是 Apache Jakarta Common 下的子项目，可以用来提供高效的、最新的、功能丰富的支持 HTTP </br>
    * 		协议的客户端编程工具包，并且它支持 HTTP 协议最新的版本和建议。</br>
	*		使用HttpClient发送请求、接收响应很简单，一般需要如下几步即可：</br>
	*	(1)创建CloseableHttpClient对象。</br>
	*	(2)创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。</br>
	*	(3)如果需要发送请求参数，可可调用setEntity(HttpEntity entity)方法来设置请求参数。setParams方法已过时（4.4.1版本）。</br>
	*	(4)调用HttpGet、HttpPost对象的setHeader(String name, String value)方法设置header信息，
	*		或者调用setHeaders(Header[] headers)设置一组header信息。</br>
	*	(5)调用CloseableHttpClient对象的execute(HttpUriRequest request)发送请求，
	*	该方法返回一个CloseableHttpResponse。</br>
	*	(6)调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了
	*	服务器的响应内容。程序可通过该对象获取服务器的响应内容；调用CloseableHttpResponse的
	*	getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头。</br>
	*	(7)释放连接。无论执行方法是否成功，都必须释放连接</br>
	*/
public class SimpleHttpClientDemo {
	 /**
     * 模拟请求
     * 
     * @param url        资源地址
     * @param map    参数列表
     * @param encoding    编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String send(String url, Map<String,String> map,String encoding) 
    		throws ParseException, IOException{
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());
        
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
    
    /**
     * 模拟请求
     * 
     * @param url        资源地址
     * @param map    参数列表
     * @param encoding    编码
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String send2(String url, Map<String,String> map,String encoding) 
    		throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        String body = "";
        //采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();
        
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
          //  .register("https", new SSLConnectionSocketFactory(sslcontext))
            .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        //创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
//        CloseableHttpClient client = HttpClients.createDefault();
        
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());
        
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
    
    /**
     * 模拟请求
     * 
     * @param url        资源地址
     * @param map    参数列表
     * @param encoding    编码
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws KeyManagementException 
     * @throws NoSuchAlgorithmException 
     * @throws ClientProtocolException 
     */
    public static String send3(String url, Map<String,String> map,String encoding) throws ClientProtocolException, IOException {
        String body = "";
        
        //tomcat是我自己的密钥库的密码，你可以替换成自己的
        //如果密码为空，则用"nopassword"代替
        SSLContext sslcontext = custom("D:\\keys\\wsriakey", "tomcat");
        
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", new SSLConnectionSocketFactory(sslcontext))
            .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        //创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
//        CloseableHttpClient client = HttpClients.createDefault();
        
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());
        
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
    /**
     * 绕过验证
     *     
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
    
    /**
     * 设置信任自签名证书
     *     
     * @param keyStorePath        密钥库路径
     * @param keyStorepass        密钥库密码
     * @return
     */
    public static SSLContext custom(String keyStorePath, String keyStorepass){
        SSLContext sc = null;
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            trustStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
        } catch (KeyStoreException | NoSuchAlgorithmException| CertificateException | IOException | KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
            }
        }
        return sc;
    }
}
