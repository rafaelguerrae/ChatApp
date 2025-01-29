package guerra.chatbotapp.viewmodel

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import guerra.chatbotapp.data.Result
import guerra.chatbotapp.data.User
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Boolean> =
        
        try {
            auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(firstName, lastName, email)
            saveUserToFireStore(user)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    private suspend fun saveUserToFireStore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun login(email: String, password: String): Result<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d("UserRepository Main", "signInWithEmailAndPassword")
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
}

