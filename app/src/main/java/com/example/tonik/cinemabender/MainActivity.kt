package com.example.tonik.cinema
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.net.ConnectivityManager
import android.widget.*
import com.example.tonik.cinemabender.FilmActivity
import com.example.tonik.cinemabender.ParseRss
import com.example.tonik.cinemabender.R



class MainActivity : AppCompatActivity() {

    // Проверка на подключение к интернету
    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listOfFilms = findViewById<ListView>(R.id.listView)
        var data = ParseRss().execute().get()
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data.names)
        listOfFilms.adapter = adapter
        listOfFilms.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            var intent = Intent(this, FilmActivity::class.java)
            var element = adapter.getItem(i)
            intent.putExtra("Element", element)
            startActivity(intent)
            onStop()
        }
    }
}




