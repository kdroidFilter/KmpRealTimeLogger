package com.kdroid.kmplog.client.features.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdroid.kmplog.client.kmplog_client.generated.resources.Res
import com.kdroid.kmplog.client.kmplog_client.generated.resources.port
import com.kdroid.kmplog.client.kmplog_client.generated.resources.restart_app_message
import com.kdroid.kmplog.client.kmplog_client.generated.resources.server_configuration
import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.LocalTextStyle
import org.jetbrains.jewel.ui.component.*
import org.jetbrains.jewel.ui.component.Typography.labelTextSize


@Composable
actual fun SettingsScreen(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {

    val portState = rememberTextFieldState(state.customPort)

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(JewelTheme.globalColors.panelBackground)
                .padding(24.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GroupHeader(
                text = stringResource(Res.string.server_configuration),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                stringResource(Res.string.restart_app_message),
                color = JewelTheme.globalColors.text.disabled,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                SettingsTextFieldRow(
                    label = stringResource(Res.string.port),
                    state = portState,
                    placeholder = DEFAULT_SERVICE_PORT.toString(),
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    fieldWidth = 80.dp,
                    onTextChanged = { onEvent(SettingsEvent.OnPortChange(portState.text.toString())) }

                )
            }
        }
    }
}

@Composable
private fun SettingsTextFieldRow(
    label: String,
    state: TextFieldState,
    placeholder: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    fieldWidth: Dp = 0.dp,
    onTextChanged: (String) -> Unit,
) {
    Row(
        horizontalArrangement = if (fieldWidth > 0.dp) Arrangement.SpaceBetween else Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = label,
            fontSize = labelTextSize(),
            style = Typography.h0TextStyle(),
            fontWeight = FontWeight.Normal,
            color = if (enabled) {
                JewelTheme.globalColors.text.normal
            } else {
                JewelTheme.globalColors.text.disabled
            }
        )
        TextField(
            state = state,
            placeholder = {
                Text(
                    text = placeholder,
                    style = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = if (fieldWidth > 0.dp) Modifier.width(fieldWidth) else Modifier.weight(1f),
            enabled = enabled,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )
    }
    LaunchedEffect(state.text) {
        onTextChanged(state.text.toString())
    }
}

@Composable
private fun CustomTriStateCheckboxRow(
    text: String,
    state: ToggleableState,
    onClick: () -> Unit,
    enabledText: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        TriStateCheckbox(
            state = state,
            onClick = onClick,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = labelTextSize(),
            style = Typography.h0TextStyle(),
            fontWeight = FontWeight.Normal,
            color = if (enabledText) {
                JewelTheme.globalColors.text.normal
            } else {
                JewelTheme.globalColors.text.disabled
            },
            modifier = Modifier.weight(1f)
        )
    }
}
