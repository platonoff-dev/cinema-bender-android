package com.example.tonik.cinemabender

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class FilmAdapter(var data: List<Film>, var context: Context): RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {
        var view = itemView
        var filmPoster = itemView!!.findViewById<ImageView>(R.id.filmPoster)
        var filmName  = itemView!!.findViewById<TextView>(R.id.nameView)
        var filmDate  = itemView!!.findViewById<TextView>(R.id.dateView)
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        var view = LayoutInflater.from(context).inflate(R.layout.film_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
                .load(data[position].imageURL)
                .resize(90, 140)
                .error(R.color.error_color_material)
                .into(holder.filmPoster)
        holder.view!!.setOnClickListener(MyOnClickLIstener(position, data, context))
        holder.filmName.text = data[position].name
        holder.filmDate.text = data[position].seance
    }
}
