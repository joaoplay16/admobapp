package com.playlab.admobapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.playlab.admobapp.databinding.ActivityInterstitialRewardAdBinding


class InterstitialRewardAdActivity : AppCompatActivity(), OnUserEarnedRewardListener  {
    private lateinit var binding: ActivityInterstitialRewardAdBinding
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    private var TAG = "MYADMOB"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterstitialRewardAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) { initializationStatus ->
            loadAd()
        }

        binding.button.setOnClickListener {
            rewardedInterstitialAd?.show(this, this)
        }
    }

    /*
    * O intersticial premiado é um tipo de formato de anúncio incentivado
    * que permite oferecer recompensas por anúncios exibidos automaticamente
    * durante as transições naturais do aplicativo. Ao contrário dos anúncios
    * premiados, os usuários não precisam ativar a visualização de um intersticial
    *  premiado.
    */

    private fun loadAd() {
        RewardedInterstitialAd.load(this, "ca-app-pub-3940256099942544/5354046379",
            AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedInterstitialAd = ad
                    rewardedInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                        override fun onAdClicked() {
                            // Called when a click is recorded for an ad.
                            Log.d(TAG, "Ad was clicked.")
                        }

                        override fun onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            Log.d(TAG, "Ad dismissed fullscreen content.")
                            rewardedInterstitialAd = null
                            loadAd()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            // Called when ad fails to show.
                            Log.e(TAG, "Ad failed to show fullscreen content.")
                            rewardedInterstitialAd = null
                        }

                        override fun onAdImpression() {
                            // Called when an impression is recorded for an ad.
                            Log.d(TAG, "Ad recorded an impression.")
                        }

                        override fun onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                            Log.d(TAG, "Ad showed fullscreen content.")
                        }
                    }
//                    rewardedInterstitialAd?.show(this@InterstitialRewardAdActivity, this@InterstitialRewardAdActivity)
                    Log.d(TAG, "Ad was loaded.")

                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    rewardedInterstitialAd = null
                }
            })
    }

    override fun onUserEarnedReward(rewardItem: RewardItem) {
        Log.d(TAG, "User has rewarded ${rewardItem.amount} ${rewardItem.type}")
    }
}