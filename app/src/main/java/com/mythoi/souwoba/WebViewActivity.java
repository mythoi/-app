package com.mythoi.souwoba;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.view.View;
import android.view.KeyEvent;
import com.tencent.smtt.sdk.DownloadListener;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ClipboardManager;
import java.io.File;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.io.IOException;
import android.os.Handler;

public class WebViewActivity extends AppCompatActivity
{
WebView myWebView;
	ProgressBar pg1;
	private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
	String url;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		SharedPreferences share=this.getSharedPreferences("themetype",0);
		switch(share.getInt("themetype",0))
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
	setContentView(R.layout.webview_activity);
	   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitleTextColor(0xffffffff);
	   setSupportActionBar(toolbar);
	url=getIntent().getStringExtra("url");
	myWebView=(WebView) findViewById(R.id.webview_activitycom_tencent_smtt_sdk_WebView);
		pg1=(ProgressBar) findViewById(R.id.webviewProgressBar);
	myWebView.getSettings().setDomStorageEnabled(true);//重要，搞了两个钟才没问题的
        //加上下面这段代码可以使网页中的链接不以浏览器的方式打开
        myWebView.setWebViewClient(new WebViewClient() {
			
			
			
			
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
                     myWebView.loadUrl(url);
					// 处理自定义scheme
					if (!url.startsWith("http")) {

						//	Toast.makeText(ContentActivity.this, "需要下载客户端收看", Toast.LENGTH_LONG).show();
						try {
							// 下固定写法
							final Intent intent = new Intent(Intent.ACTION_VIEW,
															 Uri.parse(url));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
											| Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(intent);
						} catch (Exception e) {
							// 防止没有安装的情况
							e.printStackTrace();
						}
						return true;
					}
					return false;
				}

				@Override
				public void onReceivedError(WebView view, int errorCode,
											String description, String failingUrl) {
					// Auto-generated method stub
					super.onReceivedError(view, errorCode, description, failingUrl);
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					// Auto-generated method stub

					super.onPageFinished(view, url);
				}

				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					// Auto-generated method stub
					super.onPageStarted(view, url, favicon);
				}

			});
        //得到webview设置
        WebSettings webSettings = myWebView.getSettings();
		webSettings.setPluginsEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setDefaultTextEncodingName("GBK");

        //允许使用javascript
        webSettings.setJavaScriptEnabled(true);
		//将WebAppInterface于javascript绑定
		myWebView.addJavascriptInterface(new WebAppInterface(WebViewActivity.this), "Android");

        //加载服务器上的页面
        myWebView.loadUrl(url);
		final Handler handler=new Handler();
		myWebView.setDownloadListener(new DownloadListener() {

	@Override
	public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
								long contentLength) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

    });
		//设置经度条
        myWebView.setWebChromeClient(new WebChromeClient(){

				public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
					Log.i("test", "openFileChooser 1");
					WebViewActivity.this.uploadFile = uploadFile;
					openFileChooseProcess();
				}

				// For Android < 3.0
				public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
					Log.i("test", "openFileChooser 2");
					WebViewActivity.this.uploadFile = uploadFile;
					openFileChooseProcess();
				}

				// For Android  > 4.1.1
				public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
					Log.i("test", "openFileChooser 3");
					WebViewActivity.this.uploadFile = uploadFile;
					openFileChooseProcess();
				}

				// For Android  >= 5.0
				public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
												 ValueCallback<Uri[]> filePathCallback,
												 WebChromeClient.FileChooserParams fileChooserParams) {
					Log.i("test", "openFileChooser 4:" + filePathCallback.toString());
					WebViewActivity.this.uploadFiles = filePathCallback;
					openFileChooseProcess();
					return true;
				}


				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					// TODO 自动生成的方法存根

					if(newProgress==100){
						pg1.setVisibility(View.GONE);//加载完网页进度条消失
						
					}
					else{
						pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
						pg1.setProgress(newProgress);//设置进度值
					}

				}
			});
	
	}
	private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
							: data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
							: data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
    }


	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	
	public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
	
	
	    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.openurl) {
            Intent intent_d= new Intent();        
			intent_d.setAction("android.intent.action.VIEW");    
			Uri content_url = Uri.parse(url);   
			intent_d.setData(content_url);  
			startActivity(intent_d);

        }else if(id==R.id.copyurl)
		{
			ClipboardManager cm =(ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
//将文本数据复制到剪贴板
			cm.setText(url);
			Toast.makeText(this,"已复制",0).show();
		}else if(id==R.id.share)
		{
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi，我正在使用《搜我吧》APP，本链接由《搜我吧》APP提供。给的再多，不如懂我。"+url);
			shareIntent.setType("text/plain");
//设置分享列表的标题，并且每次都显示分享列表
			startActivity(Intent.createChooser(shareIntent, "分享到"));	
		}

        return true;
    }
	
	
	
}
