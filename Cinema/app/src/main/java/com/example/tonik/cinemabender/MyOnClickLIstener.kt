package com.example.tonik.cinemabender

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity


class MyOnClickLIstener(var position: Int, var data: List<Film>, var context: Context): View.OnClickListener{
    override fun onClick(p0: View?) {
        MyOnCLick(position)
    }

    private fun MyOnCLick(pos: Int){
        val intent = Intent(context, FilmActivity::class.java)
        val filmInfo: Bundle = Bundle()
        val str = data[pos].name+"~"+data[pos].description+"~"+data[pos].hall+"~"+data[pos].seance+"~"+data[pos].imageURL
        intent.putExtra("filmInfo", str)
        startActivity(context, intent, filmInfo)
    }

}

class OnLikeClickListener(var position: Int, var data: List<Film>, var context: Context): View.OnClickListener{
    override fun onClick(view: View?) {
        var fbw = FirebaseWorking(context)
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
        fbw.addLike(data[position].name, context)
    }
}

