package com.game.synergism

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.game.synergism.uiconfig.SynergismTheme
import com.game.synergism.uiscreens.MainApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(parent = null) {
            SynergismTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = true) {
                MainApplication()
            }
        }
    }
}