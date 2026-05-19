package io.github.hristogochev.perk.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
public actual fun rememberPermissionRequestLauncher(onPermissionStatusChanged: (Permission, PermissionStatus) -> Unit): PermissionRequestLauncher {
    val onPermissionStatusChangedUpdated by rememberUpdatedState(newValue = onPermissionStatusChanged)

    return remember {
        object : PermissionRequestLauncher {
            override fun launch(vararg permissions: Permission) {
                launch(permissions.toList())
            }

            override fun launch(permissions: List<Permission>) {
                for (permission in permissions) {
                    onPermissionStatusChangedUpdated(permission, PermissionStatus.Granted)
                }
            }
        }
    }
}