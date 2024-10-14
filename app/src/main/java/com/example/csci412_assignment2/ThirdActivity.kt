package com.example.csci412_assignment2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.csci412_assignment2.ui.theme.Csci412_Assignment2Theme

class ThirdActivity : ComponentActivity() {

    private val cameraViewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register the camera intent result handler
        val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imageBitmap?.let {
                    cameraViewModel.updateImageBitmap(it)  // Save the image in ViewModel
                }
            }
        }

        setContent {
            Csci412_Assignment2Theme {
                CameraApp(
                    onCaptureClick = {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        takePictureLauncher.launch(cameraIntent)
                    },
                    imageBitmap = cameraViewModel.imageBitmap
                )
            }
        }
    }
}

@Composable
fun CameraApp(onCaptureClick: () -> Unit, imageBitmap: Bitmap?) {
    Surface(
        color = Color.hsv(190f, 0.5f, 0.7f, 1f),
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { onCaptureClick() }) {
                Text(stringResource(R.string.capture))
            }

            imageBitmap?.let {
                // Display the captured image
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Captured Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

class CameraViewModel : androidx.lifecycle.ViewModel() {
    var imageBitmap by mutableStateOf<Bitmap?>(null)
        private set

    fun updateImageBitmap(bitmap: Bitmap) {
        imageBitmap = bitmap
    }
}

@Preview
@Composable
private fun CameraAppPreview() {
   // CameraApp()
}