package com.example.dynamicstrings

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val xmlFileCreator = XmlFileCreator(this)
        xmlFileCreator.createXmlFile()
    }
}
