package com.jarvis.mytaobao.user;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jarvis.http.GetHttp;
import com.jarvis.http.HttpCallbackListener;
import com.jarvis.http.HttpUtil;
import com.jarvis.mytaobao.home.Home_F;
import com.jarvis.mytaobao.home.WareActivity;
import com.jarvis.mytaobaotest.R;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity {
    private Button btn;
    private EditText uid;
    private EditText upassword;
    private String account_name;

   public  static final String web="http:// 192.168.43.251/zwt_test/test/test.jsp";
   // public static final String web1="http://60.186.66.124/zwt_test/test/login.jsp";
    public static final String web1="http://192.168.43.251/zwt_test/test/login.jsp";
    GetHttp getHttp = null;
    HttpUtil gerhttp =null;
    String toast = null;

    String result = "";
    TextView _tvResult;

    HttpCallbackListener httpCallbackListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        _tvResult =(TextView)findViewById(R.id.tv_Result);
        setContentView(R.layout.activity_login);
        btn = (Button)findViewById(R.id.bt_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
               jsonObject();
               //show("111");
            }
        });
    }

    private void show(final String r){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), r,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try{

                    uid = (EditText)findViewById(R.id.ed_id);
                    upassword =(EditText)findViewById(R.id.ed_password);
                    final String email = uid.getText().toString();
                    final String pass = upassword.getText().toString();
                    OkHttpClient client = new OkHttpClient();
                    show(email+pass);

                    RequestBody requestBody = new FormBody.Builder()
                            .add("uid", email)
                            .add("upassword", pass)
                            .build();
                    Request request = new Request.Builder()
                            .url(web1)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    final String data = response.body().string();
                    final String result = data;
                   // show("222");
                    if(data.trim().equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(LoginActivity.this, WareActivity.class);
                                intent.putExtra("uid",email);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("account_name", account_name);
//                                intent.putExtras(bundle);
                                //setResult(1,intent);

                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    else {
                        show("用户名或密码错误"+data.trim());

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private  void jsonObject(){
      _tvResult = (TextView)findViewById(R.id.tv_Result);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();

                    // 声明请求
                    Request request = new Request.Builder()
                            .url(web1)
                            .build();
                    // 客户端发起请求
                    Response response = client.newCall(request).execute();
                    // 返回值
                    final String data = response.body().string();
                    // 解析ｊｓｏｎ
                    final String result = parseJsonObject(data);
                    // 显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("111"+data);
                            System.out.println("222"+result);

                            show(result);
                           _tvResult.setText(result);
                        }
                    });
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    private String parseJsonObject(String jsonStr){
        StringBuilder builder = new StringBuilder();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);// 生成json数组
            for( int i = 0; i < jsonArray.length(); ++i ){
                JSONObject object = jsonArray.getJSONObject(i);// 获取第i个元素
                int id = object.getInt("id");
                String password = object.getString("password");

                builder.append(id).append("-")
                        .append(password).append("\n");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return builder.toString();
    }




}
