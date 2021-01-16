package com.thinkingdobby.newbetgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sub.*

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        iv_sub_coin.setOnClickListener {
            val intent = Intent(this, CoinStartActivity::class.java)
            startActivity(intent)
        }

        tv_sub_coin.setOnClickListener {
            val intent = Intent(this, CoinStartActivity::class.java)
            startActivity(intent)
        }

        iv_sub_dice.setOnClickListener {
            val intent = Intent(this, DiceStartActivity::class.java)
            startActivity(intent)
        }

        tv_sub_dice.setOnClickListener {
            val intent = Intent(this, DiceStartActivity::class.java)
            startActivity(intent)
        }

    }
}
