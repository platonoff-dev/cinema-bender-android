package com.example.tonik.cinemabender.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.tonik.cinemabender.FirebaseWorking
import com.example.tonik.cinemabender.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class CommentActivity : AppCompatActivity() {
    lateinit var listOfComments: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        listOfComments = findViewById(R.id.commentsView)
        var fbw = FirebaseWorking(this)
        var name = intent.getSerializableExtra("name") as String
        var inputView = findViewById<TextInputLayout>(R.id.inputCommentView)
        var addCommentButton = findViewById<Button>(R.id.addCommentButton)
        generateComments(fbw.getComents(name))
        addCommentButton.setOnClickListener(View.OnClickListener {
            var comment = inputView.editText!!.text.toString()
            if (comment != "") {
                fbw.addComment(name, comment)
                inputView.editText!!.text.clear()
            }
        })
        FirebaseFirestore.getInstance().collection("films").document(name).addSnapshotListener(EventListener{
            documentSnapshot: DocumentSnapshot, firebaseFirestoreException: FirebaseFirestoreException? ->
            if (documentSnapshot.exists()){
                if (documentSnapshot.getString("comments") == null)
                    generateComments("")
                else
                    generateComments(documentSnapshot.getString("comments"))
            }
        })
    }
    private fun generateComments(comments: String){
        var lOF = comments.split('~')
        listOfComments.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lOF)
    }
}
