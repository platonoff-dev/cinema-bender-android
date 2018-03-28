package com.example.tonik.cinemabender

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*
import kotlin.collections.HashMap

class FirebaseWorking{
    var fBase = FirebaseFirestore.getInstance().collection("films")
    fun addLike(name: String){
        fBase.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()){
                var document = HashMap<String, String>()
                document.put("likes", (it.getString("likes").toInt()+1).toString())
                document.put("comments", it.getString("comments"))
                fBase.document(name).set(document as Map<String, String>)
            }
            else {
                var document = HashMap<String, String>()
                document.put("likes", "0")
                document.put("comments", "")
                fBase.document(name).set(document as Map<String, String>)
                addLike(name)
            }
        })
    }

    fun addComent(name: String, comment: String){
        fBase.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()){
                var existComents = it.getString("comments")
                existComents = "$existComents$comment~"
                var document = HashMap<String, String>()
                document.put("comments", existComents)
                document.put("likes", it.getString("likes"))
                fBase.document(name).set(document as Map<String, String>)
            }
            else {
                var document = HashMap<String, String>()
                document.put("likes", "0")
                document.put("coments", "")
                fBase.document(name).set(document as Map<String, String>)
                addComent(name, comment)
            }
        })
    }
    fun getComents(name: String): String{
        var comments: String? = ""
        fBase.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()) {
                comments = it.getString("comments")
            }
        })
        if (comments == null){
            return ""
        }else{
            return comments!!.toString()
        }

    }
}