package com.example.tonik.cinemabender

import android.content.Context
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseWorking(var context: Context){
    private var fBase = FirebaseFirestore.getInstance().collection("films")
    fun addLike(name: String, context: Context){
        fBase.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()){
                val document = HashMap<String, String>()
                val sharedPref = context.getSharedPreferences("like status", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                val status = sharedPref.getInt(name, 0)

                if (status == 0) {
                    document["likes"] = it.getString("likes")?.toInt()?.plus(1).toString()
                    editor.putInt(name, 1)
                    editor.apply()
                }
                else{
                    document["likes"] = (it.getString("likes")?.toInt()?.minus(1)).toString()
                    editor.putInt(name, 0)
                    editor.apply()
                }
                document["comments"] = it.getString("comments") ?: ""
                fBase.document(name).set(document as Map<String, String>)
            }
            else {
                val document = HashMap<String, String>()
                document["likes"] = "0"
                document["comments"] = ""
                fBase.document(name).set(document as Map<String, String>)
                addLike(name, context)
            }
        })
    }

    fun addComment(name: String, comment: String){
        fBase.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()){
                var existComments = it.getString("comments")

                existComments = if (existComments == ""){
                    comment
                } else {
                    "$comment~$existComments"
                }

                val document = HashMap<String, String>()

                document["comments"] = existComments
                document["likes"] = it.getString("likes") ?: "0"

                fBase.document(name).set(document as Map<String, String>)
            }
            else {
                val document = HashMap<String, String>()

                document["likes"] = "0"
                document["comments"] = ""
                fBase.document(name).set(document as Map<String, String>)
                addComment(name, comment)
            }
        })
    }
    fun getComments(name: String): String{
        var comments: String? = ""
        fBase.document(name).get().addOnSuccessListener {
            if (it.exists()) {
                comments = it.getString("comments")
            }
        }
        return if (comments == null){
            ""
        }else{
            comments!!.toString()
        }

    }
}