package com.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.TextUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.common.ApiResponse;
import com.github.pagehelper.PageHelper;
import com.user.entity.User;

@SuppressWarnings("deprecation")
public final class HttpClientHelper {
	/**
	 * @Description:使用HttpURLConnection发送post请求 测试通过
	 * @see HttpClientHelper#doJsonPost()
	 * @author:huzhanglin218
	 */
	public static String doJsonPost(String urlPath, String Json) {
		// HttpClient 6.0被抛弃了
		String result = "";
		BufferedReader reader = null;
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			// 设置文件类型:
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			// 设置接收类型否则返回415错误
			// conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("Accept-charset", "utf-8");
			// conn.setRequestProperty("Accept", "text/javascript, application/javascript,
			// application/ecmascript, application/x-ecmascript, */*; q=0.01");
			// conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			// conn.setRequestProperty("Accept-Language",
			// "zh-CN,en-US;q=0.8,zh;q=0.5,en;q=0.3");
			// conn.setRequestProperty("Connection","keep-alive");
			// conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64;
			// rv:52.0) Gecko/20100101 Firefox/52.0");
			// 往服务器里面发送数据
			if (Json != null && !TextUtils.isEmpty(Json)) {
				byte[] writebytes = Json.getBytes();
				// 设置文件长度
				conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
				OutputStream outwritestream = conn.getOutputStream();
				outwritestream.write(Json.getBytes());
				outwritestream.flush();
				outwritestream.close();
				System.out.println("hlhupload" + "  doJsonPost: conn  " + conn.getResponseCode());
			}
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				StringBuffer sb = new StringBuffer();
				String readLine = new String();
				while ((readLine = reader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 *
	 * 
	 * @param url 测试成功
	 * @see HttpClientHelper#doPost()
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map parameterMap) throws Exception {
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		System.out.println("POST parameter : " + parameterBuffer.toString());
		URL localURL = new URL(url);
		URLConnection connection = openConnection(localURL,null,null);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);
			outputStreamWriter.write(parameterBuffer.toString());
			outputStreamWriter.flush();
			// 响应失败
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}
			// 接收响应流
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (outputStreamWriter != null) {outputStreamWriter.close();}
			if (outputStream != null) {	outputStream.close();}
			if (reader != null) {reader.close();}
			if (inputStreamReader != null) {inputStreamReader.close();}
			if (inputStream != null) {inputStream.close();}
		}
		return resultBuffer.toString();
	}

	@SuppressWarnings("unused")
	private static  URLConnection openConnection(URL localURL,String proxyHost,Integer proxyPort) throws IOException {
		URLConnection connection;
		if (proxyHost != null && proxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			connection = localURL.openConnection(proxy);
		} else {
			connection = localURL.openConnection();
		}
		return connection;
	}

	/**
	 * @Description:使用URLConnection发送post  测试成功
	 * @author:liuyc
	 * @time:2016年5月17日 下午3:26:52
	 */
	public static String sendPost2(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> e : params.entrySet()) {
				sbParams.append(e.getKey());
				sbParams.append("=");
				sbParams.append(e.getValue());
				sbParams.append("&");
			}
		}
		URLConnection con = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		try {
			URL realUrl = new URL(urlParam);
			// 打开和URL之间的连接
			con = realUrl.openConnection();
			// 设置通用的请求属性
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			// con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			con.setDoOutput(true);
			con.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			osw = new OutputStreamWriter(con.getOutputStream(), charset);
			if (sbParams != null && sbParams.length() > 0) {
				// 发送请求参数
				osw.write(sbParams.substring(0, sbParams.length() - 1));
				// flush输出流的缓冲
				osw.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			resultBuffer = new StringBuffer();
			int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
			if (contentLength > 0) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
				String temp;
				while ((temp = br.readLine()) != null) {
					resultBuffer.append(temp);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:发送get请求保存下载文件
	 * @author:liuyc
	 * @time:2016年5月17日 下午3:27:29
	 */
	public static void sendGetAndSaveFile(String urlParam, Map<String, Object> params, String fileSavePath) {
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		HttpURLConnection con = null;
		BufferedReader br = null;
		FileOutputStream os = null;
		try {
			URL url = null;
			if (sbParams != null && sbParams.length() > 0) {
				url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
			} else {
				url = new URL(urlParam);
			}
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.connect();
			InputStream is = con.getInputStream();
			os = new FileOutputStream(fileSavePath);
			byte buf[] = new byte[1024];
			int count = 0;
			while ((count = is.read(buf)) != -1) {
				os.write(buf, 0, count);
			}
			os.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					os = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
		}
	}

	/**
	 * @Description:使用HttpURLConnection发送get请求  测试成功
	 * @author:liuyc 
	 * @see HttpClientHelper#sendGet()
	 * @time:2016年5月17日 下午3:27:29
	 */
	public static String sendGet(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		HttpURLConnection con = null;
		BufferedReader br = null;
		try {
			URL url = null;
			if (sbParams != null && sbParams.length() > 0) {
				url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
			} else {
				url = new URL(urlParam);
			}
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.connect();
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:使用URLConnection发送get请求  测试成功
	 * @author:liuyc
	 * @time:2016年5月17日 下午3:27:58
	 */
	public static String sendGet2(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		BufferedReader br = null;
		try {
			URL url = null;
			if (sbParams != null && sbParams.length() > 0) {
				url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
			} else {
				url = new URL(urlParam);
			}
			URLConnection con = url.openConnection();
			// 设置请求属性
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			con.connect();
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:使用HttpClient发送post请求  测试成功
	 * @author:liuyc
	 *  @see     HttpClientHelper#httpClientPost()
	 * @time:2016年5月17日 下午3:28:23
	 */
	public static String httpClientPost(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlParam);
		// 构建请求参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> elem = iterator.next();
			list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
		}
		BufferedReader br = null;
		try {
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				//UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
				httpPost.setEntity(entity);
			}
			/*httpPost.setHeader("Authorization", "token");
			httpPost.setHeader("Content-Type", "application/json");*/
			HttpResponse response = client.execute(httpPost);
			// 读取服务器响应数据
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:使用HttpClient发送get请求  测试通过
	 * @author:liuyc
	 * @see HttpClientHelper#httpClientGet()
	 * @time:2016年5月17日 下午3:28:56
	 */
	public static String httpClientGet(String urlParam, Map<String, Object> params, String charset) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		BufferedReader br = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				try {
					sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				sbParams.append("&");
			}
		}
		if (sbParams != null && sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
		HttpGet httpGet = new HttpGet(urlParam);
		try {
			HttpResponse response = client.execute(httpGet);
			// 读取服务器响应数据
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			resultBuffer = new StringBuffer();
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * @Description:使用socket发送post请求
	 * @author:liuyc
	 * @time:2016年5月18日 上午9:26:22
	 */
	public static String sendSocketPost(String urlParam, Map<String, Object> params, String charset) {
		String result = "";
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		Socket socket = null;
		OutputStreamWriter osw = null;
		InputStream is = null;
		try {
			URL url = new URL(urlParam);
			String host = url.getHost();
			int port = url.getPort();
			if (-1 == port) {
				port = 80;
			}
			String path = url.getPath();
			socket = new Socket(host, port);
			StringBuffer sb = new StringBuffer();
			sb.append("POST " + path + " HTTP/1.1\r\n");
			sb.append("Host: " + host + "\r\n");
			sb.append("Connection: Keep-Alive\r\n");
			sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");
			sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
			// 这里一个回车换行，表示消息头写完，不然服务器会继续等待
			sb.append("\r\n");
			if (sbParams != null && sbParams.length() > 0) {
				sb.append(sbParams.substring(0, sbParams.length() - 1));
			}
			osw = new OutputStreamWriter(socket.getOutputStream());
			osw.write(sb.toString());
			osw.flush();
			is = socket.getInputStream();
			String line = null;
			// 服务器响应体数据长度
			int contentLength = 0;
			// 读取http响应头部信息
			do {
				line = readLine(is, 0, charset);
				if (line.startsWith("Content-Length")) {
					// 拿到响应体内容长度
					contentLength = Integer.parseInt(line.split(":")[1].trim());
				}
				// 如果遇到了一个单独的回车换行，则表示请求头结束
			} while (!line.equals("\r\n"));
			// 读取出响应体数据（就是你要的数据）
			result = readLine(is, contentLength, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				} finally {
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							socket = null;
							throw new RuntimeException(e);
						}
					}
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					throw new RuntimeException(e);
				} finally {
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							socket = null;
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 测试有问题
	 * 
	 * @Description:使用socket发送get请求
	 * @author:liuyc
	 * @time:2016年5月18日 上午9:27:18
	 */
	public static String sendSocketGet(String urlParam, Map<String, Object> params, String charset) {
		String result = "";
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		Socket socket = null;
		OutputStreamWriter osw = null;
		InputStream is = null;
		try {
			URL url = new URL(urlParam);
			String host = url.getHost();
			int port = url.getPort();
			if (-1 == port) {
				port = 80;
			}
			String path = url.getPath();
			socket = new Socket(host, port);
			StringBuffer sb = new StringBuffer();
			sb.append("GET " + path + " HTTP/1.1\r\n");
			sb.append("Host: " + host + "\r\n");
			sb.append("Connection: Keep-Alive\r\n");
			sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");
			sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
			// 这里一个回车换行，表示消息头写完，不然服务器会继续等待
			sb.append("\r\n");
			if (sbParams != null && sbParams.length() > 0) {
				sb.append(sbParams.substring(0, sbParams.length() - 1));
			}
			osw = new OutputStreamWriter(socket.getOutputStream());
			osw.write(sb.toString());
			osw.flush();
			is = socket.getInputStream();
			String line = null;
			// 服务器响应体数据长度
			int contentLength = 0;
			// 读取http响应头部信息
			do {
				line = readLine(is, 0, charset);
				if (line.startsWith("Content-Length")) {
					// 拿到响应体内容长度
					contentLength = Integer.parseInt(line.split(":")[1].trim());
				}
				// 如果遇到了一个单独的回车换行，则表示请求头结束
			} while (!line.equals("\r\n"));
			// 读取出响应体数据（就是你要的数据）
			result = readLine(is, contentLength, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				} finally {
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							socket = null;
							throw new RuntimeException(e);
						}
					}
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					throw new RuntimeException(e);
				} finally {
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							socket = null;
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
		return result;
	}
	/**
	 *    
	 *  一:当然是要知道HTTP协议,知道常用的HTTP请求头,比如Host, Accept, Content-Type 知道HTTP协议支持的方法,常用有GET/POST/PUT/DELETE等如果不知道,也不用担心,我保证你读完这篇文章,你就知道一些啦,当然有个最好的参考文档就是HTTP相关的RFC文档,认真读一下肯定解决你自己心中的HTTP那些疑惑
	 *	二: 知道发送HTTP GET与POST格式很重要, 固定的格式如下:
     *  [REQUEST]<SP><URL><SP>[HTTP VERSION]<CLRF>
     *  [REQUEST HEADER: ]<SP>[VALUE]<CLRF>
                *        可以有多个请求头

       最后<CLRF>

       发送完HTTP请求头部以后, 针对不同请求如POST要发送内容部分,发送完成以后同样，以<CLRF>结尾.

       解释: <SP>表示空格, <CLRF>表示回车换行JAVA中表示为”\r\n”REQUEST表示HTTP请求命令,可以为POST, GET, PUT, DELETE等之一，HTTP VERSION的常见可能值为HTTP/1.1或者HTTP/1.0

     三: 如果1与2的知识你都具备了,下面就来介绍一下JAVA Socket的相关知识，如何创建一个JAVA客户端套接字Socket s = new Socket()如此即可,简单吧!如何连接到远程的主机与端口, 当提供URL字符串时候,可以这么做

   URL url = new URL(“http://blog.csdn.net/jia20003”);

   String host = url.getHost;

   int port = url.getDefaultPort();

  SocketAddress dest = new InetSocketAddress(this.host, this.port);

   s.connect(dest);


	 * @param params
	 * @param host
	 * @param port
	 * @return 
	 * @Description http请求
	 * @throws IOException
	 */
	public String sendSocketGet(Map<String, Object> params,String url,String host, int port) throws IOException  
    {  
		Socket socket = new Socket();  
        StringBuffer sbParams = new StringBuffer();
        sbParams.append(url);
		if (params != null && params.size() > 0) {
			sbParams.append("?");
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		
        SocketAddress dest = new InetSocketAddress(host,port);  
        socket.connect(dest);  
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());  
        BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);  
          
        bufferedWriter.write("GET " + sbParams + " HTTP/1.1\r\n");  
        bufferedWriter.write("Host: " + host + "\r\n");  
        bufferedWriter.write("\r\n");  
        bufferedWriter.flush();  
          
        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());  
        BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));  
        StringBuffer result = new StringBuffer();
        String line = null;  
        while((line = bufferedReader.readLine())!= null)  
        {  
				result.append(line);
        }  
        bufferedReader.close();  
        bufferedWriter.close();  
        socket.close(); 
        return result.toString();
    }  

	/**
	 * @Description:读取一行数据，contentLe内容长度为0时，读取响应头信息，不为0时读正文
	 * @time:2016年5月17日 下午6:11:07
	 */
	private static String readLine(InputStream is, int contentLength, String charset) throws IOException {
		List<Byte> lineByte = new ArrayList<Byte>();
		byte tempByte;
		int cumsum = 0;
		if (contentLength != 0) {
			do {
				tempByte = (byte) is.read();
				lineByte.add(Byte.valueOf(tempByte));
				cumsum++;
			} while (cumsum < contentLength);// cumsum等于contentLength表示已读完
		} else {
				do {
					tempByte = (byte) is.read();
					lineByte.add(Byte.valueOf(tempByte));
				} while (tempByte != 10);// 换行符的ascii码值为10
		}

		byte[] resutlBytes = new byte[lineByte.size()];
		for (int i = 0; i < lineByte.size(); i++) {
			resutlBytes[i] = (lineByte.get(i)).byteValue();
		}
		return new String(resutlBytes, charset);
	}

	// @Test
	public void httpClientGet() {
		String res = httpClientGet("http://localhost:8080/demo/get/orderdetail/1", null, "utf-8");
		System.out.println(res);
		JSONObject jsonObject = JSONObject.parseObject(res);
		System.out.println(jsonObject.get("code"));
		System.out.println(JSONObject.parseObject(jsonObject.getString("data")).getIntValue("ordersId"));
	}

	// @Test
	public void sendGet() {
		String res = sendGet("http://localhost:8080/demo/get/orderdetail/1", null, "utf-8");
		System.out.println(res);
		JSONObject jsonObject = JSONObject.parseObject(res);
		System.out.println(jsonObject.get("code"));
		System.out.println(JSONObject.parseObject(jsonObject.getString("data")).getIntValue("ordersId"));
	}

	// @Test 测试
	public void sendSocketGet() {
		String res = sendSocketGet("http://localhost:8080/demo/get/orderdetail/1", null, "utf-8");
		System.out.println(res);
		/*
		 * JSONObject jsonObject = JSONObject.parseObject(res);
		 * System.out.println(jsonObject.get("code"));
		 * System.out.println(JSONObject.parseObject(jsonObject.getString("data")).
		 * getIntValue("ordersId"));
		 */
	}

	@Test
	public void doJsonPost() {
		String url = "http://localhost:8080/user/";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "8");
		params.put("age", 13);
		params.put("userName", "sendPost");
		params.put("password", "password");
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(params);
		String res = doJsonPost(url, jsonObject.toString());
		System.out.println(res);
	}
	
	@Test
	public void doPost() throws Exception {
		String url = "http://localhost:8080/user/parameter";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "9");
		params.put("age", "13");
		params.put("userName", "sendPost");
		params.put("password", "password");
		String res = doPost(url, params);
		System.out.println(res);
	}
	
	@Test 
	public void httpClientPost() throws Exception {
		String url = "http://localhost:8080/user/parameter";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "11");
		params.put("age", "11");
		params.put("userName", "httpClientPost");
		params.put("password", "httpClientPost");
		String res = httpClientPost(url, params,"utf-8");
		System.out.println(res);
	}
	
	@Test
	public void sendSocketGetTest() throws IOException {
		Map<String, Object> params = null;//http://localhost:8080/demo/get/orderdetail/1
		String res = sendSocketGet(params, "demo/get/orderdetail/1", "127.0.0.1", 8080);
		
		
		System.out.println(res);
		/*JSONObject jsonObject = JSONObject.parseObject(res);
		System.out.println(jsonObject.get("code"));
		System.out.println(JSONObject.parseObject(jsonObject.getString("data")).getIntValue("ordersId"));*/
	}
	
}

