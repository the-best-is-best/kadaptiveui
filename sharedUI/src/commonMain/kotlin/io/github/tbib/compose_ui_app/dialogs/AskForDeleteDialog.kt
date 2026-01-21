package io.github.tbib.compose_ui_app.dialogs

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import io.github.tbib.kadaptiveui.dialog.AdaptiveAlertDialog
import io.github.tbib.kadaptiveui.dialog.utils.AlertDialogIosActionStyle

@Composable
fun DeleteDialogQuestion(
    args: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AdaptiveAlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
        iosConfirmButtonStyle = AlertDialogIosActionStyle.Destructive,
        iosDismissButtonStyle = AlertDialogIosActionStyle.Default,

        onDismiss = onDismiss,
        onConfirm = onConfirm,

        // Material buttons
        materialConfirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F), // Red for delete
                    contentColor = Color.White
                )
            ) {
                Text(text = "Yes")
            }
        },
        materialDismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF757575) // Gray for cancel
                )
            ) {
                Text(text = "Cancel")
            }
        },

        // Title and text
        title = "Delete Item",
        text = "Are you sure you want to delete \"$args\"?",
        confirmText = "Yes",
        dismissText = "Cancel",
    )
}
