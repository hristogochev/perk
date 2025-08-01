package io.github.hristogochev.perk.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import io.github.hristogochev.perk.util.map
import io.github.hristogochev.perk.util.mapNotNullKeys


@Composable
public actual fun rememberPermissionRequestLauncher(onPermissionStatusChanged: (Permission, PermissionStatus) -> Unit): PermissionRequestLauncher {
    val context = LocalContext.current

    val onPermissionStatusChangedUpdated by rememberUpdatedState(newValue = onPermissionStatusChanged)

    val permissionsRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val (grantedPermissions, declinedPermissions) = permissions.entries
                .partition { it.value }
                .map { value1, value2 ->
                    value1.map { it.key } to value2.map { it.key }
                }

            val (permanentlyDeclinedPermissions, temporarilyDeclinedPermissions) = declinedPermissions.partition {
                context.isPermissionPermanentlyDeclined(it)
            }


            val granted =
                grantedPermissions.associateWith { PermissionStatus.Granted }

            val temporarilyDeclined =
                temporarilyDeclinedPermissions.associateWith { PermissionStatus.TemporarilyDeclined }

            val permanentlyDeclined =
                permanentlyDeclinedPermissions.associateWith { PermissionStatus.PermanentlyDeclined }

            val all = (granted + temporarilyDeclined + permanentlyDeclined).mapNotNullKeys {
                Permission.fromPlatformPermission(it)
            }

            for ((permission, status) in all) {
                onPermissionStatusChangedUpdated(permission, status)
            }
        }
    )

    return remember(permissionsRequestLauncher) {
        object : PermissionRequestLauncher {
            override fun launch(vararg permissions: Permission) {
                launch(permissions.toList())
            }

            override fun launch(permissions: List<Permission>) {
                val permissionsToRequest = permissions.mapNotNull { permission ->
                    when (permission) {
                        Permission.Microphone -> Manifest.permission.RECORD_AUDIO
                        Permission.PostNotifications -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Manifest.permission.POST_NOTIFICATIONS
                        } else null

                        Permission.Camera -> Manifest.permission.CAMERA
                    }
                }.toTypedArray()

                if (permissionsToRequest.isEmpty()) return

                permissionsRequestLauncher.launch(permissionsToRequest)
            }
        }
    }
}