package com.example.smartstudy.presentation.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartstudy.domain.model.Subject
import com.example.smartstudy.presentation.component.DeleteDialog
import com.example.smartstudy.presentation.component.SubjectListBottomSheet
import com.example.smartstudy.presentation.component.studySessionList
import com.example.smartstudy.sessions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
) {
    val subjects = listOf(
        Subject(name = "English", getHours = 19f, colors = Subject.subjectCardColor[0], subjectId = 1),
        Subject(name = "Math", getHours = 19f, colors = Subject.subjectCardColor[1], subjectId = 1),
        Subject(name = "Physics", getHours = 19f, colors = Subject.subjectCardColor[2], subjectId = 1),
        Subject(name = "Compiler", getHours = 19f, colors = Subject.subjectCardColor[3], subjectId = 1),
        Subject(name = "Computer", getHours = 19f, colors = Subject.subjectCardColor[4], subjectId = 1),
    )


    val sheetState= rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val scope= rememberCoroutineScope()

    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }


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

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Session ?",
        bodyText = "Do you want to delete the session",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = { isDeleteDialogOpen = false }
    )

    Scaffold(topBar = { SessionScreenTopBar(onBackButtonClick = {}) }) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValue)
        ) {
            item {
                TimerSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

            item {
                RelatedToSubjectScreen(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    relatedToSubject = "English",
                    selectSubjectBottomClick = {isBottomSheetOpen=true}
                )
            }


            item { 
              ButtonSection(
                  modifier = Modifier.fillMaxWidth().padding(12.dp),
                  startButtonClick = {},
                  cancelButtonClick ={},
                  finishButtonClick = {}
              ) 
            }
            studySessionList(
                sectionTitle = "STUDY SESSION HISTORY",
                emptyListText = "You don't have any subject.\n Click + button to add the task.",
                sessions = sessions,
                onDeleteIconClick = {isDeleteDialogOpen=true }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreenTopBar(
    onBackButtonClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        },
        title = {
            Text(text = "Study Session", style = MaterialTheme.typography.headlineSmall)
        }
    )
}

@Composable
private fun TimerSection(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(
                    5.dp, MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "00:59:34",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp)
            )
        }
    }
}

@Composable
fun RelatedToSubjectScreen(
    modifier: Modifier,
    relatedToSubject: String,
    selectSubjectBottomClick: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(text = "Related to subject", style = MaterialTheme.typography.bodySmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = relatedToSubject, style = MaterialTheme.typography.bodyLarge)

            IconButton(onClick = selectSubjectBottomClick) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select due date"
                )
            }
        }
    }
}

@Composable
private fun ButtonSection(
    modifier: Modifier,
    startButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = cancelButtonClick) {
            Text(
                text = "Cancel",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        Button(onClick = startButtonClick) {
            Text(
                text = "Start",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        Button(onClick = finishButtonClick) {
            Text(
                text = "Finish",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
    }
}