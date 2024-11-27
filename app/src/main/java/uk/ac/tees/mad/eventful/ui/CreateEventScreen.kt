package uk.ac.tees.mad.eventful.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CreateEventScreen(navController: NavController) {
    var eventName by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Create Event",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TextField(
            value = eventName,
            onValueChange = { eventName = it },
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        TextField(
            value = eventDescription,
            onValueChange = { eventDescription = it },
            label = { Text("Event Description") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)

        )

        TextField(
            value = eventDate,
            onValueChange = { eventDate = it },
            label = { Text("Event Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)

        )

        TextField(
            value = eventTime,
            onValueChange = { eventTime = it },
            label = { Text("Event Time (HH:MM AM/PM)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)

        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = eventLocation,
                onValueChange = { eventLocation = it },
                label = { Text("Event Location") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)

            )
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                Text("Use GPS")
            }
        }

        Button(
            onClick = {

            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
        ) {
            Text("Create Event")
        }
    }
}

