package com.kdroid.kmplog.client.presentation.windows.titlebar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.client.data.network.WebSocketManager
import com.kdroid.kmplog.client.presentation.icons.ConnexionStatusIcon
import com.kdroid.kmplog.client.presentation.icons.Github
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar


@Composable
fun DecoratedWindowScope.AppTitleBar() {
    TitleBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConnexionStatusIcon(WebSocketManager.isConnected.collectAsState().value)

            Text(text = title, modifier = Modifier.padding(start = 8.dp))

            Spacer(Modifier.weight(1f))

            IconButton(onClick = { /* Action à définir */ }) {
                Icon(imageVector = Github, contentDescription = "GitHub")
            }

        }

    }
}



