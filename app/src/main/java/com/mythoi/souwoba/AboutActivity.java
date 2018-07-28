package com.mythoi.souwoba;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.text.Html;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class AboutActivity extends AppCompatActivity
{
	TextView tv1,tv2,tv3,tv4,tv5,tv6;

	private AlertDialog.Builder aler;
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
		setContentView(R.layout.about);
		aler=new AlertDialog.Builder(this);
		aler.setTitle("提示");
		aler.setMessage("如果你觉得这个APP还不错，可以捐赠我们支持我们的工作，感谢大家的支持");
		aler.setPositiveButton("支持", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
		//https://ds.alipay.com/?from=mobilecodec&scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FsaId%3D10000007%26clientVersion%3D3.7.0.0718%26qrcode%3Dhttps%253A%252F%252Fqr.alipay.com%252FFKX011243FFHRIALICYN2D%253F_s%253Dweb-other
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2FFKX011243FFHRIALICYN2D%3F_s%3Dweb-other")));
					}
			});
			aler.setNegativeButton("我就不",null);
			aler.show();
		tv1=(TextView) findViewById(R.id.aboutactivityTextView1);
		tv2=(TextView) findViewById(R.id.aboutactivityTextView2);
		tv3=(TextView) findViewById(R.id.aboutactivityTextView3);
		tv4=(TextView) findViewById(R.id.aboutactivityTextView4);
		tv5=(TextView) findViewById(R.id.aboutactivityTextView5);
		tv6=(TextView) findViewById(R.id.about_activityTextView);
		tv1.setText(Html.fromHtml("<u>粉丝群1：217034110（已满）</u>"));
		tv2.setText(Html.fromHtml("<u>粉丝群2：366985431（已满）</u>"));
		tv3.setText(Html.fromHtml("<u>粉丝群3：587450032</u>"));
		tv4.setText(Html.fromHtml("<u>开发者：515942586</u>"));
		tv5.setText(Html.fromHtml("<u>邮箱：oumythoi@gmail.com</u>"));
		tv6.setText(Html.fromHtml("<u>开发者 </u>"));
	}

	public void p1(View v)
	{
		joinQQGroup("J4ECWlEt9nj0bYrwP-_5TD1-xhSgUc2v");
	}
	public void p2(View v)
	{
		joinQQGroup("0gh4M5CqCrqu8oNqVmQq9Sc-Xqg-VYNU");

	}
	public void p3(View v)
	{
		joinQQGroup("B1wnW8iDbIF0wiYOtz3JnZBStAjX9pLW");	
	}
	public void p4(View v)
	{
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=515942586&version=1")));
	}
	public void p5(View v)
	{

		Intent data=new Intent(Intent.ACTION_SENDTO); 
		data.setData(Uri.parse("mailto:oumythoi@gmail.com"));
		data.putExtra(Intent.EXTRA_SUBJECT, "搜我吧"); 
		data.putExtra(Intent.EXTRA_TEXT, ""); 
		startActivity(data); 
	}

	public void p6(View v)
	{
		Intent intent_d= new Intent();        
		intent_d.setAction("android.intent.action.VIEW");    
		Uri content_url = Uri.parse("http://occhao.cc");   
		intent_d.setData(content_url);  
		startActivity(intent_d);

	}

	public boolean joinQQGroup(String key) {
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try {
			startActivity(intent);
			return true;
		} catch (Exception e) {
			// 未安装手Q或安装的版本不支持
			return false;
		}
	}

	
}
