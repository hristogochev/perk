package io.github.hristogochev.perk.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
public actual fun rememberAppSettingsOpener(): AppSettingsOpener {
    val context = LocalContext.current

    return remember(context) {
        object : AppSettingsOpener {
            override fun open(): Boolean {
                context.openAppSettings()
                return true
            }
        }
    }
}

public fun Context.openAppSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)
    )
    startActivity(intent)
}