package com.grayseal.notesapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grayseal.notesapp.ui.theme.sonoFamily

@Composable
fun WordCountDisplay(
    text: String,
    modifier: Modifier = Modifier
) {
    val wordCount = text.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
    val characterCount = text.length
    val characterCountNoSpaces = text.replace("\\s".toRegex(), "").length
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Words
            CountItem(
                icon = Icons.Default.TextFields,
                label = "Words",
                count = wordCount,
                color = Color(0xFF4CAF50)
            )
            
            // Characters
            CountItem(
                icon = Icons.Default.TextFields,
                label = "Characters",
                count = characterCount,
                color = Color(0xFF2196F3)
            )
            
            // Characters (no spaces)
            CountItem(
                icon = Icons.Default.TextFields,
                label = "No Spaces",
                count = characterCountNoSpaces,
                color = Color(0xFFFF9800)
            )
        }
    }
}

@Composable
private fun CountItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    count: Int,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = count.toString(),
            fontSize = 18.sp,
            fontFamily = sonoFamily,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        
        Text(
            text = label,
            fontSize = 10.sp,
            fontFamily = sonoFamily,
            color = Color.Gray
        )
    }
}

@Composable
fun CompactWordCount(
    text: String,
    modifier: Modifier = Modifier
) {
    val wordCount = text.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
    val characterCount = text.length
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "$wordCount words",
            fontSize = 12.sp,
            fontFamily = sonoFamily,
            color = Color.Gray
        )
        
        Text(
            text = "$characterCount chars",
            fontSize = 12.sp,
            fontFamily = sonoFamily,
            color = Color.Gray
        )
    }
}
