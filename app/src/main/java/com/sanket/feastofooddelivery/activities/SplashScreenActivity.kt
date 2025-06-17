package com.sanket.feastofooddelivery.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sanket.feastofooddelivery.R


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }, 3000)



        val textView = findViewById<TextView>(R.id.zoomText)
        val zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out)
        textView.startAnimation(zoomAnimation)


    }
}