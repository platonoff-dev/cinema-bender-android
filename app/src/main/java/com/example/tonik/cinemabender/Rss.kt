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

class ParseRss: AsyncTask<Void, Void, Rss>() {
    override fun doInBackground(vararg p0: Void?): Rss {
        val builder = SAXBuilder()
        val url = URL("http://kino-bendery.info/rss.xml")
        val text = url.readText(Charset.forName("windows-1251"))
        val document = builder.build(StringReader(text))
        val p: Rss = JDOM.load(document.rootElement, Rss::class.java)
        return p
    }
}

class Rss {
    var title by JXML / "channel" / "title" / XText
    var names by JXML / "channel" / XElements("item") / "title" / XText
    var descriptions by JXML / "channel" / XElements("item") / "description" / XText
    var halls by JXML / "channel" / XElements("item") / "hall" / XText
    var seances by JXML / "channel" / XElements("item") / "seanses" / XText
    var imageUrls by JXML / "channel" / XElements("item") / "enclosure" / XAttribute("url")
}
