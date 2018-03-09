package com.example.tonik.cinema
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.tonik.cinemabender.FilmAdapter
import com.example.tonik.cinemabender.MyOnClickLIstener
import com.example.tonik.cinemabender.ParseRss
import com.example.tonik.cinemabender.R


class MainActivity : AppCompatActivity() {

    // Проверка на подключение к интернету
    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var data = ParseRss().execute().get()
        var listOfFilms = findViewById<RecyclerView>(R.id.listOfFilms)
        listOfFilms.layoutManager = LinearLayoutManager(this)
        listOfFilms.adapter = FilmAdapter(data, this)
    }
}




