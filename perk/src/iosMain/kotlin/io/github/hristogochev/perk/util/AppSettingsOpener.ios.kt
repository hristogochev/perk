package io.github.hristogochev.perk.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Composable
public actual fun rememberAppSettingsOpener(): AppSettingsOpener {
    return remember {
        object : AppSettingsOpener {
            override fun open(): Boolean {
                val url = NSURL.URLWithString(UIApplicationOpenSettingsURLString) ?: return false
                if (!UIApplication.sharedApplication.canOpenURL(url)) {
                    return false
                }
                return UIApplication.sharedApplication.openURL(url)
            }
        }
    }
}