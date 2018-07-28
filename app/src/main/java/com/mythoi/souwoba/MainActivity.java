package com.mythoi.souwoba;
//aide模板
//aide中文网aidecn.cn
//author：mythoi
//QQ：515942586
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnClickListener
{

	@Override
	public void onClick(View p1)
	{
		if (!expanded)
		{
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
										 .getWindowToken(), InputMethodManager.SHOW_FORCED);
			fab.show();
			searchListView.setVisibility(View.VISIBLE);
			linear.setVisibility(View.GONE);
			text.setVisibility(View.VISIBLE);
            iv.setImageDrawable(searchToBar);
            searchToBar.start();
			// iv.animate().translationX(0f).setDuration(duration).setInterpolator(interp);
            text.animate().alpha(1f).setStartDelay(duration - 100).setDuration(100).setInterpolator(interp);
        }
		else
		{
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
										 .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			fab.hide();
			searchListView.setVisibility(View.GONE);
			linear.setVisibility(View.VISIBLE);
			text.setVisibility(View.GONE);
            iv.setImageDrawable(barToSearch);
            barToSearch.start();
			// iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
			text.setAlpha(0f);
        }
        expanded = !expanded;
	}





	ArrayList<Fragment> views;
	ArrayList<String> titles;
	ViewPager viewPage;
	TabLayout tabLayout;
	MyListView2 searchListView;
	LinearLayout linear;
	AlertDialog.Builder  alert;

	private AppCompatImageView iv;

	private AnimatedVectorDrawable searchToBar;

	private AnimatedVectorDrawable barToSearch;

	private Interpolator interp;

	private int duration;

	private float offset;

	private boolean expanded=false;

	private TextView text;

	private FloatingActionButton fab;

	private ProgressBar pro;

	private TextView tv;

	private long lastTime;

	private boolean isFirst;

	private DrawerLayout drawer;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{ 
		this.setTheme(R.style.AppTheme1);

		SharedPreferences share=MainActivity.this.getSharedPreferences("themetype", 0);
		switch (share.getInt("themetype", 0))
		{

			case 0:
				this.setTheme(R.style.AppTheme);		

				break;

			case 1:
				this.setTheme(R.style.AppTheme1);

				break;
			case 2:

				this.setTheme(R.style.AppTheme2);
				break;
			case 3:
				this.setTheme(R.style.AppTheme3);

				break;
			case 4:
				this.setTheme(R.style.AppTheme4);

				break;
			case 5:

				this.setTheme(R.style.AppTheme5);
				break;
			case 6:
				this.setTheme(R.style.AppTheme6);

				break;
			case 7:

				this.setTheme(R.style.AppTheme7);
				break;
			case 8:

				//	this.setTheme(R.style.AppTheme);
				break;		

		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pro = (ProgressBar) findViewById(R.id.list_buttom1ProgressBar);
		pro.setVisibility(View.GONE);
		tv = (TextView)findViewById(R.id.list_buttom1TextView);
		tv.setText("");
		iv = (AppCompatImageView) findViewById(R.id.app_bar_mainImageView);
		iv.setOnClickListener(this);
		text = (TextView) findViewById(R.id.text);
		linear = (LinearLayout) findViewById(R.id.content_mainLinearLayout);
		searchListView = (MyListView2) findViewById(R.id.searchMyListView);
		searchToBar = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_search_to_bar);
        barToSearch = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_to_search);

		alert = new AlertDialog.Builder(this);
		text.setVisibility(View.GONE);
		iv.setImageDrawable(barToSearch);
		barToSearch.start();
		iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
		text.setAlpha(0f);

		initEdit();
		interp = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        duration = getResources().getInteger(R.integer.duration_bar);
        // iv is sized to hold the search+bar so when only showing the search icon, translate the
        // whole view left by half the difference to keep it centered
        offset = -71f * (int) getResources().getDisplayMetrics().scaledDensity;
        iv.setTranslationX(offset);
		views = new ArrayList<Fragment>();
		titles = new ArrayList<String>();
		initFragmentView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.hide();  
		fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					search();
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
												 .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				}
			});

		initFloatin();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

	}

	private void initFloatin()
	{
		searchListView. setOnTouchListener(new OnTouchListener(){

				private float mTouch;

				@Override
				public boolean onTouch(View p1, MotionEvent p2)
				{
					switch (p2.getAction())
					{
						case MotionEvent.ACTION_DOWN:
							mTouch = p2.getY();
							break;
						case MotionEvent.ACTION_MOVE:
							if (p2.getY() < mTouch)
							{
								fab.	hide();
							}
							else
							{
								if (p2.getY() - mTouch > 100)
								{
									mTouch = p2.getY();
									fab.	show();
								}
							}
							break;
					}
					System.out.println(p2.getY());
					// TODO: Implement this method
					return false;
				}
			});
	}

	private void initFragmentView()
	{
		viewPage = (ViewPager) findViewById(R.id.content_mainandroid_support_v4_view_ViewPager);
		tabLayout = (TabLayout) findViewById(R.id.content_mainandroid_support_design_widget_TabLayout);
		Fragment1 view1=new Fragment1();
		Fragment2 view2=new Fragment2();
		Fragment3 view3=new Fragment3();
		Fragment4 view4=new Fragment4();
		Fragment5 view5=new Fragment5();
		Fragment6 view6=new Fragment6();
		Fragment7 view7=new Fragment7();
		Fragment8 view8=new Fragment8();
		Fragment9 view9=new Fragment9();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
		views.add(view6);
		views.add(view7);
		views.add(view8);
		views.add(view9);
		titles.add("最新");
		titles.add("视频");
		titles.add("文档");
		titles.add("音乐");
		titles.add("图片");
		titles.add("软件");
		titles.add("专辑");
		titles.add("其他");
		titles.add("达人");
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(3)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(4)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(5)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(6)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(7)));
		tabLayout.addTab(tabLayout.newTab().setText(titles.get(8)));
		viewPage.setOffscreenPageLimit(9);
		viewPage.setAdapter(new MyPageAdapter(getSupportFragmentManager(), views, titles));
		tabLayout.setupWithViewPager(viewPage);
	}

