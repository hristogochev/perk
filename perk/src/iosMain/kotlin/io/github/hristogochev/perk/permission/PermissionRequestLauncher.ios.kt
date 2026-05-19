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
                permissions.forEach { permission ->
                    isIOSPermissionGranted(permission) { hasPermission ->
                        if (hasPermission) {
                            onPermissionStatusChangedUpdated(
                                permission,
                                PermissionStatus.Granted
                            )
                            return@isIOSPermissionGranted
                        }
                        val requestPermission = when (permission) {
                            Permission.Camera -> ::requestCameraPermission
                            Permission.Microphone -> ::requestMicrophonePermission
                            Permission.PostNotifications -> ::requestPostNotificationsPermission
                        }
                        requestPermission { isGranted ->
                            if (isGranted) {
                                onPermissionStatusChangedUpdated(
                                    permission,
                                    PermissionStatus.Granted
                                )
                            } else {
                                onPermissionStatusChangedUpdated(
                                    permission,
                                    PermissionStatus.PermanentlyDeclined
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}