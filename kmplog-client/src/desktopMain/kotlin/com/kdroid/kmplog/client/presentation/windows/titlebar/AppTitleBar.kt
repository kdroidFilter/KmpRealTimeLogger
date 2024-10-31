package com.kdroid.kmplog.client.com.kdroid.kmplog.client.presentation.windows.titlebar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.client.data.network.WebSocketManager
import com.kdroid.kmplog.client.presentation.icons.ConnexionStatusIcon
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar


@Composable
fun DecoratedWindowScope.AppTitleBar() {
    TitleBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.padding(start = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                ConnexionStatusIcon(WebSocketManager.isConnected.collectAsState().value)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 64.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )


            }
        }

    }
}