//	public void animate(View view)
//	{
//
//        
//    }
//
	public void initEdit()
	{
		text.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event)
				{
					if (keyCode == KeyEvent.KEYCODE_ENTER)
					{
// 先隐藏键盘
						((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
													 .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
						search();
					}
					return false;
				}
			});

	}

	private void search()
	{
		String searchContext = text.getText().toString().trim();
		if (TextUtils.isEmpty(searchContext))
		{
			Toast.makeText(MainActivity.this, "输入框为空，请输入", 0).show();
		}
		else
		{
			pro.setVisibility(View.VISIBLE);
			tv.setText("加载中...");
			searchListView.setVisibility(View.VISIBLE);
			linear.setVisibility(View.GONE);
			searchListView.setUrlStart("http://m.panduoduo.net/s/name/" + text.getText().toString() + "/");

		}
	}



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share)
		{
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
	{
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
		{
            drawer.closeDrawer(GravityCompat.START);
        }
		else if (id == R.id.nav_gallery)
		{

			alert.setTitle("选择主题");
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"基佬紫","酷安绿","姨妈红","哔哩粉","水鸭青","颐堤蓝","伊藤橙","星空灰"});
			alert.setAdapter(adapter, new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						SharedPreferences share=MainActivity.this.getSharedPreferences("themetype", 0);
						SharedPreferences.Editor editor = share.edit(); 
//用putString的方法保存数据 
						Toast.makeText(MainActivity.this, "切换成功，重启应用生效", 1).show();
						switch (p2)
						{
							case 0:
								editor.putInt("themetype", 0);
								break;
							case 1:
								editor.putInt("themetype", 1);
								break;
							case 2:
								editor.putInt("themetype", 2);	
								break;
							case 3:
								editor.putInt("themetype", 3);	
								break;
							case 4:
								editor.putInt("themetype", 4);	
								break;
							case 5:
								editor.putInt("themetype", 5);
								break;
							case 6:
								editor.putInt("themetype", 6);	
								break;
							case 7:
								editor.putInt("themetype", 7);	
								break;
							case 8:
								editor.putInt("themetype", 8);		
								break;
							case 9:
								editor.putInt("themetype", 9);	
								break;	
						}	
							editor.commit(); 	
						Intent in=new Intent(MainActivity.this,MainActivity.class);
						startActivity(in);
						finish();
					}
				});
			alert.show();
		}
		else if (id == R.id.nav_slideshow)
		{
			Toast.makeText(this, "下个版本见", 0).show();
        }
		else if (id == R.id.nav_manage)
		{
			Toast.makeText(this, "下个版本见", 0).show();
        }
		else if (id == R.id.nav_share)
		{
			shareApp();
		}
		else if (id == R.id.nav_send)
		{
			Intent in=new Intent(this, AboutActivity.class);
			startActivity(in);
        }


        return true;
    }

	private void shareApp()
	{
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);

		try
		{
			Uri imageUri = Uri.fromFile(new File(getPackageManager().getApplicationInfo("com.mythoi.souwoba", 0).sourceDir));
			shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
			shareIntent.setType("text/plain");
//设置分享列表的标题，并且每次都显示分享列表
			startActivity(Intent.createChooser(shareIntent, "分享到"));		
		}
		catch (Exception e)
		{}

	}


//	private long mExitTime;
//	@Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//	{
//		if(expanded) {
//			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//				.hideSoftInputFromWindow(MainAtivityy.this.getCurrentFocus()
//										 .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//			fab.hide();
//			searchListView.setVisibility(View.GONE);
//			linear.setVisibility(View.VISIBLE);
//			text.setVisibility(View.GONE);
//            iv.setImageDrawable(barToSearch);
//            barToSearch.start();
//			// iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
//			text.setAlpha(0f);
//        expanded = !expanded;
//		return true;
//		}
//		
//		if (keyCode == KeyEvent.KEYCODE_BACK)
//		{
//			if ((System.currentTimeMillis() - mExitTime) > 800)
//			{
//				Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
//				mExitTime = System.currentTimeMillis();
//			}
//			else
//			{
//				// 返回桌面操作
//				Intent home = new Intent(Intent.ACTION_MAIN);
//				home.addCategory(Intent.CATEGORY_HOME);
//				startActivity(home);
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//	
//	

	@Override
	public void onBackPressed()
	{


		if (drawer.isDrawerOpen(GravityCompat.START))
		{

            drawer.closeDrawer(GravityCompat.START);
        }
		else
		{

			if (expanded)
			{
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
											 .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				fab.hide();
				searchListView.setVisibility(View.GONE);
				linear.setVisibility(View.VISIBLE);
				text.setVisibility(View.GONE);
				iv.setImageDrawable(barToSearch);
				barToSearch.start();
				// iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
				text.setAlpha(0f);
				expanded = !expanded;
				return ;
			}

			if (isFirst)
			{ 
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show(); 
				lastTime = System.currentTimeMillis();
				isFirst = false; 
			}
			else 
			{
				if ((System.currentTimeMillis() - lastTime) < 2000)
				{ 
					this.finish();
				}
				else
				{
					Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show(); lastTime = System.currentTimeMillis(); 
				} 
			} 
		}
	}

}
