package uk.ac.tees.mad.eventful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.eventful.ui.HomeScreen
import uk.ac.tees.mad.eventful.ui.LoginScreen
import uk.ac.tees.mad.eventful.ui.RegisterScreen
import uk.ac.tees.mad.eventful.ui.SplashScreen
import uk.ac.tees.mad.eventful.ui.theme.EventfulTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventfulTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") { SplashScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                }
            }
        }
    }
}
