package com.grayseal.notesapp.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grayseal.notesapp.R
import com.grayseal.notesapp.navigation.NoteScreens
import com.grayseal.notesapp.ui.theme.sonoFamily
import com.grayseal.notesapp.util.HapticFeedback
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    SplashContent(navController = navController)
}


@Composable
fun SplashContent(navController: NavController) {
    var showImage by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }
    
    // Apple-style staggered animation timing
    LaunchedEffect(Unit) {
        delay(200) // Initial pause
        showImage = true
        delay(400) // Staggered timing
        showTitle = true
        delay(300) // Smooth flow
        showSubtitle = true
        delay(500) // Final element
        showButton = true
    }
    
    // Apple-style floating animation for image
    val imageScale by animateFloatAsState(
        targetValue = if (showImage) 1f else 0.8f,
        animationSpec = tween(
            durationMillis = 800,
            easing = EaseOutBack
        )
    )
    
    val imageAlpha by animateFloatAsState(
        targetValue = if (showImage) 1f else 0f,
        animationSpec = tween(
            durationMillis = 600,
            easing = EaseInOut
        )
    )
    
    // Apple-style bounce animation for text
    val titleScale by animateFloatAsState(
        targetValue = if (showTitle) 1f else 0.9f,
        animationSpec = tween(
            durationMillis = 600,
            easing = EaseOutBack
        )
    )
    
    val titleAlpha by animateFloatAsState(
        targetValue = if (showTitle) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = EaseInOut
        )
    )
    
    val subtitleAlpha by animateFloatAsState(
        targetValue = if (showSubtitle) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = EaseInOut
        )
    )
    
    val buttonScale by animateFloatAsState(
        targetValue = if (showButton) 1f else 0.8f,
        animationSpec = tween(
            durationMillis = 500,
            easing = EaseOutBack
        )
    )
    
    val buttonAlpha by animateFloatAsState(
        targetValue = if (showButton) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = EaseInOut
        )
    )
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Apple-style floating image with scale and alpha
        Image(
            painter = painterResource(id = R.drawable.writer_image),
            contentDescription = "Splash Screen Image",
            modifier = Modifier
                .size(400.dp)
                .scale(imageScale)
                .alpha(imageAlpha)
                .graphicsLayer {
                    // Apple-style subtle shadow effect
                    shadowElevation = if (showImage) 8f else 0f
                }
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Apple-style title animation
        Text(
            "Unlock Your Writing Mood",
            style = (TextStyle(color = Color(0xFFdaaac0), fontSize = 25.sp)),
            fontFamily = sonoFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .scale(titleScale)
                .alpha(titleAlpha)
        )
        
        Spacer(Modifier.height(10.dp))
        
        // Apple-style subtitle fade-in
        Text(
            "Organize your notes beautifully",
            style = (TextStyle(fontSize = 18.sp, color = Color.Black)),
            fontFamily = sonoFamily,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(subtitleAlpha)
        )
        
        Spacer(Modifier.height(40.dp))
        
                // Apple-style button animation
        val context = LocalContext.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp), 
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .scale(buttonScale)
                    .alpha(buttonAlpha)
            ) {
                NextIconButton(
                    onClick = { 
                        HapticFeedback.mediumTap(context)
                        navController.navigate(route = NoteScreens.HomeScreen.name) 
                    }
                )
            }
        }
    }
}

@Composable
private fun NextIconButton(
    onClick: () -> Unit
) {
    FilledTonalIconButton(
        modifier = Modifier.size(60.dp),
        onClick = onClick,
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = Color(0xFFdaaac0)
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.Fingerprint,
            modifier = Modifier.size(50.dp),
            contentDescription = "Next",
            tint = Color.White
        )
    }
}