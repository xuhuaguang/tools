package com.hengyu.tools.http;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 项目名称：xunmei.paid.wallet.online.pay
 * 项目版本：V1.0
 * 包名称：com.xunmei.paid.wallet.online.pay.util
 * 创建人：yuqy
 * 创建时间：2017/6/15 18:04
 * 修改人：yuqy
 * 修改时间：2017/6/15 18:04
 * 修改备注：
 */
public class NetTools {

    /**
     * 使用Get方式获取数据
     *
     * @param url
     *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
     * @param charset
     * @return
     */
    public static String sendGet(String url, String charset) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求，字符串形式数据,通过printWrite 发送字符流
     * @param url 请求地址
     * @param param 请求数据
     * @param charset 编码方式
     */
    public static String sendPostUrl(String url, String param, String charset,String contentType) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    /**
     * POST请求，Map形式数据,通过printWrite 发送字符流
     * @param url 请求地址
     * @param param 请求数据
     * @param charset 编码方式
     */
    public static String sendPost(String url, Map<String, String> param,
                                  String charset) {

        StringBuffer buffer = new StringBuffer();
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                buffer.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue()))
                        .append("&");

            }
        }
        buffer.deleteCharAt(buffer.length() - 1);

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(buffer);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }



    /**
     * POST请求，字符串形式数据,通过printWrite 发送字符流
     * @param url 请求地址
     * @param param 请求数据
     * @param charset 编码方式
     */
    public static String sendPostByStream(String url, byte[] bytes, String charset,String contentType) {

        PrintStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintStream(conn.getOutputStream());
            // 发送请求参数
            out.print(bytes);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }



    public static void main(String[] args) throws IOException {
/*		String res = HttpUtils.sendGet("http://app.kookong.com/m/brands?app=android&deviceType=2&version=101&sdk=18", "utf-8");
		System.out.println("res = [" + res + "]");


		Map<String, String> params = new HashMap<String, String>();
        params.put("app", "android");
        params.put("deviceType", "2");
        params.put("version", "101");
        params.put("sdk", "18");
		String res2 = HttpUtils.sendPost("http://app.kookong.com/m/brands", params,  "iso88591-1");
		System.out.println("res2 = [" + res2 + "]");*/

/*		String url = "http://localhost:8000/";
		RetData retData = new RetData();
		retData.setContent("testJson req");
		String param = JSON.toJSONString(retData);

		String charset = "utf-8";
		String contentType = "application/json";

		HttpUtils.sendPostUrl(url, param, charset, contentType);
 */
        /*SortedMap<String,String> params = new TreeMap();
        params.put("orderNo","aoydoaydo3");
        params.put("payAmt","22");
        params.put("name","于起宇");
        String json = JSON.toJSONString(params);
        String sign = EncryotTools.md5Encrypt( json + AlipayConfig.SIGN_KEY);
        params.put("sign",sign);

        json = JSON.toJSONString(params);

        String res = NettyUtils.sendPost("http://127.0.0.1:9090/mpcctp/notify/mobile/website/alipay.json",params,"utf-8");
        //String res = NettyUtils.sendPostUrl("http://127.0.0.1:9090/mpcctp/notify/mobile/website/alipay.json",json,"utf-8","application/json");
        System.out.println("res2 = [" + res + "]");*/
    }
}
