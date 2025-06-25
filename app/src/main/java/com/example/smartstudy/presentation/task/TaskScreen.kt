package com.example.smartstudy.presentation.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartstudy.presentation.component.TaskCheckBox
import com.example.smartstudy.presentation.theme.Red

@Composable
fun TaskScreen() {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    Scaffold (topBar = {
        TaskScreenTopBar(
            isTaskExist = true,
            isComplete = false,
            checkBoxBorderColor = Red,
            onBackButtonClick = {},
            onDeleteButtonClick ={},
            onCheckBoxClick = {}
        )
    }){paddingValue->
        Column (modifier = Modifier.padding(paddingValue).padding(horizontal = 12.dp)){
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =title,
                onValueChange = {title=it},
                label = {Text(text = "Title")},
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =description,
                onValueChange = {description=it},
                label = {Text(text = "Description")},
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenTopBar(
    isTaskExist: Boolean,
    isComplete: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick:()-> Unit,
    onDeleteButtonClick:()-> Unit,
    onCheckBoxClick:()-> Unit,
){
    TopAppBar(navigationIcon = {
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Arrow")
        }
    },
        title = {Text(text = "Tasks", style = MaterialTheme.typography.headlineSmall)},
        actions = {
            if(isTaskExist){
                TaskCheckBox(
                    isComplete = isComplete,
                    borderColor = checkBoxBorderColor,
                    onCheckBoxClick = onCheckBoxClick
                )
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    )
}