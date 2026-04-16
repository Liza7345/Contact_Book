package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.test.ui.theme.TestTheme
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTheme {
                ContactBookScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBookScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Контактная книга") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val phoneNumber = "+74951234567"
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Нет приложения для звонков", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp)
            ) {
                Text("📞 Позвонить")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:contact@example.com")
                        putExtra(Intent.EXTRA_SUBJECT, "Обращение")
                    }
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Нет приложения для почты", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp)
            ) {
                Text("✉️ Написать email")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val uri = Uri.parse("geo:60.0237,30.2289?q=60.0237,30.2289(Наш офис)")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Нет приложения для карт", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp)
            ) {
                Text("🗺️ Показать офис на карте")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val shareText = "Контакт: +7 (495) 123-45-67, contact@example.com"
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    val chooser = Intent.createChooser(intent, "Поделиться контактом")
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(chooser)
                    } else {
                        Toast.makeText(context, "Нет приложений для отправки", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp)
            ) {
                Text("📤 Поделиться контактом")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ContactBookScreenPreview() {
    TestTheme {
        ContactBookScreen()
    }
}
