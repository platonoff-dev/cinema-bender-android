package com.example.tonik.cinemabender

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tonik.cinemabender.Activities.OfflineActivity


class MainActivity : AppCompatActivity() {

    // Check internet connection
    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isOnline()){
            val intent = Intent(this, OfflineActivity::class.java)
            startActivity(intent)
        }
        else{
            setContentView(R.layout.activity_main)
            val data = ParseRss().execute().get()
            val listOfFilms = findViewById<RecyclerView>(R.id.listOfFilms)
            listOfFilms.layoutManager = LinearLayoutManager(this)
            listOfFilms.adapter = FilmAdapter(data, this)
        }
    }
}




