package com.mohit.numeriq

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.numeriq.ui.theme.NumerIQTheme
import com.mohit.numeriq.utils.Strings


@Composable
fun AboutApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(modifier = Modifier.height(200.dp))
        }
        // App Name
        item {
            Text(
                text = Strings.appName,
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )
        }

        // Version
        item {
            Text(
                text = "Version 1.0.0",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )
        }

        // App Description
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = Strings.about,
                    style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Developer Info
        item {
            Text(
                text = "Developed by "+ Strings.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )
        }

        item {
            Text(
                text = "© 2025 All Rights Reserved",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )
        }

        // Contact Us Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 220.dp)
                    .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📞 Contact Us",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )

                ContactItem(
                    icon = R.drawable.ic_email,
                    title = "Email",
                    description = "mohitsaini19215@gmail.com",
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:mohitsaini19215@gmail.com")
                        }
                        context.startActivity(emailIntent)
                    }
                )

                ContactItem(
                    icon = R.drawable.ic_linkedin,
                    title = "LinkedIn",
                    description = "www.linkedin.com/in/mohitkumar9639919215",
                    onClick = {
                        val linkedInIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://www.linkedin.com/in/mohitkumar9639919215")
                        }
                        context.startActivity(linkedInIntent)
                    }
                )

                ContactItem(
                    icon = R.drawable.ic_chat,
                    title = "WhatsApp",
                    description = "9639919215 (HI, Mohit)",
                    onClick = {
                        val whatsAppIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://wa.me/919639919215?text=HI,%20Mohit")
                        }
                        context.startActivity(whatsAppIntent)
                    }
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    icon: Any, // Allow both Int and ImageVector
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (icon) {
            is Int -> Image( // Handle drawable resource ID
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            is ImageVector -> Icon( // Handle ImageVector
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            else -> {} // Handle other cases if needed
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewAboutApp() {
    NumerIQTheme {
        AboutApp()
    }
}
