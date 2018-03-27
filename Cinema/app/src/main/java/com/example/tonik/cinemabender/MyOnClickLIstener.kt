package com.example.tonik.cinemabender

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.GestureDetector
import android.view.View


class MyOnClickLIstener(var position: Int, var data: List<Film>, var context: Context): View.OnClickListener{
    override fun onClick(p0: View?) {
        MyOnCLick(position)
    }

    private fun MyOnCLick(pos: Int){
        var intent = Intent(context, FilmActivity::class.java)
        var filmInfo: Bundle = Bundle()
        var str = data[pos].name+"~"+data[pos].description+"~"+data[pos].hall+"~"+data[pos].seance+"~"+data[pos].imageURL
        intent.putExtra("filmInfo", str)
        startActivity(context, intent, filmInfo)
    }

}

class OnLikeClickListener(var position: Int, var data: List<Film>): View.OnClickListener{
    override fun onClick(view: View?) {
        var fbw = FirebaseWorking()
        view!!.animate()
                .scaleX(5f)
                .scaleY(5f)
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .alpha(1f)
                            .setDuration(0)
                            .start()
                }
                .start()
        fbw.addLike(data[position].name)
    }

}
