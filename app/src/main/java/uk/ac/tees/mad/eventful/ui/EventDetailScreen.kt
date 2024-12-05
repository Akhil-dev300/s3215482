package uk.ac.tees.mad.eventful.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EditEventScreen(
    eventId: String,
    navController: NavController,
    viewModel: EditEventViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(eventId) {
        viewModel.getEventDetails(eventId)
    }

    Text(text = "Event Id")

}
