package com.example.tonik.cinemabender

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html.fromHtml
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.tonik.cinemabender.Activities.CommentActivity
import com.squareup.picasso.Picasso


class FilmActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_activity)
        var filmPoster = findViewById<ImageView>(R.id.filmPoster)
        var nameView = findViewById<TextView>(R.id.filmName)
        var descriptionView = findViewById<TextView>(R.id.filmDescription)
        var hallView = findViewById<TextView>(R.id.filmHall)
        var seanceView = findViewById<TextView>(R.id.filmSeance)
        var filmInfo = intent.getSerializableExtra("filmInfo") as String
        var data  = filmInfo.split('~')
        nameView.text = data[0] //name
        descriptionView.text = fromHtml(data[1]) //description
        hallView.text = data[2] //hall
        seanceView.text = data[3] //time
        Picasso.get()
                .load(data[4])
                .error(R.color.error_color_material)
                .resize(160, 240)
                .into(filmPoster)
        var commentFilmButton = findViewById<ImageButton>(R.id.commentFilmButton)
        commentFilmButton.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, CommentActivity::class.java)
            intent.putExtra("name", data[0])
            startActivity(intent)
        })
    }
}
