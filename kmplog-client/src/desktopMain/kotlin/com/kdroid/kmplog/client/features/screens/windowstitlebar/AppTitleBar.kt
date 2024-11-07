package com.kdroid.kmplog.client.features.screens.windowstitlebar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.client.core.presentation.MainViewModel
import com.kdroid.kmplog.client.core.presentation.icons.ConnexionStatusIcon
import com.kdroid.kmplog.client.core.presentation.icons.Github
import com.kdroid.kmplog.client.core.presentation.theme.iconColor
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DecoratedWindowScope.AppTitleBar() {
    val mainViewModel : MainViewModel = koinViewModel()
    val state = rememberAppTitleBarState(mainViewModel)
    AppTitleBarComponent(state)
}

@Composable
fun DecoratedWindowScope.AppTitleBarComponent(state: AppTitleBarState) {
    TitleBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConnexionStatusIcon(state.isConnected)

            Text(text = title, modifier = Modifier.padding(start = 8.dp))

            Spacer(Modifier.weight(1f))

            IconButton(onClick = { /* Action à définir */ }) {
                Icon(imageVector = Github, contentDescription = "GitHub", tint = iconColor())
            }

        }

    }
}



