package com.example.csci412_assignment2

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.csci412_assignment2.ui.theme.Csci412_Assignment2Theme


class SecondActivity : ComponentActivity() {

    private val permission = "com.example.csci412_assignment2.MSE412"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionFlow(
                permission = permission,
                onPermissionGranted = { ChallengeApp() },
                onPermissionDenied = { ChallengeAppDenied() }
            )
        }

        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
        } else {
            // Permission already granted
            Toast.makeText(this, "Permission already granted!", Toast.LENGTH_SHORT).show()
            setContent() {
                Csci412_Assignment2Theme {
                    ChallengeApp()
                }
            }
        }
    }
}

@Composable
fun PermissionFlow(
    permission: String,
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: @Composable () -> Unit
) {
    var hasPermission by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(true) }

    if (showPermissionDialog) {
        PermissionDialog(
            onGrant = {
                hasPermission = true
                showPermissionDialog = false
            },
            onDeny = {
                hasPermission = false
                showPermissionDialog = false
            }
        )
    }

    if (hasPermission) {
        onPermissionGranted()
    } else if (!showPermissionDialog) {
        onPermissionDenied()
    }
}

@Composable
fun PermissionDialog(onGrant: () -> Unit, onDeny: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = "Permission Request")
        },
        text = {
            Text(text = "Please grant permission to proceed.")
        },
        confirmButton = {
            Button(onClick = onGrant) {
                Text("Allow")
            }
        },
        dismissButton = {
            Button(onClick = onDeny) {
                Text("Deny")
            }
        }
    )
}

@Composable
fun ChallengeCard(challenge: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Text(
                text = challenge,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun ChallengeList(challengeList: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(challengeList) { challenge ->
            ChallengeCard (
                challenge = challenge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ChallengeApp() {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        color = Color.hsv(190f, 0.5f, 0.7f, 1f),
        modifier = Modifier.fillMaxSize()
        .statusBarsPadding()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            val cList: List<String> = listOf(stringResource(R.string.c1),
                stringResource(R.string.c2), stringResource(R.string.c3),
                stringResource(R.string.c4), stringResource(R.string.c5))
            ChallengeList(challengeList = cList)

            val context = LocalContext.current
            Button(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(stringResource(R.string.home))
            }
        }
    }
}

@Composable
fun ChallengeAppDenied() {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        color = Color.hsv(190f, 0.5f, 0.7f, 1f),
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            val context = LocalContext.current
            Button(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(stringResource(R.string.home))
            }
        }
    }
}


@Preview
@Composable
private fun ChallengeAppPreview() {
    ChallengeApp()
}