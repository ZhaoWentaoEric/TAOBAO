package com.jarvis.mytaobao.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jarvis.http.CU_JSONResolve;
import com.jarvis.http.GetHttp;
import com.jarvis.mytaobaotest.R;
import com.javis.Adapter.Adapter_ListView_ware;
import com.javis.Bean.Bean_ListView_ware;
import com.javis.Bean.Bean_ListView_ware_1;
import com.lesogo.cu.custom.xListview.XListView;
import com.lesogo.cu.custom.xListview.XListView.IXListViewListener;
import com.zdp.aseo.content.AseoZdpAseo;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 多个商品展示界面
 *
 */
@SuppressLint("SimpleDateFormat")
public class WareActivity extends Activity implements OnTouchListener, IXListViewListener {
	//显示所有商品的列表
	private XListView listView;
	//整个顶部搜索控件，用于隐藏和显示底部整个控件
	private LinearLayout ll_search;
	//返回按钮
	private ImageView iv_back;
	@SuppressWarnings("unused")
	private EditText ed_search;
	private Button btn_msg;
	String test="";
	String test2="";
	private AnimationSet animationSet;
	/**第一次按下屏幕时的Y坐标*/
	float fist_down_Y = 0;
	/**请求数据的页数*/
	private int pageIndex = 0;
	/**存储网络返回的数据*/
	//private HashMap<String, Object> hashMap;
	private HashMap<String, Bean_ListView_ware> hashMap;
	//private HashMap<String, Bean_ListView_ware_1> hashMap;
	/**存储网络返回的数据中的data字段*/
	//private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
	private ArrayList<Bean_ListView_ware> arrayList = new ArrayList<Bean_ListView_ware>();
	//private ArrayList<HashMap<String, Bean_ListView_ware_1>> arrayList = new ArrayList<HashMap<String, Bean_ListView_ware_1>>();
	//private ArrayList<Bean_ListView_ware_1> arrayList = new ArrayList<Bean_ListView_ware_1>();
	public static final String websearchall="http://60.186.66.124/zwt_test/test/babyDetail.jsp";
	String imfor =new String();
	private  ArrayList<Bean_ListView_ware> beanlist = new ArrayList<Bean_ListView_ware>();
	String uid =null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ware_a);
		initView();
		//请求网络数据
		new WareTask().execute();

	}

	private void initView() {
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		ll_search = (LinearLayout) findViewById(R.id.ll_choice);
		ed_search = (EditText) findViewById(R.id.ed_Searchware);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btn_msg = (Button)findViewById(R.id.btn_msg);
		btn_msg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//jsonObject();
			//	show(test2);

			}
		});

		listView = (XListView) findViewById(R.id.listView_ware);
		listView.setOnTouchListener(this);
		listView.setXListViewListener(this);
		// 设置可以进行下拉加载的功能
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//第一次按下时的坐标
				fist_down_Y = y;
				break;
			case MotionEvent.ACTION_MOVE:
				// 向上滑动，隐藏搜索栏
				if (fist_down_Y - y > 250 && ll_search.isShown()) {
					if (animationSet != null) {
						animationSet = null;
					}
					animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
					ll_search.startAnimation(animationSet);
					ll_search.setY(-100);
					ll_search.setVisibility(View.GONE);
				}
				// 向下滑动，显示搜索栏
				if (y - fist_down_Y > 250 && !ll_search.isShown()) {
					if (animationSet != null) {
						animationSet = null;
					}
					animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
					ll_search.startAnimation(animationSet);
					ll_search.setY(0);
					ll_search.setVisibility(View.VISIBLE);
				}
				break;

		}
		return false;

	}


	private void show2(final String r){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), r,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

