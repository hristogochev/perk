package io.github.hristogochev.perk.permission

import androidx.compose.runtime.Composable


@Composable
public expect fun rememberPermissionChecker(): PermissionChecker

public interface PermissionChecker {
    public fun isPermissionGranted(permission: Permission, onResult: (Boolean) -> Unit)
}
