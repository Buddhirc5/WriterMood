package com.grayseal.notesapp.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grayseal.notesapp.navigation.NoteScreens
import com.grayseal.notesapp.ui.theme.*
import com.grayseal.notesapp.ui.theme.AppTheme
import com.grayseal.notesapp.ui.theme.ThemeManager

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThemeChangerScreen(navController: NavController) {
    val currentTheme by remember { mutableStateOf(ThemeManager.currentTheme) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(1000)
                        )
                    ) {
                        Text(
                            "Choose Theme",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = sonoFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = ThemeManager.getPrimaryColor()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(ThemeManager.getBackgroundColor())
        ) {
            // Header
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(1000, delayMillis = 300))
            ) {
                Text(
                    text = "Select Your Theme",
                    style = TextStyle(
                        color = ThemeManager.getPrimaryColor(),
                        fontSize = 24.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(20.dp)
                )
            }
            
            // Theme Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(AppTheme.values()) { theme ->
                    ThemeCard(
                        theme = theme,
                        isSelected = theme == currentTheme,
                        onClick = {
                            ThemeManager.setTheme(theme)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThemeCard(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val primaryColor = when (theme) {
        AppTheme.PINK -> Color(0xFFdaaac0)
        AppTheme.BLUE -> Color(0xFF4A90E2)
        AppTheme.GREEN -> Color(0xFF4CAF50)
        AppTheme.PURPLE -> Color(0xFF9C27B0)
        AppTheme.ORANGE -> Color(0xFFFF9800)
    }
    
    val backgroundColor = when (theme) {
        AppTheme.PINK -> Color(0xFFf8f4ff)
        AppTheme.BLUE -> Color(0xFFf0f8ff)
        AppTheme.GREEN -> Color(0xFFf1f8e9)
        AppTheme.PURPLE -> Color(0xFFf3e5f5)
        AppTheme.ORANGE -> Color(0xFFfff3e0)
    }
    
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(1000, delayMillis = 500)) + scaleIn(
            animationSpec = tween(1000, delayMillis = 500)
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { onClick() },
            elevation = if (isSelected) 8.dp else 4.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = backgroundColor
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Theme color circle
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(primaryColor)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Theme name
                    Text(
                        text = theme.name.lowercase().capitalize(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = sonoFamily,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor
                        )
                    )
                    
                    // Selected indicator
                    if (isSelected) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = primaryColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
