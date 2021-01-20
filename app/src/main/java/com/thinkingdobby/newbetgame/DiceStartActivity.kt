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
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_coin_start.*
import kotlin.random.Random

class DiceStartActivity : AppCompatActivity(), SensorEventListener {

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