package com.example.smartstudy.presentation.task

import android.R.style
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartstudy.domain.model.Subject
import com.example.smartstudy.presentation.component.DeleteDialog
import com.example.smartstudy.presentation.component.SubjectListBottomSheet
import com.example.smartstudy.presentation.component.TaskCheckBox
import com.example.smartstudy.presentation.component.TaskDatePicker
import com.example.smartstudy.presentation.theme.Red
import com.example.smartstudy.util.Priority
import com.example.smartstudy.util.changeMillisToDateString
import kotlinx.coroutines.launch
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    val subjects = listOf(
        Subject(name = "English", getHours = 19f, colors = Subject.subjectCardColor[0], subjectId = 1),
        Subject(name = "Math", getHours = 19f, colors = Subject.subjectCardColor[1], subjectId = 1),
        Subject(name = "Physics", getHours = 19f, colors = Subject.subjectCardColor[2], subjectId = 1),
        Subject(name = "Compiler", getHours = 19f, colors = Subject.subjectCardColor[3], subjectId = 1),
        Subject(name = "Computer", getHours = 19f, colors = Subject.subjectCardColor[4], subjectId = 1),
    )


    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    val dataPickerState = rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())

    val sheetState= rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val scope= rememberCoroutineScope()

    var taskTitleError by rememberSaveable { mutableStateOf<String?>(null) }
    taskTitleError = when {
        title.isBlank() -> "The title can't be blank"
        title.length < 4 -> "The title is too small"
        title.length > 30 -> "The title is too large"
        else -> null
    }

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Tasks ?",
        bodyText = "Do you want to delete the task",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = { isDeleteDialogOpen = false }
    )

    TaskDatePicker(
        state = dataPickerState,
        isOpen = isDatePickerDialogOpen,
        onDismissRequest = { isDatePickerDialogOpen = false },
        onConfirmButtonClicked = { isDatePickerDialogOpen = false }
    )

    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects =subjects,
        onDismissRequest = {isBottomSheetOpen=false},
        onSubjectClicked ={
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) isBottomSheetOpen=false
            }
        }
    )

    Scaffold(topBar = {
        TaskScreenTopBar(
            isTaskExist = true,
            isComplete = false,
            checkBoxBorderColor = Red,
            onBackButtonClick = {},
            onDeleteButtonClick = { isDeleteDialogOpen = true },
            onCheckBoxClick = {}
        )
    }) { paddingValue ->
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(paddingValue)
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                label = { Text(text = "Title") },
                singleLine = true,
                isError = taskTitleError != null && title.isNotBlank(),
                supportingText = { Text(text = taskTitleError.orEmpty()) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Description") },
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Due Date", style = MaterialTheme.typography.bodySmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dataPickerState.selectedDateMillis.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = { isDatePickerDialogOpen = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Due Date"
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Priority", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Priority.entries.forEach { priority ->
                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgrounColor = priority.color,
                        borderColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else Color.Transparent,
                        labelColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else Color.White.copy(alpha = 0.7f),
                        onClick = {}
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Related to subject", style = MaterialTheme.typography.bodySmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = "English", style = MaterialTheme.typography.bodyLarge)

                IconButton(onClick = {isBottomSheetOpen=true}) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select due date"
                    )
                }
            }
            Button(
                enabled = taskTitleError == null,
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenTopBar(
    isTaskExist: Boolean,
    isComplete: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Arrow")
            }
        },
        title = { Text(text = "Tasks", style = MaterialTheme.typography.headlineSmall) },
        actions = {
            if (isTaskExist) {
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

@Composable
private fun PriorityButton(
    modifier: Modifier = Modifier,
    label: String,
    backgrounColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(backgrounColor)
            .clickable { onClick() }
            .padding(5.dp)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp))
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = labelColor)
    }
}