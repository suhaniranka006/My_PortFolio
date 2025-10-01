package com.example.my_portfolio


import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.time.format.TextStyle

/**
 * SplashScreen Composable
 * @param navController: used to navigate to MainScreen after splash
 */
@Composable
fun SplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.intro_tune)
    }

    // Scale animation for profile image
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1000, easing = EaseOutBack)
    )

    // Floating animation
    val offsetY by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Launch splash animation and navigation
    LaunchedEffect(true) {
        startAnimation = true
        mediaPlayer.start()
        delay(3000)
        mediaPlayer.release()
        navController.navigate("main_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    // Entire splash screen layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE7A8BD), Color(0xFFDE799B), Color(0xFFD74375))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image with Glow & Depth
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = offsetY.dp)
                    .scale(scale)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radiusOuter = size.minDimension / 1.1f
                    val radiusInner = size.minDimension / 1.5f

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = 0.2f), Color.Transparent),
                            center = center,
                            radius = radiusOuter
                        )
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Magenta.copy(alpha = 0.25f), Color.Transparent),
                            center = center,
                            radius = radiusInner
                        )
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = 0.15f), Color.Transparent),
                            center = center,
                            radius = radiusInner / 1.2f
                        )
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .shadow(24.dp, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Heavy Layered Name Text
            Box {
                Text(
                    text = "Suhani Ranka",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFF4081),
                    modifier = Modifier.offset(x = 3.dp, y = 3.dp)
                )
                Text(
                    text = "Suhani Ranka",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Animated Subtitle
            AnimatedSubtitleHeavy()
        }
    }
}

@Composable
fun AnimatedSubtitleHeavy() {
    var animateText by remember { mutableStateOf(false) }

    val fontSize by animateFloatAsState(
        targetValue = if (animateText) 26f else 18f,
        animationSpec = tween(durationMillis = 1000, easing = EaseOutCubic)
    )

    val textColor by animateColorAsState(
        targetValue = if (animateText) Color.White.copy(alpha = 0.9f) else Color.White.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 1000)
    )

    val scale by animateFloatAsState(
        targetValue = if (animateText) 1f else 0.8f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        delay(600)
        animateText = true
    }

    Text(
        text = "Aspiring Android Developer",
        fontSize = fontSize.sp,
        color = textColor,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.scale(scale)
    )
}
