package com.game.synergism.uiscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainApplication() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Button(modifier = Modifier.fillMaxWidth(0.7f).height(70.dp), onClick = {}) {
            Text("Workers: 0 [+0]\ncost: 100 coins\nCoins/Sec: 0 [0.00%]")
        }
        Button(modifier = Modifier.fillMaxWidth(0.7f).height(70.dp), onClick = {}) {
            Text("Investments: 0 [+0]\ncost: 1000 coins\nCoins/Sec: 0 [0.00%]")
        }
        Button(modifier = Modifier.fillMaxWidth(0.7f).height(70.dp), onClick = {}) {
            Text("Printers: 0 [+0]\ncost: 10000 coins\nCoins/Sec: 0 [0.00%]")
        }
        Button(modifier = Modifier.fillMaxWidth(0.7f).height(70.dp), onClick = {}) {
            Text("Coin Mints: 0 [+0]\ncost: 100000 coins\nCoins/Sec: 0 [0.00%]")
        }
        Button(modifier = Modifier.fillMaxWidth(0.7f).height(70.dp), onClick = {}) {
            Text("Alchemies: 0 [+0]\ncost: 1000000 coins\nCoins/Sec: 0 [0.00%]")
        }
    }
}

@Preview(device = "id:pixel_7_pro")
@Composable
fun PreviewMainApplication() {
    MainApplication()
}
