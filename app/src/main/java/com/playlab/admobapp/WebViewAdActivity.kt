package com.playlab.admobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.playlab.admobapp.databinding.ActivityWebViewAdBinding

class WebViewAdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewAdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.javaScriptEnabled = true

        MobileAds.registerWebView(binding.webView)

        binding.webView.loadUrl("https://webview-api-for-ads-test.glitch.me/")
    }
}