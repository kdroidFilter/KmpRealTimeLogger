package com.kdroid.kmplog.client.core.presentation

import java.awt.Desktop
import java.net.URI

actual fun openGithubPage() {
    Desktop.getDesktop().browse(URI(GITHUB_PAGE))
}