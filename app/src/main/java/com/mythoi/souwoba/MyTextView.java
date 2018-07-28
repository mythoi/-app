package com.mythoi.souwoba;
import android.widget.TextView;
import android.content.Context;
import android.util.AttributeSet;
import android.content.SharedPreferences;

public class MyTextView extends TextView
{
	public MyTextView(Context context,AttributeSet attr){
		super(context,attr);
		SharedPreferences share=context.getSharedPreferences("themetype",0);
		switch(share.getInt("themetype",0))
		{
			case 0:
				setTextColor(0xffAB47BC);
				break;
			case 1:
				setTextColor(0xff66BB6A);
				break;
			case 2:
				setTextColor(0xffFF1744);
				break;
			case 3:
				setTextColor(0xffFF4081);
				break;
			case 4:
				setTextColor(0xff0097A7);
				break;
			case 5:
				setTextColor(0xff40C4FF);	
				break;
			case 6:
				setTextColor(0xffFF6E40);
				break;
			case 7:
				setTextColor(0xffBDBDBD);	
				break;
			case 8:
				//	this.setTheme(R.style.AppTheme);
				break;		

		}


	}
	
	
	
	
	
}
