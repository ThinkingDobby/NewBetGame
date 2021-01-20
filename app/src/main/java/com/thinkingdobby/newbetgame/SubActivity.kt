package com.thinkingdobby.newbetgame

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_sub.*

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val decorView = window.decorView
        var uiOption = window.decorView.systemUiVisibility
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) uiOption =
            uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) uiOption =
            uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) uiOption =
            uiOption or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        decorView.setSystemUiVisibility(uiOption)

        iv_sub_coin.setOnClickListener {
            val intent = Intent(this, CoinStartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        tv_sub_coin.setOnClickListener {
            val intent = Intent(this, CoinStartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        iv_sub_dice.setOnClickListener {
            val intent = Intent(this, DiceStartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        tv_sub_dice.setOnClickListener {
            val intent = Intent(this, DiceStartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}
