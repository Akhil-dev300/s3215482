package uk.ac.tees.mad.eventful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.eventful.ui.CreateEventScreen
import uk.ac.tees.mad.eventful.ui.EditEventScreen
import uk.ac.tees.mad.eventful.ui.EventDetailScreen
import uk.ac.tees.mad.eventful.ui.HomeScreen
import uk.ac.tees.mad.eventful.ui.LoginScreen
import uk.ac.tees.mad.eventful.ui.ProfileScreen
import uk.ac.tees.mad.eventful.ui.RegisterScreen
import uk.ac.tees.mad.eventful.ui.SplashScreen
import uk.ac.tees.mad.eventful.ui.theme.EventfulTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventfulTheme {
                Scaffold { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("splash") { SplashScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable("home") { HomeScreen(navController) }
                        composable("create_event") { CreateEventScreen(navController) }
                        composable("event_detail/{eventId}") { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getString("eventId")
                            if (eventId != null) {
                                EventDetailScreen(eventId, navController)
                            }
                        }
                        composable("edit_event/{eventId}") { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getString("eventId")
                            if (eventId != null) {
                                EditEventScreen(eventId, navController)
                            }
                        }
                        composable("profile") { ProfileScreen(navController) }

                    }
                }

            }
        }
    }
}
