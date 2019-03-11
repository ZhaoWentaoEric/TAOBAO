package com.jarvis.mytaobao.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jarvis.mytaobao.user.LoginActivity;
import com.jarvis.mytaobaotest.R;
import com.javis.Bean.Bean_DingDan;
import com.javis.Bean.Bean_ListView_ware;
import com.zdp.aseo.content.AseoZdpAseo;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 确认订单界面
 *
 *
 */
public class BuynowActivity extends Activity {

	final  String url ="";
	private TextView bt_ok,bt_back;
	private Button bt_buy_ok;
	String Get_id;
	String Get_name;
	String Get_price;
	String Get_Salenum;
	String Get_adress;
	String Get_uid;
	Bean_DingDan bean_dingDan = new Bean_DingDan();
	String DingDanHao;
	TextView name;
	public static final String web="http://192.168.43.251/zwt_test/test/buy.jsp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_now_a);
		initView();
		Intent intent =getIntent();
		Get_id = intent.getStringExtra("id");
		Get_uid = intent.getStringExtra("uid")
;		Get_name = intent.getStringExtra("name");
		Get_price=intent.getStringExtra("price");
		Get_Salenum= intent.getStringExtra("get_Salenum");
		Get_adress = intent.getStringExtra("adress");
		name = (TextView)findViewById(R.id.tv_name);
		name.setText(Get_name);


	}

	private void initView(){
		bt_back=(TextView) findViewById(R.id.bt_buy_back);
//		name =(TextView)findViewById((R.id.tv_name));
//		name.setText(Get_name);
		//show(Get_id+"+++++++");
		bt_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
//		bt_ok=(TextView) findViewById(R.id.tv_buy_ok);
//		bt_ok.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				//Toast.makeText(BuynowActivity.this, "暂无法支付", Toast.LENGTH_SHORT).show();
//				if(Get_uid==null){
//					show("error");
//					finish();
//					Intent intent = new Intent(BuynowActivity.this, LoginActivity.class);
//					startActivity(intent);
//				}
//				else{
//					show(Get_uid);
//					buy();
//				}
//			}
//		});
		bt_buy_ok = (Button) findViewById(R.id.bt_buy_ok);
		bt_buy_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(Get_uid==null){
					show("请先登陆");
					finish();
					Intent intent = new Intent(BuynowActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				else{
					show("购买"+Get_uid);
					buy();
				}
			}
		});


	}
	private void buy(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
//					uid = (EditText)findViewById(R.id.ed_id);
//					upassword =(EditText)findViewById(R.id.ed_password);
//					final String email = uid.getText().toString();
//					final String pass = upassword.getText().toString();
					OkHttpClient client = new OkHttpClient();
					RequestBody requestBody = new FormBody.Builder()
							.add("uid", Get_uid)
							.add("pid", Get_id)
							//.add("buysum",bean_dingDan.Get_sum())
							.build();
					Request request = new Request.Builder()
							.url(web)
							.post(requestBody)
							.build();
					Response response = client.newCall(request).execute();
					show(web + "、" + Get_id + "、" + Get_uid);
		//			final String data = response.body().string();
		//			final String result = data;
		//			if(data.trim().equals("1")){
		//				runOnUiThread(new Runnable() {
		//					@Override
		//					public void run() {
		//						Toast.makeText(getApplicationContext(),"购买成功",Toast.LENGTH_SHORT).show();
//								Intent intent =  new Intent(getApplicationContext(), Home_F.class);
//								//startActivity(intent);
//								Bundle bundle = new Bundle();
//								bundle.putString("DingDanHao", DingDanHao);
//								intent.putExtras(bundle);
//								setResult(1,intent);
		//						finish();
		//					}
		//				});
		//			}
		//			else {
		//				show("购买失败，无法存入"+data.trim());

		//			}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}


//	private  void jsonObject(){
//		_tvResult = (TextView)findViewById(R.id.tv_Result);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					// 声明客户端
//					OkHttpClient client = new OkHttpClient();
//
//					// 声明请求
//					Request request = new Request.Builder()
//							.url(web)
//							.build();
//					// 客户端发起请求
//					Response response = client.newCall(request).execute();
//					// 返回值
//					final String data = response.body().string();
//					// 解析ｊｓｏｎ
//					final String result = parseJsonObject(data);
//					// 显示
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							System.out.println("111"+data);
//							System.out.println("222"+result);
//
//							show(result);
//							_tvResult.setText(result);
//						}
//					});
//				}
//				catch (Exception ex){
//					ex.printStackTrace();
//				}
//			}
//		}).start();
//	}
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

	private void show(final String r){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), r,
						Toast.LENGTH_SHORT).show();
			}
		});
	}



}
