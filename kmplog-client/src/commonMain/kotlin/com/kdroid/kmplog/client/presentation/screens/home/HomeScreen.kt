package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.client.presentation.theme.getDarkColor
import com.kdroid.kmplog.core.formatMessage
import com.kdroid.kmplog.core.formatTag
import com.kdroid.kmplog.core.getPriorityChar
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun Home() {
    val homeViewModel: HomeViewModel = koinViewModel()
    HomeScreen(homeState = rememberHomeScreenState(homeViewModel = homeViewModel),)
}

@Composable
fun HomeScreen(homeState: HomeState){
    var size by remember { mutableStateOf(IntSize.Zero) }

    val logMessages = homeState.logMessages
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    // Défiler vers le bas automatiquement chaque fois que la liste de messages change
    LaunchedEffect(logMessages.size) {
        if (logMessages.isNotEmpty()) {
            coroutineScope.launch {
                scrollState.scrollToItem(logMessages.size - 1)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .onGloballyPositioned { layoutCoordinates ->
                    size = layoutCoordinates.size
                },
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            items(logMessages) { logMessage ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isHorizontal = size.width > size.height
                    val formatedMsg = if (isHorizontal) formatMessage(logMessage.message) else logMessage.message
                    val formatedTag = if (isHorizontal) formatTag(logMessage.tag) else (logMessage.tag)

                    Text(
                        text = "${logMessage.timestamp} $formatedTag ${getPriorityChar(logMessage.priority)} $formatedMsg",
                        color = getDarkColor(logMessage.priority),
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

