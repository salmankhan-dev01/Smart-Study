package com.example.smartstudy.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddSubjectDialog(
    isOpen: Boolean,
    title: String="Add/Update Subject",
    onDismissRequest:()-> Unit,
    onConfirmButtonClick:()-> Unit
) {
    if(isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title) },
            text = { Text(text = "Dialog body") },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(text = "Save")
                }
            }
        )
    }
}