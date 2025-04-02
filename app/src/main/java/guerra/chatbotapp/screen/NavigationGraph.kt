package guerra.chatbotapp.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = if(isLoggedIn) Screen.ChatRoomsScreen.route else Screen.SignUpScreen.route
    ) {
        composable(Screen.SignUpScreen.route) {
            SignUpView(
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) },
                authViewModel = authViewModel,
                context = context
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginView(
                onNavigateToSignUp = {  navController.popBackStack()} ,
                authViewModel = authViewModel,
                context = context,
                onSignInSuccess = {
                    navController.navigate(Screen.ChatRoomsScreen.route)
                }
            )
        }
        composable(Screen.ChatRoomsScreen.route){
            ChatRoomScreen(
                roomViewModel = roomViewModel,
                context = context
            )
        }
    }
}