package io.github.hristogochev.perk.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import io.github.hristogochev.perk.dialog.PermissionDeclinedDialog
import io.github.hristogochev.perk.dialog.permissionDialogPermissionText
import io.github.hristogochev.perk.util.rememberAppSettingsOpener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.iterator

public typealias PermissionDeclinedDialog = @Composable (permission: Permission, onClick: () -> Unit, onDismiss: () -> Unit) -> Unit

@Composable
public fun rememberPermissionRequestDialogLauncher(
    vararg permissions: Permission,
    permissionTemporaryDeclinedDialog: PermissionDeclinedDialog = { fPermission, onClick, onDismiss ->
        PermissionDeclinedDialog(
            onClick = onClick,
            onDismiss = onDismiss,
            header = "Permission required",
            description = "You haven't allowed ${permissionDialogPermissionText(fPermission)}.",
            onClickText = "Give permission",
            closeText = "Close",
        )
    },
    permissionPermanentlyDeclinedDialog: PermissionDeclinedDialog = { fPermission, onClick, onDismiss ->
        PermissionDeclinedDialog(
            onClick = onClick,
            onDismiss = onDismiss,
            header = "Permission denied",
            description = "You haven't allowed ${permissionDialogPermissionText(fPermission)}. Please allow it from the app's settings.",
            onClickText = "Take me there",
            closeText = "Close",
        )
    },
    onPermissionGranted: (permission: Permission) -> Unit,
): PermissionRequestDialogLauncher {
    val appSettingsOpener = rememberAppSettingsOpener()

    val permissionStatuses = remember {
        mutableStateMapOf<Permission, PermissionStatus>()
    }

    val permissionChecker = rememberPermissionChecker()

    val permissionRequestLauncher =
        rememberPermissionRequestLauncher { fPermission, fPermissionStatus ->
            permissionStatuses[fPermission] = fPermissionStatus

            if (fPermissionStatus == PermissionStatus.Granted) {
                onPermissionGranted(fPermission)
            }
        }

    for (pair in permissionStatuses) {
        val permission = pair.key
        val permissionStatus = pair.value

        if (permissionStatus == PermissionStatus.TemporarilyDeclined) {
            val onClick = {
                permissionChecker.isPermissionGranted(permission) { granted ->
                    if (granted) {
                        permissionStatuses[permission] = PermissionStatus.Granted
                        onPermissionGranted(permission)
                        return@isPermissionGranted
                    }

                    if (permissionStatus == PermissionStatus.TemporarilyDeclined) {
                        permissionStatuses.remove(permission)
                    }

                    permissionRequestLauncher.launch(permission)
                }
            }

            val onDismiss = {
                permissionChecker.isPermissionGranted(permission) { granted ->
                    if (granted) {
                        permissionStatuses[permission] = PermissionStatus.Granted
                        return@isPermissionGranted
                    }

                    if (permissionStatus == PermissionStatus.TemporarilyDeclined) {
                        permissionStatuses.remove(permission)
                    }
                }
            }

            permissionTemporaryDeclinedDialog(
                permission,
                onClick,
                onDismiss,
            )
        }
        if (permissionStatus == PermissionStatus.PermanentlyDeclined) {
            val onClick = {
                permissionChecker.isPermissionGranted(permission) {
                    if (it) {
                        permissionStatuses[permission] = PermissionStatus.Granted
                        onPermissionGranted(permission)
                        return@isPermissionGranted
                    }

                    if (permissionStatus == PermissionStatus.PermanentlyDeclined) {
                        permissionStatuses.remove(permission)
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        appSettingsOpener.open()
                    }
                }
            }

            val onDismiss = {
                permissionChecker.isPermissionGranted(permission) {
                    if (it) {
                        permissionStatuses[permission] = PermissionStatus.Granted
                        return@isPermissionGranted
                    }

                    if (permissionStatus == PermissionStatus.PermanentlyDeclined) {
                        permissionStatuses.remove(permission)
                    }
                }
            }

            permissionPermanentlyDeclinedDialog(
                permission,
                onClick,
                onDismiss,
            )
        }
    }

    val perms by remember(permissions) {
        derivedStateOf {
            permissions.toList()
        }
    }

    return remember(permissionRequestLauncher, perms) {
        object : PermissionRequestDialogLauncher {
            override fun launch() {
                permissionRequestLauncher.launch(perms)
            }
        }
    }
}

public interface PermissionRequestDialogLauncher {
    public fun launch()
}