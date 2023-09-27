package com.example.raceapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

class Participant(
    val name: String,
    val progressIncrement:Int=1,
    val maxProgress: Int = 100,
) {


    // Burada currentProgress'e disardan erisilip degistirilmesin diye onu private set olarak isaretledim
   var currentProgress by mutableStateOf(0)
       private set


    suspend fun runPlayer(){
        while (currentProgress<maxProgress){
            delay(200)
            currentProgress+=progressIncrement
        }
    }
    fun reset() {
        currentProgress = 0
    }


}



