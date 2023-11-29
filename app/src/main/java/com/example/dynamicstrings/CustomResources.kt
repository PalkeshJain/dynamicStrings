package com.example.dynamicstrings

import android.content.Context
import android.content.res.Resources
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.FileInputStream
import kotlin.math.log

class CustomResources( private val context: Context, base: Resources) :
    Resources(base.assets, base.displayMetrics, base.configuration) {

    override fun getString(id: Int): String {
        return try {
            val key = getResourceName(id)
            val internalStorageString = getInternalStorageString(context, key)
            internalStorageString.orEmpty()
        } catch (e: Resources.NotFoundException) {
            super.getString(id)
        }
    }

    private fun getInternalStorageString(context: Context, key: String): String? {
        val key = key.substring(key.lastIndexOf('/') + 1)
        return getValueForKey(key) ?: throw NotFoundException()
    }

    private fun getValueForKey(key: String): String? {
        val file = File(context.filesDir, "dynamic_strings.xml")
        try {
            FileInputStream(file).use { inputStream ->
                val factory = XmlPullParserFactory.newInstance()
                val parser = factory.newPullParser()
                parser.setInput(inputStream, null)

                var eventType = parser.eventType
                var currentKey: String? = null
                var value: String? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            currentKey = parser.getAttributeValue(null, "name")
                        }
                        XmlPullParser.TEXT -> {
                            if (currentKey == key) {
                                value = parser.text
                                break
                            }
                        }
                    }
                    eventType = parser.next()
                }
                Log.d("Palkesh", "String retuned is :"+value)
                return value
            }
        } catch (e: Exception) {
            Log.e("XmlFileCreator", "Error reading XML file", e)
        }
        return null
    }
}
