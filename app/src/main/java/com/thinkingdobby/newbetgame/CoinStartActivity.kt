package com.thinkingdobby.newbetgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_coin_start.*
import kotlin.random.Random

class CoinStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_start)

        tv_coinStart_result.setText("")

        val now = intent.getBooleanExtra("status", true)
        if (now) {
            iv_coinStart_coin.setImageResource(R.drawable.coin_front)
        } else {
            iv_coinStart_coin.setImageResource(R.drawable.coin_back)
        }


        tv_coinStart_spin.setOnClickListener {
            iv_coinStart_coin.animate().rotationX(360f).setDuration(300).start()
            val tmp = getImage(getRandomValue())
            iv_coinStart_coin.setImageResource(tmp)
            if (checkStatus(tmp)) {
                tv_coinStart_result.setText("앞면!")
            } else {
                tv_coinStart_result.setText("뒷면!")
            }

            //임시로 작성한 코드
            tv_coinStart_spin.setText("리셋")

            tv_coinStart_spin.setOnClickListener {
                val intent = getIntent()
                intent.putExtra("status", checkStatus(tmp))
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
        return if (value == 0) R.drawable.coin_front
        else R.drawable.coin_back
    }

    private fun getRandomValue(): Int {
        return Random.nextInt(2)
    }


    private fun checkStatus(status: Int): Boolean {
        return status == R.drawable.coin_front
    }
}
