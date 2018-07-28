package com.mythoi.souwoba;
import android.support.v4.app.Fragment;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.os.Message;

public class Fragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			swipe.setRefreshing(false);
			// TODO: Implement this method
			super.handleMessage(msg);
		}
	
};
	@Override
	public void onRefresh()
	{
		listView.setUrlStart("http://m.panduoduo.net/c/4/",handler);
		
		// TODO: Implement this method
	}

	View view;
	MyListView listView;

	private SwipeRefreshLayout swipe;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment3,null);
		listView=(MyListView) view.findViewById(R.id.fragment3com_mythoi_souwoba_MyListView);
		swipe=(SwipeRefreshLayout) view.findViewById(R.id.fragment3android_support_v4_widget_SwipeRefreshLayout);
		swipe.setOnRefreshListener(this);
		swipe.setRefreshing(true);
		swipe.setColorSchemeResources(R.color.colorPrimary);
		
		listView.setUrlStart("http://m.panduoduo.net/c/4/",handler);
		return view;
	}

}
