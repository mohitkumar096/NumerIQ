package com.mohit.numeriq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.compose.rememberNavController
import com.mohit.numeriq.ui.screen.CalculatorScreen
import com.mohit.numeriq.ui.theme.NumerIQTheme
import com.mohit.numeriq.utils.Strings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumerIQTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { AppBar(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(navController, startDestination = "calculator") {
            composable("calculator") { CalculatorScreen(modifier = Modifier.padding(innerPadding)) }
            composable("about") { AboutApp(modifier = Modifier.padding(innerPadding)) }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController) {
    val context = LocalContext.current
    var themeColor: Color
    var textColor: Color

    if (isSystemInDarkTheme()) {
        themeColor = Color(0xFF101010)
        textColor = Color.White
    } else {
        themeColor = Color(0xFFCACACA)
        textColor = Color.Black
    }

    TopAppBar(
        title = { Text(Strings.appName, color = textColor) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = themeColor
        ),
        actions = {
            IconButton(onClick = {
                navController.navigate("about") // Navigate to AboutApp screen
            }) {
                Icon(Icons.Default.Info, contentDescription = "About", tint = textColor)
            }

        }
    )
}
@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    NumerIQTheme {
        AppContent()
    }
}
