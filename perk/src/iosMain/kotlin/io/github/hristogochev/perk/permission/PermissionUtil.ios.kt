package io.github.hristogochev.perk.permission

import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionRecordPermissionGranted
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNUserNotificationCenter

public fun requestCameraPermission(callback: (Boolean) -> Unit) {
    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
        callback(granted)
    }
}

public fun requestMicrophonePermission(callback: (Boolean) -> Unit) {
    AVAudioSession.sharedInstance().requestRecordPermission { granted ->
        callback(granted)
    }
}


private val NOTIFICATION_PERMISSIONS = UNAuthorizationOptionAlert or
        UNAuthorizationOptionSound or
        UNAuthorizationOptionBadge

public fun requestPostNotificationsPermission(onResult: (Boolean) -> Unit) {
    val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
    notificationCenter.requestAuthorizationWithOptions(NOTIFICATION_PERMISSIONS) { isGranted, _ ->
        onResult(isGranted)
    }
}

public fun isIOSPermissionGranted(permission: Permission, onResult: (Boolean) -> Unit) {
    return when (permission) {
        Permission.Microphone -> {
            val micStatus = AVAudioSession.sharedInstance().recordPermission()
            onResult(micStatus == AVAudioSessionRecordPermissionGranted)
        }

        Permission.PostNotifications -> {
            val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
            notificationCenter.getNotificationSettingsWithCompletionHandler { settings ->
                val isAuthorized = settings?.authorizationStatus == UNAuthorizationStatusAuthorized
                onResult(isAuthorized)
            }
        }

        Permission.Camera -> {
            val cameraStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
            onResult(cameraStatus == AVAuthorizationStatusAuthorized)
        }
    }
}