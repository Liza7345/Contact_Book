package com.example.test

import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme

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

    val onCallClick = {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startIntentSafely(context, intent, toastNoCallApp)
    }

    val onEmailClick = {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$emailAddress")
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        }
        startIntentSafely(context, intent, toastNoEmailApp)
    }

    val onMapClick = {
        val uri = Uri.parse("geo:$location?q=$location($label)")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startIntentSafely(context, intent, toastNoMapApp)
    }

    val onShareClick = {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            val chooser = Intent.createChooser(intent, buttonShareText)
            context.startActivity(chooser)
        } else {
            Toast.makeText(context, toastNoShareApp, Toast.LENGTH_SHORT).show()
        }
    }

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
            ActionButton(text = buttonCallText, onClick = onCallClick)
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(text = buttonEmailText, onClick = onEmailClick)
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(text = buttonMapText, onClick = onMapClick)
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(text = buttonShareText, onClick = onShareClick)
        }
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.button_bg),
            contentColor = colorResource(R.color.button_text)
        )
    ) {
        Text(text)
    }
}

private fun startIntentSafely(context: android.content.Context, intent: Intent, errorMessage: String) {
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun ContactBookScreenPreview() {
    TestTheme {
        ContactBookScreen()
    }
}