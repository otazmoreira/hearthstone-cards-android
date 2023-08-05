package dev.tavieto.hearthstone.feature.main.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.tavieto.hearthstone.feature.main.R
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewAction.Get

@Composable
fun SplashScreen(viewModel: SplashViewModel) {
    Content()

    LaunchedEffect(Unit) {
        viewModel.dispatchAction(Get.LoadAllCards)
    }
}

@Composable
private fun Content() {
    val animationLottieFile by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            modifier = Modifier.size(400.dp),
            composition = animationLottieFile,
            iterations = LottieConstants.IterateForever,
            restartOnPlay = true
        )
        Text(
            text = "estamos preparando tudo!",
            fontSize = 20.sp
        )
    }
}
