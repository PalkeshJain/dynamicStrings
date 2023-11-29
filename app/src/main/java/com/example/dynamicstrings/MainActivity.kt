package com.example.dynamicstrings

import android.os.Bundle
import android.widget.TextView



class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        textView.text = getString(R.string.dynamicText)

        val staticTextView : TextView = findViewById(R.id.staticTextView)
        staticTextView.text = getString(R.string.staticText)
    }
}
