package com.example

import android.content.Intent
import android.content.res.Resources.Theme
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myletcode.databinding.ActivitySplashBinding

/**
 * 启动页，避免白屏闪屏
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    //不知道为啥，这个一个参数的才能正常欸
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)
        Thread.sleep(2000)

    }

    //开始使用的是这个 一直出问题，找了好久，要吐了
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }


    /**
     * 跳转到主页面
     */
    private fun jump2Main() {
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }

}