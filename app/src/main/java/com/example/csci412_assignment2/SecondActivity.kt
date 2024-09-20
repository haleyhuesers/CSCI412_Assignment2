package com.example.csci412_assignment2

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.csci412_assignment2.ui.theme.Csci412_Assignment2Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent() {
            Csci412_Assignment2Theme {
                ChallengeApp()
            }
        }
    }
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

@Preview
@Composable
private fun ChallengeAppPreview() {
    ChallengeApp()
}