package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.core.formatMessage
import com.kdroid.kmplog.core.formatTag
import com.kdroid.kmplog.core.getPriorityChar
import kotlinx.coroutines.launch



@Composable
fun HomeScreen(viewModel: HomeViewModel){
    var size by remember { mutableStateOf(IntSize.Zero) }

    val logMessages = viewModel.logMessages
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
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

}

