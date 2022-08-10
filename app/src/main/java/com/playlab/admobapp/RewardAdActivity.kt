package com.playlab.admobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.playlab.admobapp.databinding.ActivityRewardAdBinding

class RewardAdActivity : AppCompatActivity() {
    private val TAG = "MYADMOB"
    private lateinit var binding: ActivityRewardAdBinding
    private var mRewardedAd: RewardedAd? = null
    private var rewardAmount : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardAdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this)
        loadRewardedAd()

        binding.textView.text = getString(R.string.coins, 0)
        binding.button.setOnClickListener {
            showRewardedAd()
        }
    }

    //loading the ad before show
    //when the ad is closed the onResume is called and then the ad is loaded again
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

        loadRewardedAd()
    }

    private fun loadRewardedAd(){

        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mRewardedAd = rewardedAd
                }
            })
    }

    private fun showRewardedAd(){
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mRewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mRewardedAd = null
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

        if (mRewardedAd != null) {
            mRewardedAd?.show(this, object: OnUserEarnedRewardListener {

                override fun onUserEarnedReward(rewardItem: RewardItem) {
                    rewardAmount += rewardItem.amount
                    var rewardType = rewardItem.type
                    binding.textView.text = getString(R.string.coins, rewardAmount)
                    Log.d(TAG, "User earned the reward. amount $rewardAmount type $rewardType")
                }

            })
        } else {
            Log.d(TAG, "The reward ad wasn't ready yet.")
        }


    }
}