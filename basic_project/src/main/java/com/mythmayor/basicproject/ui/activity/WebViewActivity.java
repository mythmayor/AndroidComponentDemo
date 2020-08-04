package com.mythmayor.basicproject.ui.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.base.BaseTitleBarActivity;

/**
 * Created by mythmayor on 2020/6/30.
 * WebView页面
 * <p>
 * android与js交互
 * 1.js调用安卓方法
 * (1)js：window.androidObject.androidMethod()
 *    安卓：调用webView.addJavascriptInterface，复写androidMethod方法，加上@JavascriptInterface
 * (2)安卓通过shouldOverrideUrlLoading方法拦截url再解析
 * 2.安卓调用js方法
 * (1)js：定义jsMethod方法
 *   安卓：调用webView.loadUrl("javascript:jsMethod(" + jsonParams + ")");
 * (2)js：定义jsMethod方法
 *   安卓：String method = "jsMethod(" + jsonParams + ")";
 *    调用webView.evaluateJavascript(method, new ValueCallback<String>() {
 *      @Override
 *       public void onReceiveValue(String value) {
 *           Log.i("test", "js返回的数据" + value);
 *       }
 *    });
 */
@Route(path = "/basicproject/WebViewActivity")
public class WebViewActivity extends BaseTitleBarActivity {

    private WebView mWebView;
    private String mTitle;
    private String mUrl;
    private ProgressBar mProgressBar;

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initSubView(View view) {
        mWebView = (WebView) view.findViewById(R.id.webView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void initSubEvent() {
    }

    @Override
    public void initSubData(Intent intent) {
        mTitle = intent.getStringExtra(MyConstant.INTENT_EXTRA_TITLE);
        mUrl = intent.getStringExtra(MyConstant.INTENT_EXTRA_URL);
        setTopTitle(true, mTitle);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }

            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void setTitleBar() {
        setLeftImage(true, R.mipmap.arrow_left);
    }
}
