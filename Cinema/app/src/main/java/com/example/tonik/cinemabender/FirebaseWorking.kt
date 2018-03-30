package com.example.tonik.cinemabender

import android.content.Context
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseWorking(var context: Context){
    var fBase = FirebaseFirestore.getInstance().collection("films")
    fun addLike(name: String, context: Context){
        fBase.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()){
                var document = HashMap<String, String>()
                var sharedPref = context.getSharedPreferences("like status", Context.MODE_PRIVATE)
                var editor = sharedPref.edit()
                var status = sharedPref.getInt(name, 0)
                if (status == 0) {
                    document.put("likes", (it.getString("likes").toInt() + 1).toString())
                    editor.putInt(name, 1)
                    editor.apply()
                }
                else{
                    document.put("likes", (it.getString("likes").toInt() - 1).toString())
                    editor.putInt(name, 0)
                    editor.apply()
                }
                document.put("comments", it.getString("comments"))
                fBase.document(name).set(document as Map<String, String>)
            }
            else {
                var document = HashMap<String, String>()
                document.put("likes", "0")
                document.put("comments", "")
                fBase.document(name).set(document as Map<String, String>)
                addLike(name, context)
            }
        })
    }

    fun addComment(name: String, comment: String){
        fBase.document(name).get().addOnSuccessListener(OnSuccessListener {
            if (it.exists()){
                var existComents = it.getString("comments")
                if (existComents == ""){
                    existComents = comment
                }
                else {
                    existComents = "$comment~$existComents"
                }
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
                addComment(name, comment)
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