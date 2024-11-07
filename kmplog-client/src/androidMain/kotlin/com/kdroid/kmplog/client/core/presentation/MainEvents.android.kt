package com.kdroid.kmplog.client.core.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.java.KoinJavaComponent.inject

actual fun openGithubPage() {
    val context : Context by inject(Context::class.java)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_PAGE))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}