package se.magictechnology.ondev4dec

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import se.magictechnology.ondev4dec.ui.theme.Ondev4decTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ondev4decTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MLexample()
                }
            }
        }
    }
}

fun doMlkit(ctx : Context) {

    val drawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.frog, null)
    val bitmapDrawable = drawable as BitmapDrawable
    val bitmap = bitmapDrawable.bitmap

    val image = InputImage.fromBitmap(bitmap, 0)

    val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    labeler.process(image)
        .addOnSuccessListener { labels ->
            // Task completed successfully
            // ...
            for (label in labels) {
                val text = label.text
                val confidence = label.confidence
                val index = label.index

                Log.i("ondevdebug", confidence.toString())
                Log.i("ondevdebug", text)
            }
        }
        .addOnFailureListener { e ->
            // Task failed with an exception
            // ...
            Log.i("ondevdebug", "IMAGE FAIL")
        }
}

@Composable
fun MLexample() {

    val ctx = LocalContext.current

    Column {
        Text("ML EXAMPLE")
        Button(onClick = { doMlkit(ctx = ctx) }) {
            Text("Do ML")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MLexamplePreview() {
    Ondev4decTheme {
        MLexample()
    }
}