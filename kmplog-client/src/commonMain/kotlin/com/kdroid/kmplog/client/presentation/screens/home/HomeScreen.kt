package com.kdroid.kmplog.client.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.core.ScrollArea
import com.composables.core.ScrollAreaScope
import com.composables.core.rememberScrollAreaState
import com.kdroid.kmplog.client.presentation.theme.backgroundColor
import com.kdroid.kmplog.client.presentation.theme.getTerminalTextColor
import com.kdroid.kmplog.core.formatMessage
import com.kdroid.kmplog.core.formatTag
import com.kdroid.kmplog.core.getPriorityChar
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun Home() {
    val homeViewModel: HomeViewModel = koinViewModel()
    HomeScreen(homeState = rememberHomeScreenState(vm = homeViewModel), onEvent = homeViewModel::onEvent)
}

@Composable
fun HomeScreen(homeState: HomeState, onEvent: (HomeEvents) -> Unit) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val logMessages = homeState.logMessages
    val coroutineScope = rememberCoroutineScope()
    val fontSize = homeState.fontSize
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ControlsRow(onEvent = { onEvent(it) }, modifier = Modifier.fillMaxWidth())
        }
    ) { innerPadding ->
        // Défiler vers le bas automatiquement chaque fois que la liste de messages change
        LaunchedEffect(logMessages.size) {
            if (logMessages.isNotEmpty()) {
                coroutineScope.launch {
                    scrollState.scrollToItem(logMessages.size - 1)
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).background(backgroundColor())) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                val state = rememberScrollAreaState(scrollState)
                ScrollArea(state = state) {
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
                                val formatedMsg =
                                    if (isHorizontal) formatMessage(logMessage.message) else logMessage.message
                                val formatedTag = if (isHorizontal) formatTag(logMessage.tag) else (logMessage.tag)

                                Text(
                                    text = "${logMessage.timestamp} $formatedTag ${getPriorityChar(logMessage.priority)} $formatedMsg",
                                    color = getTerminalTextColor(logMessage.priority),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = fontSize.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                    }
                    Scrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        scrollbarState = scrollState
                    )
                }

            }
        }
    }
}

@Composable
expect fun ControlsRow(modifier: Modifier = Modifier, onEvent: (HomeEvents) -> Unit)

@Composable
expect fun  ScrollAreaScope.Scrollbar(modifier: Modifier, scrollbarState: LazyListState)