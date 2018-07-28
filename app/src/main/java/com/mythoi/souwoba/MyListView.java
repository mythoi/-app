package com.mythoi.souwoba;
import android.widget.ListView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import java.io.IOException;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.nodes.Element;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class MyListView extends ListView  implements OnItemClickListener,OnScrollListener
{
	Handler handler;
	View viewBottom;
	int ye=1;
	String url,url2,url3;
	boolean isToBottom=true;
	Document doc,doc2;
	Elements ele;
	SimpleAdapter adapter;
	ProgressDialog progress;
	AlertDialog.Builder alert;
	Context context;
	
	ArrayList<HashMap<String,String>> itemData,itemData2;
	public MyListView(Context context,AttributeSet attr)
	{
		super(context,attr);
		this.context=context;
        viewBottom=LayoutInflater.from(context).inflate(R.layout.list_buttom1,null);
		itemData=new ArrayList<HashMap<String,String>>();
		itemData2=new ArrayList<HashMap<String,String>>();
		adapter=new SimpleAdapter(context,itemData,R.layout.list_item,new String[]{"title","time","url"},new int[]{R.id.list_itemTextViewTitle,R.id.list_itemTextViewTime,R.id.list_itemTextViewUrl});
		setAdapter(adapter);
		setOnScrollListener(this);
		setOnItemClickListener(this);
		addFooterView(viewBottom);
		progress=new ProgressDialog(context);
		alert=new AlertDialog.Builder(context);
		}
	
	
	public void setUrlStart(String url,Handler handler)
	{
		this.handler=handler;
		ye=1;
		itemData.clear();
		adapter.notifyDataSetChanged();
		this.url=url;
		new MyAsyncTask().execute();
	}
	
	@Override
	public void onScrollStateChanged(AbsListView p1, int p2)
	{
		// TODO: Implement this method
	}

	@Override
	public void onScroll(AbsListView p1, int p2, int p3, int p4)
	{
		if(p2+p3==p4&&p2!=0)
		{
			if(isToBottom)
			{
				ye++;
				new MyAsyncTask().execute();
			}
			viewBottom.setVisibility(View.VISIBLE);
		}else
		{
			viewBottom.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		
		TextView tv=(TextView) p2.findViewById(R.id.list_itemTextViewUrl);
	if(tv==null)return;
		url2=tv.getText().toString();
	System.out.println(url2);
	progress.setMessage("解析中...");
	progress.show();
	new MyAsyncTask2().execute();
		}

	class MyAsyncTask extends AsyncTask
	{

		@Override
		protected Object doInBackground(Object[] p1)
		{
			isToBottom=false;
			try
			{
				doc = Jsoup.connect(url + ye).get();
				ele=doc.select("div.content");
				for(Element data:ele)
				{
					HashMap<String,String> hashMap=new HashMap<String,String>();
				hashMap.put("title",data.select("h3").text());
				//hashMap.put("type","分类："+data.select("a.tag").text());
			    String str=data.select("div.list-content").text();
			     hashMap.put("time",str.replaceAll("&nbsp;&nbsp;","   "));
					hashMap.put("url","http://m.panduoduo.net"+data.select("a[title]").attr("href"));
					itemData2.add(hashMap);
					}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result)
		{
			handler.sendEmptyMessage(0);
			isToBottom=true;
			itemData.addAll(itemData2);
			itemData2.clear();
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		
	}
	
	class MyAsyncTask2 extends AsyncTask
	{

		private String url4;

		@Override
		protected Object doInBackground(Object[] p1)
		{
			try
			{
				doc2 = Jsoup.connect(url2).get();
				System.out.println(doc2);
				url3=doc2.select("a#redirect-link").attr("href");
				Document doc=Jsoup.connect(url3).get();
				 url4= doc.select("a").attr("href");
				Document doc3=Jsoup.connect(url4).get();
				if(doc3.toString().indexOf("链接不存在")!=-1)
				{
					url4=url4.replaceAll(url4.substring(url4.indexOf("shareid="),url4.indexOf("&uk")),"").replaceAll("link","home");
					publishProgress();
					}		
				}
			catch (Exception e)
			{
			System.out.println(e);
			} 
			return null;
		}

		@Override
		protected void onProgressUpdate(Object[] values)
		{
			Toast.makeText(context,"链接已失效，正在为你跳转到他的网盘",1).show();
			super.onProgressUpdate(values);
		}

		
		@Override
		protected void onPostExecute(Object result)
		{
			progress.dismiss();
			//alert.setTitle(url3);
			//alert.setMessage("gggg|");
			//alert.show();
			Intent in=new Intent(context,WebViewActivity.class);
			in.putExtra("url",url4);
			context.startActivity(in);
			super.onPostExecute(result);
		}
		
		
	}
}




