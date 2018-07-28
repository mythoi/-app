package com.mythoi.souwoba;
import android.support.v4.app.Fragment;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.os.Message;

public class Fragment9 extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			swipe.setRefreshing(false);
			super.handleMessage(msg);
		}

	};
	@Override
	public void onRefresh()
	{
		listView.setUrlStart("http://m.panduoduo.net/u/bd/",handler);	
	}

	View view;
	MyListViewDaRen listView;
	SwipeRefreshLayout swipe;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment9,null);
		listView=(MyListViewDaRen) view.findViewById(R.id.fragment9com_mythoi_souwoba_MyListView);
		swipe=(SwipeRefreshLayout) view.findViewById(R.id.fragment9android_support_v4_widget_SwipeRefreshLayout);
		swipe.setRefreshing(true);
		swipe.setColorSchemeResources(R.color.colorPrimary);
		swipe.setOnRefreshListener(this);
		listView.setUrlStart("http://m.panduoduo.net/u/bd/",handler);
		return view;
	}

}
