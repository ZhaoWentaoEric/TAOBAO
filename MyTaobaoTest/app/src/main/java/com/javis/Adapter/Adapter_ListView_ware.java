package com.javis.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.jarvis.http.CU_VolleyTool;
import com.jarvis.mytaobaotest.R;
import com.javis.Bean.Bean_ListView_ware;
import com.javis.Bean.Bean_ListView_ware_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_ListView_ware extends BaseAdapter {
	private Context context;
	@SuppressWarnings("unused")
	private int[] data;
//	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
//	private ArrayList<HashMap<String, Bean_ListView_ware_1>> arrayList = new ArrayList<HashMap<String, Bean_ListView_ware_1>>();
	private ArrayList<Bean_ListView_ware> arrayList = new ArrayList<Bean_ListView_ware>();

//	public Adapter_ListView_ware(Context context, ArrayList<HashMap<String, Object>> arrayList) {
//
//		this.context = context;
//		this.arrayList = arrayList;
//	}

//	public Adapter_ListView_ware(Context context, ArrayList<HashMap<String, Bean_ListView_ware_1>> arrayList) {
//
//		this.context = context;
//		this.arrayList = arrayList;
//	}

	public Adapter_ListView_ware(Context context, ArrayList<Bean_ListView_ware> arrayList) {

		this.context = context;
		this.arrayList = arrayList;
	}

	public Adapter_ListView_ware(Context context) {
		this.context = context;

	}

	@Override
	public int getCount() {
		return (arrayList != null && arrayList.size() == 0) ? 30: arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View currentView, ViewGroup arg2) {
		HolderView holderView = null;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_ware, null);
			holderView.iv_pic = (ImageView) currentView.findViewById(R.id.iv_adapter_list_pic);
			//holderView.tv_name.setText(arrayList.get(position).get("name").toString());
			holderView.tv_name = (TextView) currentView.findViewById(R.id.name);
			holderView.tv_price = (TextView) currentView.findViewById(R.id.price);
			holderView.tv_sale_num = (TextView) currentView.findViewById(R.id.sale_num);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		if (arrayList.size() != 0) {

			ImageListener listener = ImageLoader.getImageListener(holderView.iv_pic, R.drawable.ic_launcher, R.drawable.ic_launcher);
//			CU_VolleyTool.getInstance(context).getmImageLoader().get("http://192.168.0.111:3000/taoBao/img/" + arrayList.get(position).get("pic"), listener);
	//		CU_VolleyTool.getInstance(context).getmImageLoader().get("http://192.168.0.111:3000/taoBao/img/" + arrayList.get(position).getPic(), listener);
			CU_VolleyTool.getInstance(context).getmImageLoader().get("" + arrayList.get(position).getPic(), listener);

//			holderView.tv_name.setText(arrayList.get(position).get("tv_name").toString());
//			holderView.tv_price.setText("￥" + arrayList.get(position).get("tv_price").toString() + "元");
//			holderView.tv_sale_num.setText("月销量:" + arrayList.get(position).get("tv_sale_num").toString() + "件     " + arrayList.get(position).get("address").toString());
			holderView.tv_name.setText(arrayList.get(position).getName());
			holderView.tv_price.setText("￥" + arrayList.get(position).getPrice() + "元");
			holderView.tv_sale_num.setText("月销量:" + arrayList.get(position).getSale_num() + "件     " + arrayList.get(position).getAddress());
		}
		return currentView;
	}

	public class HolderView {

		private ImageView iv_pic;
		private TextView tv_sale_num;
		private TextView tv_name;
		private TextView tv_price;

	}

}
