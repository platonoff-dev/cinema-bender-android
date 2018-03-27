package com.example.tonik.cinemabender

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import kotlin.collections.HashMap

class FirebaseWorking{
    var base = FirebaseFirestore.getInstance().collection("films")
    fun addLike(name: String){
        base.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()){
                var a = HashMap<String, String>()
                a.put("likes", (it.getString("likes").toInt() + 1).toString())
                base.document(name).set(a as Map<String, String>)
            }
        })
    }
    fun getNumberOfLikes(name: String){

    }
    fun addComent(name: String){

    }
    fun getComents(name: String){

    }
    fun addFilm(name: String){
        var film = HashMap<String, String>()
        film.put("coments", "")
        film.put("likes", Random().nextInt().toString())
        base.document(name).set(film as Map<String, Any>)
    }
}