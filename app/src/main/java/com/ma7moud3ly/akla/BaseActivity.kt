package com.ma7moud3ly.akla

import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun back(view: View) {
        finish()
    }
}