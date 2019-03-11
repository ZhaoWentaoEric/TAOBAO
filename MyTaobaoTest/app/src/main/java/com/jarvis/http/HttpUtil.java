package com.jarvis.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zjfcdf on 18-11-16.
 */

public class HttpUtil {

    public static void getByOkHttp(final String address,
                                   final okhttp3.Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();

                    // 声明请求
                    Request request = new Request.Builder()
                            .url(address)
                            .build();
                    // 客户端发起请求
                    client.newCall(request).enqueue(callback);


                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public static String getByHttpUrlConn(final String address,
                                        final HttpCallbackListener listener
    ){
         final String result =null;       // 所有的网络处理－－>线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                BufferedReader reader = null;
                try {
                    // 1) 连接
                    URL url = new URL(address);
                    conn = (HttpURLConnection) url.openConnection();
                    // 设置属性
                    conn.setRequestMethod("GET"); // POST ?para=
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    // 2) 读取网页信息
                    InputStream in = conn.getInputStream(); // 二进制流
                    // 二进制流-->字符流-->缓冲流
                    reader = new BufferedReader( new InputStreamReader( in ) );

                    final StringBuilder builder = new StringBuilder();
                    String line = reader.readLine();
                //  result =reader.readLine();

                    while ( line != null ){
                        builder.append(line);
                        line = reader.readLine();

                        //result = line;

                    }

                    // 网络成功-->无法返回值 builder.toString
                    // 回调函数
                    if( listener != null ){
                        listener.onFinish(builder.toString());
                    }


                }
                catch (Exception ex){
                    ex.printStackTrace();
                    // 失败
                    if( listener != null ){
                        listener.onError(ex);
                    }
                }
                finally {
                    if( reader != null ){
                        try {
                            reader.close();
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    if( conn != null ){
                        conn.disconnect();
                    }
                }
            }
        }).start();
        return result;
    }
}
