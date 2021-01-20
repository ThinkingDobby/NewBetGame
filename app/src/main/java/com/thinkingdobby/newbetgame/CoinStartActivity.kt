package com.thinkingdobby.newbetgame

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_coin_start.*
import kotlin.random.Random

class CoinStartActivity : AppCompatActivity(), SensorEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_start)

        val decorView = window.decorView
        var uiOption = window.decorView.systemUiVisibility
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) uiOption =
            uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) uiOption =
            uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) uiOption =
            uiOption or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        decorView.setSystemUiVisibility(uiOption)

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

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private val sensorManager1 by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()

        sensorManager1.registerListener(
            this,
            sensorManager1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private var lastUpdate = 0L
    private val SHAKE_THRESHOLD = 600
    private var last_x: Float = 0F
    private var last_y: Float = 0F
    private var last_z: Float = 0F

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val curTime = System.currentTimeMillis()
            if (tv_coinStart_spin.text == "굴리기!") {
                if ((curTime - lastUpdate) > 100) {
                    val diffTime = curTime - lastUpdate
                    lastUpdate = curTime

                    val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000
                    if (speed > SHAKE_THRESHOLD) {
                        iv_coinStart_coin.animate().rotationX(360f).setDuration(300).start()
                        val tmp = getImage(getRandomValue())
                        iv_coinStart_coin.setImageResource(tmp)
                        if (checkStatus(tmp)) {
                            tv_coinStart_result.setText("앞면!")
                        } else {
                            tv_coinStart_result.setText("뒷면!")
                        }

                        tv_coinStart_spin.setText("리셋")

                        tv_coinStart_spin.setOnClickListener {
                            val intent = getIntent()
                            intent.putExtra("status", checkStatus(tmp))
                            finish()
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                        }
                    }
                    last_x = x
                    last_y = y
                    last_z = z
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager1.unregisterListener(this)
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
