package com.thinkingdobby.newbetgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_coin_start.*
import kotlin.random.Random

class DiceStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_start)

        tv_coinStart_result.setText("")

        val now = intent.getIntExtra("status", 0)
        iv_coinStart_coin.setImageResource(getImage(now))

        tv_coinStart_spin.setOnClickListener {
            val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
            iv_coinStart_coin.startAnimation(shake)
            val number = getRandomValue()
            val tmp = getImage(number)
            iv_coinStart_coin.setImageResource(tmp)
            tv_coinStart_result.setText("${number + 1}!")

            tv_coinStart_spin.setText("리셋")

            tv_coinStart_spin.setOnClickListener {
                val intent = getIntent()
                intent.putExtra("status", number)
                finish()
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }

        tv_coinStart_back.setOnClickListener {
            finish()
        }
    }

    private fun getImage(value: Int): Int {
        return when (value) {
            0 -> R.drawable.dice_1
            1 -> R.drawable.dice_2
            2 -> R.drawable.dice_3
            3 -> R.drawable.dice_4
            4 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun getRandomValue(): Int {
        return Random.nextInt(6)
    }
}