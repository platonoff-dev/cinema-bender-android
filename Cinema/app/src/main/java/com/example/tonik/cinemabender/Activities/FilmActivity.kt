package com.example.tonik.cinemabender

import android.content.Intent
import android.os.Bundle
import android.text.Html.fromHtml
import androidx.appcompat.app.AppCompatActivity
import com.example.tonik.cinemabender.Activities.CommentActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.film_activity.*


class FilmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_activity)
        val filmPoster = filmPoster
        val filmInfo = intent.getSerializableExtra("filmInfo") as String
        val data  = filmInfo.split('~')

        filmName.text = data[0] //name
        filmDescription.text = fromHtml(data[1]) //description
        filmHall.text = data[2] + " зал" //hall
        filmSeance.text = data[3] //time
        Picasso.get()
                .load(data[4])
                .error(R.color.design_default_color_error)
                .resize(160, 240)
                .into(filmPoster)
        commentFilmButton.setOnClickListener {
            val intent = Intent(this, CommentActivity::class.java)
            intent.putExtra("name", data[0])
            startActivity(intent)
        }
    }
}
