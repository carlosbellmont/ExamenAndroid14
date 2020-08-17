package com.cbellmont.ejemploandroidviewmodel

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var model :MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        downloadAll()


    }

    private fun downloadAll(){
        CoroutineScope(IO).launch {
            val list = model.getFilms()
            setTextOnMainThread(list)
        }
    }

    private suspend fun setTextOnMainThread(list: MutableList<Film>) {
        withContext (Main) {
            list.forEach {
                tvFilms.append("${it.name}\n")
            }
            pbLoading.visibility = View.GONE
        }
    }
}