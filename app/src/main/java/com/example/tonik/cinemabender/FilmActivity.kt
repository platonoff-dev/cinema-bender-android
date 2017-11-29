package com.example.tonik.cinemabender

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html.fromHtml
import android.widget.ImageView
import android.widget.TextView
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL


class FilmActivity : AppCompatActivity() {

    private fun normalizeUrl(url: String): String{
        return url.replace("http://kino-bendery.infohttp://kino-bendery.info", "http://kino-bendery.info")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)
        var data = ParseRss().execute().get()
        var textView2 = findViewById<TextView>(R.id.textView2)
        var textView3 = findViewById<TextView>(R.id.textView3)
        var textView4 = findViewById<TextView>(R.id.textView4)
        var element: String = intent.getSerializableExtra("Element") as String
        var size  = data.names!!.size
        var pos: Int = 0
        var textView5 = findViewById<TextView>(R.id.textView5)
        for (s in 0..size){
            if (element == data.names!!.elementAt(s)){
                pos = s
                break
            }
        }
        textView2.text = data.names!!.elementAt(pos)
        textView3.text = fromHtml(data.descriptions!!.elementAt(pos))
        textView4.text = data.halls!!.elementAt(pos)
        textView5.text = data.seances!!.elementAt(pos)
        var imageView = findViewById<ImageView>(R.id.imageView)
        var image = DownloadImage().execute(normalizeUrl(data.imageUrls!!.elementAt(pos))).get()
        if (image != null){
            imageView.setImageBitmap(image)
        }

    }

    private inner class DownloadImage : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg args: String): Bitmap? {
            val url: URL
            val out: BufferedOutputStream
            val `in`: InputStream
            val buf: BufferedInputStream
            try {
                url = URL(args[0])
            } catch (e: MalformedURLException) {
                return null
            }

            val bmp: Bitmap
            try {
                `in` = url.openStream()
                buf = BufferedInputStream(`in`)
                bmp = BitmapFactory.decodeStream(buf)
                `in`.close()
                buf.close()
            } catch (e: IOException) {
                return null
            }

            return bmp
        }
    }
}
