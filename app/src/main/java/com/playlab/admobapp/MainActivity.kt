package com.playlab.admobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.playlab.admobapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAd(binding.adView)
    }

    private fun loadAd(adView: AdView){
        adView.loadAd(AdRequest.Builder().build())

        adView.adListener = object: AdListener(){
            val TAG = "MYADMOB"
            override fun onAdLoaded() {
                Log.d(TAG, "onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad")

            }

            override fun onAdOpened() {
                Log.d(TAG,"onAdOpened")

            }

            override fun onAdClicked() {
                Log.d(TAG, "onAdClicked")
            }

            override fun onAdClosed() {
                Log.d(TAG, "onAdClosed")
            }

            override fun onAdImpression() {
                Log.d(TAG, "onAdImpression")
            }
        }
    }
}