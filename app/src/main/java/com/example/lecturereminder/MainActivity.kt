package com.example.lecturereminder

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Calendar

// ------------------- Main Activity -------------------
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LectureReminderApp(this)
            }
        }
    }
}

// ------------------- Composables -------------------
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun LectureReminderApp(context: Context) {
    var lectures by remember { mutableStateOf(mutableListOf<Lecture>()) }
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (lectures.isEmpty()) {
            // Empty state UI
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "No Lectures",
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No lectures yet!",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tap the + button to add your first lecture reminder.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Lecture list
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(lectures) { lecture ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = lecture.subject,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Time: ${lecture.time}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Lecture")
        }

        if (showDialog) {
            AddLectureDialog(
                context = context,
                onDismiss = { showDialog = false },
                onSave = { subject, calendar ->
                    lectures.add(Lecture(subject, "${calendar.time}"))
                    scheduleAlarm(context, calendar, subject)
                    showDialog = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun AddLectureDialog(
    context: Context,
    onDismiss: () -> Unit,
    onSave: (String, Calendar) -> Unit
) {
    var subject by remember { mutableStateOf("") }
    val calendar = remember { Calendar.getInstance() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Lecture") },
        text = {
            Column {
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    // ---------------- Date Picker ----------------
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            calendar.set(Calendar.YEAR, year)
                            calendar.set(Calendar.MONTH, month)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                            // ---------------- Time Picker ----------------
                            TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    calendar.set(Calendar.MINUTE, minute)
                                    calendar.set(Calendar.SECOND, 0)
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            ).show()
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }) {
                    Text("Pick Date & Time")
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Selected: ${calendar.time}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            Button(onClick = { if (subject.isNotEmpty()) onSave(subject, calendar) }) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// ------------------- Data Class -------------------
data class Lecture(val subject: String, val time: String)

// ------------------- Alarm Function -------------------
@RequiresApi(Build.VERSION_CODES.M)
fun scheduleAlarm(context: Context, calendar: Calendar, subject: String) {
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("subject", subject)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        subject.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
        // Optionally prompt user to allow exact alarms in settings
    }

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}
