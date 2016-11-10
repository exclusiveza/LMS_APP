package com.example.fourt.lms_app;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.DownloadListener;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.util.Log;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    String user;
    String pass;
    String title;
    int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inbound data from login.java class
        Intent inbound = getIntent();
        user = inbound.getStringExtra("userr");
        pass = inbound.getStringExtra("passs");

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        // Settings
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSaveFormData(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultFontSize(36);
        webSettings.setAllowFileAccess(true);
        // Load URL
        mWebView.loadUrl("http://lms.psu.ac.th");

        // Download Manager
        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimetype);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();

            }
        });

        // Webview
        mWebView.setWebViewClient(new WebViewClient() {

            //Hiding Page when started
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebView.setVisibility(View.GONE);
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (i == 0) {
                    view.loadUrl("javascript:(function() { document.getElementById('login_username').value = '" + user + "'; " +
                            " document.getElementById('login_password').value = '" + pass + "';})()");
                    view.loadUrl("javascript:(function() { document.getElementById('login').submit(); })()");
                }
                //Display title
                title = view.getTitle();
                Log.v("Title is", title);
                //Javascript Inject , By checking website title.
                if (title.equals("LMS@PSU E-Learning Management System")) {
                    view.loadUrl("javascript:(function() { " +
                            "document.getElementById('middle-column').style.display='none';})()");
                }
                if (url.startsWith("http://lms.psu.ac.th/course/view.php?id") || url.startsWith("https://lms.psu.ac.th/course/view.php?id")) {
                    view.loadUrl("javascript:(function() { " +
                            "document.getElementById('left-column').style.display='none';})()");
                }
                // Javascript Inject
                view.loadUrl("javascript:(function() { " +
                        "document.getElementById('right-column').style.display='none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementById('footer').style.display='none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementById('inst1').style.display='none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('generalbox sitetopic box')[0].style.display = 'none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('block_participants sideblock')[0].style.display = 'none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('block_activity_modules sideblock')[0].style.display = 'none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('block_search_forums sideblock')[0].style.display = 'none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('block_admin sideblock')[0].style.display = 'none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('navbar clearfix')[0].style.display = 'none';})()");
                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('qmmc')[0].style.display = 'none';})()");
                i++;
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);

            }
        });
    }

    // On back button pressed
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}


