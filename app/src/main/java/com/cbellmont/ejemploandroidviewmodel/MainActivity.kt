package com.cbellmont.ejemploandroidviewmodel

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cbellmont.ejemploandroidviewmodel.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var model :MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                binding.tvFilms.append("${it.name}\n")
            }
            binding.pbLoading.visibility = View.GONE
        }
    }
}