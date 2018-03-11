package com.example.tonik.cinemabender

import android.os.AsyncTask
import org.jdom2.input.SAXBuilder
import org.jonnyzzz.kotlin.xml.bind.XAttribute
import org.jonnyzzz.kotlin.xml.bind.XElements
import org.jonnyzzz.kotlin.xml.bind.XText
import org.jonnyzzz.kotlin.xml.bind.jdom.JDOM
import org.jonnyzzz.kotlin.xml.bind.jdom.JXML
import java.io.StringReader
import java.net.URL
import java.nio.charset.Charset

class ParseRss: AsyncTask<Void, Void, List<Film>>() {
    private fun dataToCollection(names: List<String>, descriptions: List<String>, halls: List<String>, seances: List<String>, imageURLs: List<String>): List<Film>{
        var index: Int
        var filmsCollection = List(names.size, {index -> Film(names[index], descriptions[index], halls[index], seances[index], normalizeUrl(imageURLs[index]))})
//        for (index: Int in 0..names.size){
//            filmsCollection.add(Film(names[index], descriptions[index], halls[index], seances[index], normalizeUrl(imageURLs[index])))
//        }
        return filmsCollection
    }

    private fun normalizeUrl(url: String): String{
        return url.replace("http://kino-bendery.infohttp://kino-bendery.info", "http://kino-bendery.info")
    }

    override fun doInBackground(vararg p0: Void?): List<Film> {
        val builder = SAXBuilder()
        val url = URL("http://kino-bendery.info/rss.xml")
        val text = url.readText(Charset.forName("windows-1251"))
        val document = builder.build(StringReader(text))
        val p: Rss = JDOM.load(document.rootElement, Rss::class.java)
        var data = dataToCollection(p.names!!, p.descriptions!!, p.halls!!, p.seances!!, p.imageUrls!!)
        return data
    }
}

class Rss {
    var names by JXML / "channel" / XElements("item") / "title" / XText
    var descriptions by JXML / "channel" / XElements("item") / "description" / XText
    var halls by JXML / "channel" / XElements("item") / "hall" / XText
    var seances by JXML / "channel" / XElements("item") / "seanses" / XText
    var imageUrls by JXML / "channel" / XElements("item") / "enclosure" / XAttribute("url")
}

class Film(var name: String, var description: String, var hall: String, var seance: String, var imageURL: String)
