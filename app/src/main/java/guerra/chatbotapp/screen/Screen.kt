package guerra.chatbotapp.screen

sealed class Screen(val route:String){
    object LoginScreen:Screen("loginScreen")
    object SignUpScreen:Screen("signUpScreen")
    object ChatRoomsScreen:Screen("chatRoomScreen")
    object ChatScreen:Screen("chatScreen")
}