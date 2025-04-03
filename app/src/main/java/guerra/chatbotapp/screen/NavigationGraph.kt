package guerra.chatbotapp.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import guerra.chatbotapp.viewmodel.AuthViewModel
import guerra.chatbotapp.viewmodel.RoomViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    roomViewModel: RoomViewModel,
    context: Context,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.ChatRoomsScreen.route else Screen.SignInScreen.route
    ) {
        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                onNavigateToLogin = { navController.popBackStack() },
                authViewModel = authViewModel,
                context = context
            )
        }
        composable(Screen.SignInScreen.route) {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate(Screen.SignUpScreen.route)},
                authViewModel = authViewModel,
                context = context,
                onSignInSuccess = {
                    navController.navigate(Screen.ChatRoomsScreen.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.ChatRoomsScreen.route) {
            ChatRoomScreen(
                roomViewModel = roomViewModel,
                context = context,
                onSignOut = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}