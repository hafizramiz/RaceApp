package com.example.raceapp.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.raceapp.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val Participant.progressValue: Float
    get() = currentProgress / maxProgress.toFloat()

@Composable

fun HomeScreen() {

    var isWalking by remember { mutableStateOf(false) }
    val walkingState by animateIntAsState(if (isWalking) 1 else 0)

    val transition = updateTransition(targetState = walkingState)
    val translationX by transition.animateFloat(
        label = "translationX"
    ) { state ->
        if (state == 0) {
            0f
        } else {
            200f // Yürüme mesafesi
        }
    }


    val playerOne: Participant = remember {
        Participant("playerOne", maxProgress = 100)
    }
    val playerTwo: Participant = remember {
        Participant("playerTwo", maxProgress = 100, progressIncrement = 2)
    }
    var raceInProgress by remember { mutableStateOf(false) }

    if (raceInProgress) {
        LaunchedEffect(playerOne, playerTwo) {
            coroutineScope {
// Tek bir launch icinde yaparsam birincinin bitmesini bekliyor. Ayri launch icinde yazarsam
                // Fire and Forget mantigiyla calisiyor.
                launch {
                    playerOne.runPlayer()

                }
                launch {
                    playerTwo.runPlayer()
                }
            }
            println("Coroutine scope bitti")
            raceInProgress = false
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Race App")

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_walk),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .offset(translationX.dp, 0.dp)
            )


            Spacer(modifier = Modifier.height(12.dp))
            Text("Player 1")
            CustomRow(curentProgress = playerOne.currentProgress)
            Indicator(progressValue = playerOne.progressValue)
            Spacer(modifier = Modifier.height(20.dp))
            Text("Player 1")
            CustomRow(curentProgress = playerTwo.currentProgress)
            Indicator(progressValue = playerTwo.progressValue)
            Spacer(modifier = Modifier.height(10.dp))
            RaceControls(
                isRunning = raceInProgress,
                onRunStateChange = {
                    raceInProgress = it
                    isWalking = !isWalking
                },
                onReset = {
                    playerOne.reset()
                    playerTwo.reset()
                    raceInProgress = false
                    if (isWalking) {
                        isWalking = false
                    }
                })

        }
    }
}


@Composable
private fun RaceControls(
    onRunStateChange: (Boolean) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    isRunning: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { onRunStateChange(!isRunning) }) {
            Text(if (isRunning) "Pause" else "Start")
        }

        Button(onClick = onReset) {
            Text("Reset")
        }
    }
}


@Composable
fun Indicator(progressValue: Float) {
    LinearProgressIndicator(
        progress = progressValue,
        modifier = Modifier
            .width(250.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(20.dp),
    )
}

@Composable
fun CustomRow(curentProgress: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(text = "Progress: ${curentProgress} %")

        Text(text = "100 % ")
    }
}


@Preview(showBackground = true)
@Composable
fun preview() {
    //Indicator()
    CustomRow(12)
}



