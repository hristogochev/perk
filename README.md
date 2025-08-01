# Perk

Heavily experimental Compose Multiplatform (Android/iOS) permission management library.

More permissions will be added when needed. Anyone can open an issue with a permission request.

### Setup

To use this library, add `perk` as a dependency to your project:

```toml
[versions]
perk = "0.1.0"

[libraries]
perk = { module = "io.github.hristogochev:perk", version.ref = "perk" }
```

### Examples

Check if a permission is granted:

```kotlin
val permissionChecker = rememberPermissionChecker()

permissionChecker.isPermissionGranted(Permission.PostNotifications) { isGranted: Boolean ->
    println("Permission granted: $isGranted")
}
```

Request a permission:

```kotlin
val permissionRequestLauncher = rememberPermissionRequestLauncher { permission:Permission, permissionStatus:PermissionStatus -> 
    when (permissionStatus) {
        PermissionStatus.Granted -> println("Permission granted")
        PermissionStatus.PermanentlyDeclined -> println("Permission permanently declined")
        PermissionStatus.TemporarilyDeclined -> println("Permission temporary declined")
    }
}
    
permissionRequestLauncher.launch(Permission.PostNotifications)
```

Request a permission until the user grants it or cancels the request:

```kotlin
val permissionRequestDialogLauncher = rememberPermissionRequestDialogLauncher(Permission.PostNotifications) { permission: Permission ->
    println("Permission granted") 
}

permissionRequestDialogLauncher.launch()
```

### License

Licensed under [MIT license](https://github.com/hristogochev/perk/blob/main/LICENSE).