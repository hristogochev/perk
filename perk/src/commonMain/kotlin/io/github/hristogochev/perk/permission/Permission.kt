package io.github.hristogochev.perk.permission

public sealed interface Permission {
    public data object Microphone : Permission
    public data object PostNotifications : Permission
    public data object Camera : Permission
    public companion object
}

