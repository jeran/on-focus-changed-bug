package dev.jeran.onfocuschangedbug

import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column {
                    val focusManager = LocalFocusManager.current
                    BackHandler { focusManager.clearFocus() }
                    TextField(
                        modifier = Modifier
                            .testTag("text_field")
                            .onFocusChanged {
                                when (Looper.myLooper() == Looper.getMainLooper()) {
                                    true -> Unit
                                    else -> error("onFocusChanged invoked off of main thread")
                                }
                            },
                        value = "",
                        onValueChange = {},
                    )
                }
            }
        }
    }
}
