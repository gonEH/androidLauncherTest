package com.goneh.androidlaunchertest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }
}
