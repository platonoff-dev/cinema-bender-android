package com.example.tonik.cinemabender.Activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.tonik.cinemabender.FirebaseWorking
import com.example.tonik.cinemabender.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.activity_comment.*


class CommentActivity : AppCompatActivity() {
    lateinit var listOfComments: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        listOfComments = commentsView
        val fbw = FirebaseWorking(this)
        val name = intent.getSerializableExtra("name") as String
        val inputView = inputCommentView
        val addCommentButton = findViewById<Button>(R.id.addCommentButton)
        generateComments(fbw.getComments(name))
        addCommentButton.setOnClickListener {
            val comment = inputView.editText!!.text.toString()
            if (comment != "") {
                fbw.addComment(name, comment)
                inputView.editText!!.text.clear()
            }
        }

        val firestore = FirebaseFirestore.getInstance()
        val film = firestore.collection("films").document(name)
        film.addSnapshotListener { value, error ->
            snapshotListener(value, error)
        }
    }

    private fun snapshotListener(value: DocumentSnapshot?, error: FirebaseFirestoreException?): Unit {
        if (value != null && value.exists()) {
            if (value.getString("comments") == null)
                generateComments("")
            else
                generateComments(value.getString("comments") ?: "")
        }
    }

    private fun generateComments(comments: String) {
        val lOF = comments.split('~')
        listOfComments.adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lOF
        )
    }
}
