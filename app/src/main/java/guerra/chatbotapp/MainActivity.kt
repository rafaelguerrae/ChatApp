package guerra.chatbotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import guerra.chatbotapp.screen.NavigationGraph
import guerra.chatbotapp.ui.theme.ChatbotAppTheme
import guerra.chatbotapp.viewmodel.AuthViewModel
import guerra.chatbotapp.viewmodel.RoomViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            val roomViewModel: RoomViewModel = viewModel()
            val context = LocalContext.current

            ChatbotAppTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = isSystemInDarkTheme()
                val statusBarColor = MaterialTheme.colorScheme.surface

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = !useDarkIcons
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    NavigationGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        roomViewModel = roomViewModel,
                        context = context,
                        isLoggedIn = FirebaseAuth.getInstance().currentUser != null
                    )
                }
            }
        }
    }
}