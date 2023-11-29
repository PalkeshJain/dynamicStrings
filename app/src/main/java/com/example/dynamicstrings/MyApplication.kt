package com.example.dynamicstrings

import android.app.Application

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.URL
import java.util.concurrent.CountDownLatch

class MyApplication : Application() {
    private val latch = CountDownLatch(1)
    //    override fun onCreate() {
//        super.onCreate()
//
//        val xmlFileCreator = XmlFileCreator(this)
//        xmlFileCreator.createXmlFile()
//    }
    override fun onCreate() {
        super.onCreate()

        // Perform tasks on app startup
        GlobalScope.launch(Dispatchers.IO) {
            // Make API call and save the XML response to a file
            fetchDataFromApiAndSaveToFile(applicationContext)
            // Notify that the tasks are complete
            latch.countDown()
        }
        runBlocking {
            latch.await()
        }
    }
    private suspend fun fetchDataFromApiAndSaveToFile(context: Context) {
        try {
            // Make API call
            val apiUrl =
                "https://api.phraseapp.com/api/v2/projects/ccd3be71be517febf75c4ce21aa4e6da/locales/3e5737beb6ba71c8cc4fcc27e7891a5a/download?file_format=xml&access_token=159dbaa5015e05aa456962bf5a3230920bbac1c3575744f84293f4ee9e152f6b"
            val apiResult = fetchDataFromApi(apiUrl)

            // Save result to XML file
            saveToXmlFile(apiResult, context)

            // Example: Reading data from the XML file
            val savedData = readFromXmlFile(context)
            Log.d("MyApp", "Data read from XML file: $savedData")
        } catch (e: Exception) {
            Log.e("MyApp", "Error fetching data from API: ${e.message}")
        }
    }

    private suspend fun fetchDataFromApi(apiUrl: String): String {
        return withContext(Dispatchers.IO) {
            try {
                URL(apiUrl).readText()
            } catch (e: IOException) {
                throw Exception("Error fetching data from API: ${e.message}")
            }
        }
    }

    private fun saveToXmlFile(data: String, context: Context) {
        try {
            val file = File(context.filesDir, "dynamic_strings.xml")
            val outputStream = FileOutputStream(file)
            val writer = OutputStreamWriter(outputStream)
            writer.write(data)
            writer.close()
            outputStream.close()
            Log.d("MyApp", "Data saved to XML file")
        } catch (e: IOException) {
            Log.e("MyApp", "Error saving data to XML file: ${e.message}")
        }
    }

    private fun readFromXmlFile(context: Context): String {
        return try {
            val file = File(context.filesDir, "dynamic_strings.xml")
            file.readText()
        } catch (e: IOException) {
            Log.e("MyApp", "Error reading data from XML file: ${e.message}")
            ""
        }
    }

}
