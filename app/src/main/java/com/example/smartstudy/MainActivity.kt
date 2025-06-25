package com.example.smartstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smartstudy.domain.model.Session
import com.example.smartstudy.domain.model.Subject
import com.example.smartstudy.domain.model.Task
import com.example.smartstudy.presentation.dashboard.DashboardScreen
import com.example.smartstudy.presentation.subject.SubjectScreen
import com.example.smartstudy.presentation.task.TaskScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskScreen()
        }
    }
}


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