//向下滑动刷新虽然没啥软用
//	private class WareTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

	private class WareTask extends AsyncTask<Void, Void, HashMap<String, Bean_ListView_ware>> {
		ProgressDialog dialog=null;

		@Override
		protected void onPreExecute() {
			if (dialog==null) {
				dialog=ProgressDialog.show(WareActivity.this, "","正在加载...");
				dialog.show();
			}


		}

		@Override
	//	protected HashMap<String, Object> doInBackground(Void... arg0) {

		protected HashMap<String, Bean_ListView_ware> doInBackground(Void... arg0) {
			String url = "";
//			if (pageIndex == 0) {
				url = "http://192.168.43.251/zwt_test/test/babyDetail.jsp";
//			} else {
//				url = "http://192.168.0.111:3000/taoBaoQuery?pageIndex=" + pageIndex;
//			}
			//请求数据，返回json
			String json = GetHttp.RequstGetHttp(url);

			//show(json);
			//第一层的数组类型字段
			String[] LIST1_field = { "data" };
			//		private int ps_id;		//商品编号
//		                private int pd_fk_id;	//品牌生产线
//		                private int pd_num;		//库存
//		                float pd_price;	//价格
//						private String pd_name;	//商品名称
//						private String pd_properties;	//商品属性
			String[] aaa ={"123345"};
			//第二层的对象类型字段
			//String[] STR2_field = { "ps_id", "pd_fk_id", "pd_num", "pd_price", "pd_name", "pd_properties" };
			String[] STR2_field = { "id", "name", "price", "sale_num", "address", "pic" };
			ArrayList<String[]> aL_STR2_field = new ArrayList<String[]>();
			//第二层的对象类型字段放入第一层的数组类型字段中
			aL_STR2_field.add(STR2_field);


			//解析返回的json
			//hashMap = CU_JSONResolve.parseHashMap2(json, null, LIST1_field, aL_STR2_field);
			hashMap = CU_JSONResolve.parseHashMap2_2(json, null, LIST1_field, aL_STR2_field);
			//hashMap = CU_JSONResolve.parseHashMap2_3(json, null, LIST1_field, aL_STR2_field);
			//hashMap = CU_JSONResolve.parseHashMap1(json, STR2_field);


//			System.out.println("www"+i);
			//show(""+hashMap);
			test= "------LIST1_field="+ Arrays.toString(LIST1_field)+"-------STR2_field=="+Arrays.toString(STR2_field);
			return hashMap;
		}

		@SuppressWarnings("unchecked")
		@Override
//		protected void onPostExecute(HashMap<String, Object> result) {

		protected void onPostExecute(HashMap<String, Bean_ListView_ware> result) {

			if (dialog!=null&&dialog.isShowing()) {
				dialog.dismiss();
				dialog=null;
			}

			test2=result.toString();
			//如果网络数据请求失败，那么显示默认的数据
			if (result != null && result.values() != null) {
				//得到data字段的数据
				System.out.println("ddddd" + result.get("1").toString());
				//arrayList.addAll((Collection<? extends HashMap<String, Object>>) result.get("id"));
				//arrayList.addAll((Collection<? extends HashMap<String, Bean_ListView_ware_1>>) result.get("528.5"));
				Collection<Bean_ListView_ware> values = result.values();
				Iterator<Bean_ListView_ware> iterator = values.iterator();
				while (iterator.hasNext()) {
					Bean_ListView_ware bean1 = iterator.next();
					arrayList.add(bean1);
					System.out.println(bean1.toString());
				}
				System.out.println("ddddd1");
				listView.setAdapter(new Adapter_ListView_ware(WareActivity.this, arrayList));

				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						Bean_ListView_ware info = arrayList.get(position);
						Intent intent2 =getIntent();
						uid = intent2.getStringExtra("uid");
						//show(info+" / "+info.getId());

						Intent intent = new Intent(WareActivity.this, BabyActivity.class);
						intent.putExtra("uid",uid);
						intent.putExtra("id",info.getId());
						intent.putExtra("adress",info.getAddress());
						intent.putExtra("name",info.getName());
						intent.putExtra("price",info.getPrice());
						intent.putExtra("getSale_num",info.getSale_num());
						intent.putExtra("pic", info.getPic());
						startActivity(intent);
					}
				});

			}else {
				listView.setAdapter(new Adapter_ListView_ware(WareActivity.this,arrayList));
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						Intent intent = new Intent(WareActivity.this, BabyActivity.class);
						startActivity(intent);
					}
				});



			}

			// 停止刷新和加载
			onLoad();

		}

	}

	/** 刷新 */
	@Override
	public void onRefresh() {
		// 刷新数据
		pageIndex = 0;
		arrayList.clear();
		new WareTask().execute();
		// 停止刷新和加载
		onLoad();

	}

	/** 加载更多 */
	@Override
	public void onLoadMore() {
		pageIndex += 1;
		if (pageIndex >= 4) {
			Toast.makeText(this, "已经最后一页了", Toast.LENGTH_SHORT).show();
			onLoad();
			return;
		}
		new WareTask().execute();

	}

	/** 停止加载和刷新 */
	private void onLoad() {
		listView.stopRefresh();
		// 停止加载更多
		listView.stopLoadMore();

		// 设置最后一次刷新时间
		listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
	}

	/** 简单的时间格式 */
	public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	public static String getCurrentTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));

	}

	private  void jsonObject(){
		//_tvResult = (TextView)findViewById(R.id.tv_Result);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 声明客户端
					OkHttpClient client = new OkHttpClient();

					// 声明请求
					Request request = new Request.Builder()
							.url(websearchall)
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

							//show(result);
							//imfor =result;
							//_tvResult.setText(result);//子线程不能使用
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

				int ps_id = object.getInt("ps_id");
				int pd_fk_id =object.getInt("pd_fk_id");
				int pd_num_id =object.getInt("pd_num");
				String pd_price = object.getString("pd_price");
				String pd_name = object.getString("pd_name");
				String pd_properties = object.getString("pd_properties");


				builder.append(ps_id).append("-")
						.append(pd_fk_id).append("-")
						  .append(pd_num_id).append("-")
							.append(pd_price).append("-")
							  .append(pd_name).append("-")
								.append(pd_properties).append("\n");
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return builder.toString();
	}
//	private void show(final String r){
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				Toast.makeText(getApplicationContext(), r,
//						Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
//	public void parseJSONWithGSON(String jsonDate) {
//		beanlist.clear();
//		Gson gson = new Gson();
//
//		ArrayList<Bean_ListView_ware> lieboapList = gson.fromJson(jsonDate, new TypeToken<List<Bean_ListView_ware>>(){
//		}.getType());
//		for (Bean_ListView_ware info : lieboapList) {
//			beanlist.add(info);
//		}
//		.setText("璇勮 " + bean.size());
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				adapter.notifyDataSetChanged();
//			}
//		});


}
