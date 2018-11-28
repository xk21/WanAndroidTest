package com.cmy.wanandroidtest.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cmy.wanandroidtest.R;
import com.cmy.wanandroidtest.base.BaseActivity;
import com.cmy.wanandroidtest.base.BasePresenter;
import com.cmy.wanandroidtest.constant.Constant;
import com.cmy.wanandroidtest.interfaces.IArticleView;
import com.cmy.wanandroidtest.prsenter.ArticleDetailPresenter;
import com.cmy.wanandroidtest.utils.LogUtil;
import com.cmy.wanandroidtest.utils.WebViewProgressBar;

public class ArticleDetailsActivity extends BaseActivity <IArticleView,ArticleDetailPresenter>
        implements IArticleView{

    private ArticleDetailPresenter mPresenter;
    private String title;
    private String articleLink;
    private int articleId;
    private boolean isCollect;
    private FrameLayout mWebContent;
    private WebView mWebView;
    private WebViewProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void initData() {
        mWebView = new WebView(this);
//        mWebView.addJavascriptInterface(new MXCallBackForJs(new JsHandler(this)), Constants.MX_JS_OBJECT);

        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebContent.addView(mWebView);
        mProgressBar = new WebViewProgressBar(this);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelOffset(R.dimen.dp_2)));
        mWebContent.addView(mProgressBar);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // 获取地理位置权限
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationDatabasePath(getFilesDir().getPath());

        // 设置页面自适应webview
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 从Lollipop(21)开始WebView默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.start();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });
        mWebView.loadUrl(articleLink);

//        if (title != null && articleLink != null) {
//            mAgentWeb = AgentWeb.with(this)
//                    .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
//                    .useDefaultIndicator()
//                    .createAgentWeb()
//                    .ready()
//                    .go(articleLink);
//            WebView mWebView = mAgentWeb.getWebCreator().getWebView();
//            WebSettings mSettings = mWebView.getSettings();
//            mSettings.setJavaScriptEnabled(true);
//            mSettings.setSupportZoom(true);
//            mSettings.setBuiltInZoomControls(true);
//            //不显示缩放按钮
//            mSettings.setDisplayZoomControls(false);
//            //设置自适应屏幕，两者合用
//            //将图片调整到适合WebView的大小
//            mSettings.setUseWideViewPort(true);
//            //缩放至屏幕的大小
//            mSettings.setLoadWithOverviewMode(true);
//            //自适应屏幕
//            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        }
    }

    @Override
    protected void initView() {
        getBundleData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.article_toolbar);
        mWebContent = findViewById(R.id.article_detail_web_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationOnClickListener(v->onBackPressedSupport());
    }

    private void onBackPressedSupport() {
        finish();
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString(Constant.ARTICLE_TITLE);
        articleLink = bundle.getString(Constant.ARTICLE_LINK);
        articleId = bundle.getInt(Constant.ARTICLE_ID, Constant.REQUEST_ERROR);
        isCollect = bundle.getBoolean(Constant.ARTICLE_IS_COLLECT);
    }

    @Override
    protected ArticleDetailPresenter createPresenter() {
        if (null==mPresenter){
            mPresenter = new ArticleDetailPresenter();
        }
        return mPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtil.getInstance().d("szjjyh item.getItemId()="+item.getItemId());
        switch (item.getItemId()) {
            case R.id.menu_article_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "来自WanAndroid 【" + title + "】" + articleLink);
                startActivity(Intent.createChooser(shareIntent, "分享"));
                break;
            case R.id.menu_article_collect:
//                if ((Boolean) SharedPreferenceUtil.get(context, Constant.ISLOGIN, Constant.FALSE)) {
//                    if (isCollect) {
//                        mPresenter.cancelCollectArticle(articleId);
//                    } else {
//                        mPresenter.collectArticle(articleId);
//                    }
//                } else {
//                    ToastUtil.show(activity, getString(R.string.please_login));
//                    JumpUtil.overlay(activity, LoginActivity.class);
//                }
                break;
            case R.id.menu_article_browser:
                Uri uri = Uri.parse(articleLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void collectArticleOK(String info) {

    }

    @Override
    public void collectArticleErr(String info) {

    }

    @Override
    public void cancelCollectArticleOK(String info) {

    }

    @Override
    public void cancelCollectArticleErr(String info) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            mWebView.loadUrl(articleLink);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return false;
    }
}
