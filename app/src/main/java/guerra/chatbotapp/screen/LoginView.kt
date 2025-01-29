package guerra.chatbotapp.screen

import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import guerra.chatbotapp.R
import guerra.chatbotapp.data.Result
import guerra.chatbotapp.viewmodel.AuthViewModel

@Composable
fun LoginView(
    onNavigateToSignUp: () -> Unit,
    authViewModel: AuthViewModel,
    context: Context,
    onSignInSuccess: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember {
        mutableStateOf("")
    }

    val result by authViewModel.authResult.observeAsState()

    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (email.isEmpty() && password.isEmpty()) Toast.makeText(context, "Email and Password are Empty", Toast.LENGTH_SHORT).show()
                else if (email.isEmpty()) Toast.makeText(context, "Email is Empty", Toast.LENGTH_SHORT).show()
                else if (password.isEmpty()) Toast.makeText(context, "Password is Empty", Toast.LENGTH_SHORT).show()
                else {
                    authViewModel.login(email.trim(), password)
                    isLoading = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(8.dp)
        ) {
            if (!isLoading) Text("Sign In")
            else CircularProgressIndicator(
                modifier = Modifier.width(24.dp),
                colorResource(R.color.white)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(
                "Don't have an account?"
            )

            Text(
                text = "Sign up.",
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable { onNavigateToSignUp() },
                colorResource(R.color.purple_500)
            )
        }
    }

    result?.let {
        when (it) {
            is Result.Success -> {
                onSignInSuccess()
                Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                isLoading = false
                authViewModel.clearResult()
            }

            is Result.Error -> {
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                isLoading = false
                authViewModel.clearResult()
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    LoginView()
//}