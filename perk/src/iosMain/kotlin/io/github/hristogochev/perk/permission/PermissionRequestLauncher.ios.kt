package io.github.hristogochev.perk.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
public actual fun rememberPermissionRequestLauncher(onPermissionStatusChanged: (Permission, PermissionStatus) -> Unit): PermissionRequestLauncher {
    return remember {
        object : PermissionRequestLauncher {
            override fun launch(vararg permissions: Permission) {
                launch(permissions.toList())
            }

            override fun launch(permissions: List<Permission>) {
                permissions.forEach { permission ->
                    isIOSPermissionGranted(permission) { hasPermission ->
                        if (hasPermission) {
                            onPermissionStatusChanged(
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
                                onPermissionStatusChanged(
                                    permission,
                                    PermissionStatus.Granted
                                )
                            } else {
                                onPermissionStatusChanged(
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