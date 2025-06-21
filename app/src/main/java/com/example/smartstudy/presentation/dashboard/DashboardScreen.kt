package com.example.smartstudy.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.smartstudy.domain.model.Subject
import com.example.smartstudy.presentation.component.CountCard
import com.example.smartstudy.R
import com.example.smartstudy.domain.model.Session
import com.example.smartstudy.domain.model.Task
import com.example.smartstudy.presentation.component.SubjectCard
import com.example.smartstudy.presentation.component.studySessionList
import com.example.smartstudy.presentation.component.taskList

@Composable
fun DashboardScreen() {

    val subject = listOf(
        Subject(name = "English", getHours = 19f, colors = Subject.subjectCardColor[0], subjectId = 1),
        Subject(name = "Math", getHours = 19f, colors = Subject.subjectCardColor[1], subjectId = 1),
        Subject(name = "Physics", getHours = 19f, colors = Subject.subjectCardColor[2], subjectId = 1),
        Subject(name = "Compiler", getHours = 19f, colors = Subject.subjectCardColor[3], subjectId = 1),
        Subject(name = "Computer", getHours = 19f, colors = Subject.subjectCardColor[4], subjectId = 1),
    )

    val tasks=listOf(
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 0, relatedToSubject = "", isComplete = false, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 1, relatedToSubject = "", isComplete = true, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 2, relatedToSubject = "", isComplete = false, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 1, relatedToSubject = "", isComplete = false, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 1, relatedToSubject = "", isComplete = true, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 0, relatedToSubject = "", isComplete = false, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 1, relatedToSubject = "", isComplete = false, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 2, relatedToSubject = "", isComplete = false, taskSubjectId = 0, taskId = 1),
        Task(title = "Prepare note", description = "", dueDate = 0L, priority = 1, relatedToSubject = "", isComplete = false, taskSubjectId = 0, taskId = 1)

    )

    val sessions=listOf(
        Session( sessionId = 0, date = 0L, duration = 2L, sessionSubjectId = 0, relatedToSubject = "Hindi"),
        Session( sessionId = 0, date = 0L, duration = 2L, sessionSubjectId = 0, relatedToSubject = "English"),
        Session( sessionId = 0, date = 0L, duration = 2L, sessionSubjectId = 0, relatedToSubject = "Web tech"),
        Session( sessionId = 0, date = 0L, duration = 2L, sessionSubjectId = 0, relatedToSubject = "Compiler"),
        Session( sessionId = 0, date = 0L, duration = 2L, sessionSubjectId = 0, relatedToSubject = "Hadoop"),
        Session( sessionId = 0, date = 0L, duration = 2L, sessionSubjectId = 0, relatedToSubject = "Automata")
    )

    Scaffold(
        topBar = { DashboardScreenTopBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    subjectCount = 15, studiedHours = "23", goalHours = "54"

                )
            }
            item {
                SubjectCardSection(modifier = Modifier.fillMaxWidth(), subjectList = subject)
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(text = "Start Study Session")
                }
            }
                this@LazyColumn.taskList(
                    sectionTitle = "UPCOMING TASKS",
                    emptyListText = "You don't have any subject.\n Click + button to add the task.",
                    tasks = tasks,
                    onCheckBoxClick = {},
                    onTaskCardClick = {}
                )
            item { Spacer(modifier = Modifier.height(20.dp)) }
            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any subject.\n Click + button to add the task.",
                sessions = sessions,
                onDeleteIconClick = {}
            )
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "StudySmart",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}

@Composable
private fun CountCardsSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String,
) {
    Row(modifier = modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
    }

}

@Composable
private fun SubjectCardSection(
    modifier: Modifier = Modifier,
    subjectList: List<Subject>,
    emptySubject: String = "You don't have any subject.\n Click + button to add the subject.",
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subject", style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add subject")
            }
        }

        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_books),
                contentDescription = emptySubject
            )
            Text(
                text = emptySubject, modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors,
                    onClick = {})
            }
        }
    }

}