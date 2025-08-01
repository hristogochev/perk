package io.github.hristogochev.perk.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
public actual fun rememberPermissionChecker(): PermissionChecker {
    return remember {
        object : PermissionChecker {
            override fun isPermissionGranted(permission: Permission, onResult: (Boolean) -> Unit) {
                isIOSPermissionGranted(permission, onResult)
            }
        }
    }
}

