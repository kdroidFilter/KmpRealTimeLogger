package com.kdroid.kmplog.client.core.presentation

import kotlinx.browser.window

actual fun openGithubPage() {
    window.open(GITHUB_PAGE, "_blank")
}