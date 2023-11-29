package com.example.dynamicstrings

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getResources(): Resources {
        return CustomResources( this , super.getResources())
    }
}