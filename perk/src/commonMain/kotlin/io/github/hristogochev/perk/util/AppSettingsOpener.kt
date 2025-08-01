package io.github.hristogochev.perk.util

import androidx.compose.runtime.Composable

@Composable
public expect fun rememberAppSettingsOpener(): AppSettingsOpener

public interface AppSettingsOpener {
    public fun open(): Boolean
}
