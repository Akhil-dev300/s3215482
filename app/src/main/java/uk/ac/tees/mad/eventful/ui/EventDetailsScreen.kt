package uk.ac.tees.mad.eventful.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EventDetailScreen(
    eventId: String,
    navController: NavController,
    viewModel: EventDetailViewModel = viewModel()
) {

    LaunchedEffect(eventId) {
        viewModel.getEventDetails(eventId)
    }

    Text(text = "Event: ${eventId}")
}
