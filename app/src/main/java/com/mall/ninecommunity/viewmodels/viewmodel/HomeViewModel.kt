package com.mall.ninecommunity.viewmodels.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mall.baselibrary.base.viewModel.BaseViewModel
import com.mall.ninecommunity.model.main.Plant

/**
 *
 */
class HomeViewModel(app:Application):BaseViewModel(app){
    var plantList = MutableLiveData<MutableList<Plant>>()

    fun loadDada(){
        val list = mutableListOf<Plant>()
        repeat(20){
            val plant = Plant()
            plant.plantId = it.toString()
            plant.plantName = "尘世迷途小郎酒${it}"
            list.add(plant)
        }
        plantList.value = list
    }
}