/**
 * 
 * @RequestMapping(value = "/", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public ApiResponse<?> listUser() {
		PageHelper.startPage(1, 1);
		List<User> user = userService.listUser();
		return ApiResponse.successResponse(user);
	}

	// 处理"/users/"的POST请求，用来创建User
	// 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ApiResponse<?> postUser(@RequestBody User user) {
		 userService.put(user.getId(), user);
		 ApiResponse<?> apiResponse = ApiResponse.successResponse();
		 return apiResponse;
	}
	
	@RequestMapping(value = "/parameter", method = RequestMethod.POST)
	public ApiResponse<?> postUser(HttpServletRequest request) {
		
		 User user = new User();
		 user.setId(Integer.parseInt(request.getParameter("id")));
		 user.setAge(request.getParameter("age"));
		 user.setPassword(request.getParameter("password"));
		 user.setUserName(request.getParameter("userName"));
		 userService.put(user .getId(), user);
		 ApiResponse<?> apiResponse = ApiResponse.successResponse();
		 return apiResponse;
	}

	// 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
	// url中的id可通过@PathVariable绑定到函数的参数中
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ApiResponse<?> getUser(@PathVariable int id) {
		User user = userService.get(id);
		return ApiResponse.successResponse(user);
	}

	// 处理"/users/{id}"的PUT请求，用来更新User信息
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ApiResponse<?> putUser( @RequestBody User user) {
		User u = userService.get(user.getId());
		u.setUserName(user.getUserName());
		u.setAge(user.getAge());
		userService.update(u.getId(), u);·
		return ApiResponse.successResponse();
	}*/

	// 处理"/users/{id}"的DELETE请求，用来删除User
/*
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ApiResponse<?> deleteUser(@PathVariable int id) {
		userService.remove(id);
		return ApiResponse.successResponse();
	} 
 */
