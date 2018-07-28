package com.mythoi.souwoba;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MyPageAdapter extends FragmentPagerAdapter
{



ArrayList<Fragment> views;
	ArrayList<String> titles;
	public MyPageAdapter(FragmentManager fm, ArrayList<Fragment> views,ArrayList<String> titles)
	{
		super(fm);
		this.views=views;
		this.titles=titles;
	}
	@Override
	public Fragment getItem(int p1)
	{
		// TODO: Implement this method
		return views.get(p1);
	}
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return views.size();
	}


	@Override
	public CharSequence getPageTitle(int position)
	{
		// TODO: Implement this method
		return titles.get(position);
	}
}
