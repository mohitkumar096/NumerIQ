package com.mohit.numeriq.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val buttonColors = when (label) {
        "C", "⌫", "%" -> ButtonDefaults.buttonColors(containerColor = Color(0xFF757575)) // Medium gray
        "/", "x", "-", "+", "=" -> ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)) // Brighter Orange
        else -> ButtonDefaults.buttonColors(containerColor = Color(0xFF424242)) // Darker gray
    }

    Button(
        onClick = onClick,
        shape = RectangleShape, // Circular buttons for a modern look
        colors = buttonColors,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp), // Adds slight elevation
        modifier = modifier.size(75.dp).clip(RoundedCornerShape(14.dp)) // Consistent button size
    ) {
        Text(
            text = label,
            fontSize = 26.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

