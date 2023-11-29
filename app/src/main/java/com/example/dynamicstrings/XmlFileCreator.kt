package com.example.dynamicstrings
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class XmlFileCreator(private val context: Context) {

    fun createXmlFile() {
        try {
            val xmlContent = """
                <resources>
                    <string name="app_name">Dynamic Strings</string>
                    <string name="dynamicText">This is Dynamic Text from internal Storage res</string>
                </resources>
            """.trimIndent()

            val fileName = "dynamic_strings.xml"
            val file = File(context.filesDir, fileName)

            Log.d("XmlFileCreator", "File path: ${file.absolutePath}")

            FileOutputStream(file).use { outputStream ->
                outputStream.write(xmlContent.toByteArray())
            }

            Log.d("XmlFileCreator", "File created successfully")
        } catch (e: Exception) {
            Log.e("XmlFileCreator", "Error creating XML file", e)
        }
    }
}
