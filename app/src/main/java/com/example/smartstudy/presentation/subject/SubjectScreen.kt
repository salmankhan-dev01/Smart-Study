package com.example.smartstudy.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.smartstudy.domain.model.Session
import com.example.smartstudy.domain.model.Subject
import com.example.smartstudy.domain.model.Task
import com.example.smartstudy.presentation.component.AddSubjectDialog
import com.example.smartstudy.presentation.component.CountCard
import com.example.smartstudy.presentation.component.DeleteDialog
import com.example.smartstudy.presentation.component.studySessionList
import com.example.smartstudy.presentation.component.taskList
import com.example.smartstudy.sessions
import com.example.smartstudy.tasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen() {
    val scrollBehavior= TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState= rememberLazyListState()
    val isFABExpended by remember { derivedStateOf { listState.firstVisibleItemIndex==0 } }


    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Subject.subjectCardColor.random()) }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = { isAddSubjectDialogOpen = false },
        subjectName = subjectName,
        goalHours = goalHours,
        selectedColors =selectedColor,
        onSubjectNameChange = { subjectName=it },
        onGoalHoursChange = {goalHours=it},
        onColorChange = {selectedColor=it}
    )

    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Subject?",
        bodyText = "Are you sure,You want to delete this subject? Your study and tasks session will be permanently deleted. This action can't be undo",
        onDismissRequest = {isDeleteSubjectDialogOpen=false},
        onConfirmButtonClick = {  isDeleteSubjectDialogOpen=false}
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete session?",
        bodyText = "Are you sure,You want to delete this session? Your studied hours will be reduced by this session time. This action can't be undo",
        onDismissRequest = {isDeleteSessionDialogOpen=false},
        onConfirmButtonClick = {  isDeleteSessionDialogOpen=false}
    )



    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubectScreenTopBar(
                title = "English",
                onBackButtonClick = {},
                onDeleteButtonClick = {isDeleteSubjectDialogOpen=true},
                onEditButtonClick = {isAddSubjectDialogOpen=true},
                scrollBehavior=scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                icon = {Icon(imageVector = Icons.Default.Add, contentDescription = "Add")},
                text = {Text(text = "Add task")},
                expanded = isFABExpended
            )
        }
    ) { paddingValue ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            item {
                SubjectOverViewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    studiedHours = "43",
                    goalHours = "50",
                    progress = 0.75f
                )
            }

            this@LazyColumn.taskList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any subject.\n Click + button to add the task.",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item { Spacer(modifier = Modifier.height(20.dp)) }
            this@LazyColumn.taskList(
                sectionTitle = "COMPLETED TASKS",
                emptyListText = "You don't have any completed tasks.\n Click the checkbox to complete the tasks.",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item { Spacer(modifier = Modifier.height(20.dp)) }

            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any subject.\n Click + button to add the task.",
                sessions = sessions,
                onDeleteIconClick = { isDeleteSubjectDialogOpen=true}
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubectScreenTopBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior=scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "navigation back")
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Subject")
            }
            IconButton(onClick = onEditButtonClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Subject")
            }
        }
    )
}


@Composable
private fun SubjectOverViewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress: Float,
) {
    val parcentageProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal study hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study hours",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$progress%")
        }
    }
}
