package com.kdroid.kmplog.client.features.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kdroid.kmplog.client.kmplog_client.generated.resources.*
import com.kdroid.kmplog.core.DEFAULT_SERVICE_PORT
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.LocalTextStyle
import org.jetbrains.jewel.ui.component.*
import org.jetbrains.jewel.ui.component.Typography.labelTextSize


@Composable
actual fun SettingsScreen(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {

    var checkboxState by remember {
        mutableStateOf(
            when (state.automaticDetection) {
                true -> ToggleableState.On
                false -> ToggleableState.Off
            }
        )
    }
    LaunchedEffect(state.automaticDetection) {
        checkboxState = when (state.automaticDetection) {
            true -> ToggleableState.On
            false -> ToggleableState.Off
        }
    }
    val addressIpState = rememberTextFieldState(state.customIpAdress)
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
                text = stringResource(Res.string.host_configuration),
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomTriStateCheckboxRow(
                    text = stringResource(Res.string.automatic_detection),
                    state = checkboxState,
                    onClick = {
                        onEvent(
                            SettingsEvent.OnAutomaticDetectionChange(when (checkboxState) {
                            ToggleableState.On -> false
                            ToggleableState.Off, ToggleableState.Indeterminate -> true
                        }))

                    },
                    enabledText = checkboxState == ToggleableState.On,
                )

                SettingsTextFieldRow(
                    label = stringResource(Res.string.ip_address),
                    state = addressIpState,
                    placeholder = stringResource(Res.string.ip_placeholder),
                    enabled = checkboxState != ToggleableState.On,
                    modifier = Modifier.fillMaxWidth(),
                    onTextChanged = {onEvent(SettingsEvent.OnIpChange(addressIpState.text.toString()))}
                )

                SettingsTextFieldRow(
                    label = stringResource(Res.string.port),
                    state = portState,
                    placeholder = DEFAULT_SERVICE_PORT.toString(),
                    enabled = checkboxState != ToggleableState.On,
                    modifier = Modifier.fillMaxWidth(),
                    fieldWidth = 80.dp,
                    onTextChanged = {onEvent(SettingsEvent.OnPortChange(portState.text.toString()))}

                )
            }
        }
    }
}

@Composable
fun SettingsTextFieldRow(
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
fun CustomTriStateCheckboxRow(
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
