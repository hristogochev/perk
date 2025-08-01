package io.github.hristogochev.perk.permission

import androidx.compose.runtime.Composable


@Composable
public expect fun rememberPermissionRequestLauncher(onPermissionStatusChanged: (Permission, PermissionStatus) -> Unit): PermissionRequestLauncher

public interface PermissionRequestLauncher {
    public fun launch(vararg permissions: Permission)
    public fun launch(permissions: List<Permission>)
}