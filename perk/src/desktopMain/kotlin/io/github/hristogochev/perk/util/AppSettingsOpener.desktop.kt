package io.github.hristogochev.perk.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
public actual fun rememberAppSettingsOpener(): AppSettingsOpener {
    return remember {
        object : AppSettingsOpener {
            override fun open(): Boolean {
                return false
            }
        }
    }
}