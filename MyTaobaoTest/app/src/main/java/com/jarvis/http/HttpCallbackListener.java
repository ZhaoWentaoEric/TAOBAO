package com.jarvis.http;

import android.widget.Toast;

/**
 * Created by zjfcdf on 18-11-16.
 */

public interface HttpCallbackListener {
    // 网络连接成功
    void onFinish(String response);
    // 失败
    void onError(Exception ex);
}
