package com.cbellmont.ejemploandroidviewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cbellmont.ejemploandroidviewmodel.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*


class MainActivityViewModel  : ViewModel() {

    private var films = mutableListOf<Film>()

    suspend fun showFilms(binding: ActivityMainBinding) {
        withContext(Dispatchers.Main) {
            binding.pbLoading.visibility = View.VISIBLE
        }
        withContext(Dispatchers.IO) {
            delay(1500)
            films = downloadFilms()
        }
        withContext(Dispatchers.Main) {
            films.forEach {
                binding.tvFilms.append("${it.name}\n")
            }
            binding.pbLoading.visibility = View.GONE
        }
    }

    private suspend fun downloadFilms(): MutableList<Film> {
        return viewModelScope.async {
            films.addAll(
                mutableListOf(
                    Film(1, "La Amenaza Fantasma", "aaaa"),
                    Film(2, "El Ataque de los Clones", "aaaa"),
                    Film(3, "La Venganza de los Sith", "aaaa"),
                    Film(4, "Una Nueva Esperanza", "aaaa"),
                    Film(5, "El Imperio Contraataca", "aaaa"),
                    Film(6, "El Retorno del Jedi", "aaaa"),
                    Film(4, "El Despertar de la Fuerza", "aaaa"),
                    Film(5, "Los Útimos Jedi", "aaaa"),
                    Film(6, "El Ascenso de Skywalker", "aaaa")
                )
            )
            films
        }.await()
    }



}