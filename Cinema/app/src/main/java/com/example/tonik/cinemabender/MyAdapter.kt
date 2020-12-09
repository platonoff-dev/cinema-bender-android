package com.example.tonik.cinemabender

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.squareup.picasso.Picasso

class FilmAdapter(var data: List<Film>, var context: Context): RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var view = itemView
        var filmPoster = itemView!!.findViewById<ImageView>(R.id.filmPoster)
        var filmName  = itemView!!.findViewById<TextView>(R.id.nameView)
        var filmDate  = itemView!!.findViewById<TextView>(R.id.dateView)
        var likeView: ImageView = itemView!!.findViewById<ImageView>(R.id.likeImage)
        var numberOfLikesView = itemView!!.findViewById<TextView>(R.id.numberOfLikesView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.film_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    var fBase = FirebaseFirestore.getInstance().collection("films")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
                .load(data[position].imageURL)
                .resize(90, 140)
                .error(R.color.design_default_color_error)
                .into(holder.filmPoster)
        for (element in data){
            fBase.document(element.name).get().addOnSuccessListener {
                if (!it.exists()){
                    val document = HashMap<String, String>()
                    document.put("likes", "0")
                    document.put("comments", "")
                    fBase.document(element.name).set(document as Map<String, String>)
                }
            }
        }
        fBase.document(data[position].name).addSnapshotListener{
            documentSnapshot: DocumentSnapshot?, _: FirebaseFirestoreException? ->
            if (documentSnapshot!!.exists())
                holder.numberOfLikesView.text = documentSnapshot.getString("likes")
        }
        holder.view!!.setOnClickListener(MyOnClickLIstener(position, data, context))
        holder.filmName.text = data[position].name
        holder.filmDate.text = data[position].seance
        holder.likeView.setOnClickListener(OnLikeClickListener(position, data, context))
    }
}
