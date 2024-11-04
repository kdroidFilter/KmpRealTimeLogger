package com.kdroid.kmplog.client.presentation.screens.settings

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
import com.kdroid.kmplog.core.SERVICE_PORT
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.LocalTextStyle
import org.jetbrains.jewel.ui.component.*
import org.jetbrains.jewel.ui.component.Typography.labelTextSize

@Composable
actual fun SettingsScreen() {
    var checkboxState by remember { mutableStateOf(ToggleableState.On) }
    val addressIpState = rememberTextFieldState("")
    val portState = rememberTextFieldState("")

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
                text = "Host Configuration",
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomTriStateCheckboxRow(
                    text = "Détection automatique",
                    state = checkboxState,
                    onClick = {
                        checkboxState = when (checkboxState) {
                            ToggleableState.On -> ToggleableState.Off
                            ToggleableState.Off -> ToggleableState.Indeterminate
                            ToggleableState.Indeterminate -> ToggleableState.On
                        }
                    },
                    enabledText = checkboxState == ToggleableState.On,
                )

                SettingsTextFieldRow(
                    label = "Address IP",
                    state = addressIpState,
                    placeholder = "192.168.xxx.xxx",
                    enabled = checkboxState != ToggleableState.On,
                    modifier = Modifier.fillMaxWidth()
                )

                SettingsTextFieldRow(
                    label = "Port",
                    state = portState,
                    placeholder = SERVICE_PORT.toString(),
                    enabled = checkboxState != ToggleableState.On,
                    modifier = Modifier.fillMaxWidth(),
                    fieldWidth = 80.dp
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
    fieldWidth: Dp = 0.dp
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
