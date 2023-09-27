import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.raceapp.R
import kotlinx.coroutines.delay

@Composable
fun WalkingManScreen() {
    var isWalking by remember { mutableStateOf(false) }
    var rotation by remember { mutableStateOf(0f) }

    LaunchedEffect(isWalking) {
        while (isWalking) {
            rotation += 10f // Dönme hızı
            delay(100) // Dönme aralığı
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Gray)
                .rotate(rotation)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_walk),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isWalking = !isWalking
            },
            modifier = Modifier.size(100.dp)
        ) {
            Text(text = if (isWalking) "Durdur" else "Başlat")
        }
    }
}

@Composable
fun MyApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        WalkingManScreen()
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun PreviewWalkingManScreen() {
    MyApp()
}
