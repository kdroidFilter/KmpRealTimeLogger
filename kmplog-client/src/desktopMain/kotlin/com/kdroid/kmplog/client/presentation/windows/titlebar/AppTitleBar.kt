package com.kdroid.kmplog.client.com.kdroid.kmplog.client.presentation.windows.titlebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(
                            color = if (true) Color.Green else Color.Red,
                            shape = CircleShape
                        )
                )
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



