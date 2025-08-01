package io.github.hristogochev.perk.permission

public sealed interface PermissionStatus {
    public data object Granted : PermissionStatus
    public data object TemporarilyDeclined : PermissionStatus
    public data object PermanentlyDeclined : PermissionStatus
}

