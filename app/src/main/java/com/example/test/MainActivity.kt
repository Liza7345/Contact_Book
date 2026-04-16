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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.ButtonDefaults


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

    val phoneNumber = stringResource(R.string.phone_number)
    val emailAddress = stringResource(R.string.email_address)
    val emailSubject = stringResource(R.string.email_subject)
    val shareText = stringResource(R.string.share_contact_text)
    val location = stringResource(R.string.office_location)
    val label = stringResource(R.string.office_label)

    val toastNoCallApp = stringResource(R.string.toast_no_call_app)
    val toastNoEmailApp = stringResource(R.string.toast_no_email_app)
    val toastNoMapApp = stringResource(R.string.toast_no_map_app)
    val toastNoShareApp = stringResource(R.string.toast_no_share_app)

    val buttonCallText = stringResource(R.string.button_call)
    val buttonEmailText = stringResource(R.string.button_email)
    val buttonMapText = stringResource(R.string.button_map)
    val buttonShareText = stringResource(R.string.button_share)

    val buttonBgColor = colorResource(R.color.button_bg)
    val buttonTextColor = colorResource(R.color.button_text)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.contact_book_title)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.top_bar_container)
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
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, toastNoCallApp, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgColor,
                    contentColor = buttonTextColor
                )
            ) {
                Text(buttonCallText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$emailAddress")
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                    }
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, toastNoEmailApp, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgColor,
                    contentColor = buttonTextColor
                )
            ) {
                Text(buttonEmailText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val uri = Uri.parse("geo:$location?q=$location($label)")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, toastNoMapApp, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgColor,
                    contentColor = buttonTextColor
                )
            ) {
                Text(buttonMapText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }

                    // Исправление: проверяем, есть ли хоть одно приложение для обработки Intent
                    if (intent.resolveActivity(context.packageManager) != null) {
                        val chooser = Intent.createChooser(intent, buttonShareText)
                        context.startActivity(chooser)
                    } else {
                        Toast.makeText(context, toastNoShareApp, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgColor,
                    contentColor = buttonTextColor
                )
            ) {
                Text(buttonShareText)
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