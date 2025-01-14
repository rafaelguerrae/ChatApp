package guerra.chatbotapp.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import guerra.chatbotapp.viewmodel.AuthViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignUpScreen.route
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
                onNavigateToSignUp = { navController.navigate(Screen.SignUpScreen.route) } ,
                authViewModel = authViewModel,
                context = context
            ){
                navController.navigate(Screen.ChatRoomsScreen.route)
            }
        }
        composable(Screen.ChatRoomsScreen.route){
            ChatRoomScreen()
        }
    }
}