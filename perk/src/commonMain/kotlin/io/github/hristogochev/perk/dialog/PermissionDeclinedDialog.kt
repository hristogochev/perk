package io.github.hristogochev.perk.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import io.github.hristogochev.perk.permission.Permission

@Composable
public fun PermissionDeclinedDialog(
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    header: String,
    description: String,
    onClickText: String,
    closeText: String,
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(36.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = header,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    lineHeight = (16 * 1.3).sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = Int.MAX_VALUE
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    lineHeight = (14 * 1.3).sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = Int.MAX_VALUE
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onClick,
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 32.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = onClickText,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.sp,
                        lineHeight = (16 * 1.3).sp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismiss,
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = closeText,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.sp,
                        lineHeight = (16 * 1.3).sp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                }
            }
        }
    }
}

public fun permissionDialogPermissionText(permission: Permission): String {
    return when (permission) {
        Permission.Microphone -> "access to your microphone"
        Permission.PostNotifications -> "access to your notifications"
        Permission.Camera -> "access to your camera"
    }
}