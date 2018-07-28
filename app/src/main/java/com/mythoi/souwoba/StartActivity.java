package com.mythoi.souwoba;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.tencent.smtt.sdk.QbSdk;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		SharedPreferences share=this.getSharedPreferences("themetype", 0);
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


		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start_activity);
		if(android.os.Build.VERSION.SDK_INT<21){
			Toast.makeText(this,"你的手机安卓版本太低，不兼容该软件",1).show();
		}
		new Thread()
		{
			public void run()
			{
				try
				{
					QbSdk.initX5Environment(StartActivity.this,null);//首次加载x5浏览器内核
					sleep(3000);
					Intent in=new Intent(StartActivity.this, MainActivity.class);
					startActivity(in);
					finish();
				}
				catch (InterruptedException e)
				{}
			}
		}.start();
	}

}
