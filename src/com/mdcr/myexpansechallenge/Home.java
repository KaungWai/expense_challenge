package com.mdcr.myexpansechallenge;

import com.mdcr.expensechallenge.R;
import com.mdcr.wif.WebAppInterface;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Home extends ActionBarActivity {
	private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebChromeClient(new WebChromeClient());
        WebSettings myWebSettins = myWebView.getSettings();
        myWebSettins.setJavaScriptEnabled(true);
        myWebSettins.setDomStorageEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this,this.getContentResolver()), "Android");
        
        myWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
    	new AlertDialog.Builder(this)
    	.setTitle("Closing Application")
    	.setMessage("Are you sure want to exit?")
    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		})
		.setNegativeButton("No", null)
		.show();
    }
}