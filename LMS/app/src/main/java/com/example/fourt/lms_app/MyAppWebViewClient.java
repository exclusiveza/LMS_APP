package com.example.fourt.lms_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.DownloadManager;
import android.content.ContextWrapper;

/**
 * Created by fourt on 9/9/2016.
 */


public class MyAppWebViewClient extends WebViewClient
{
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
/*        if(request.toString().startsWith("http:\\lms") || request.toString().startsWith("https:\\lms"))
            return false;
        else {*/
            view.loadUrl(request.toString());
            return true;
        }
    }



