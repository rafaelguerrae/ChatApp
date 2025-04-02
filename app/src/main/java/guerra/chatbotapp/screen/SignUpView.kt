package guerra.chatbotapp.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import guerra.chatbotapp.R
import guerra.chatbotapp.viewmodel.AuthViewModel

@Composable
fun SignUpView(
    onNavigateToLogin: () -> Unit,
    authViewModel: AuthViewModel,
    context: Context
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    val logoId = if(isSystemInDarkTheme()) painterResource(id = R.drawable.ic_text_white) else painterResource(id = R.drawable.ic_text)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = logoId, contentDescription = "Logo", modifier = Modifier.size(100.dp))

            Spacer(modifier = Modifier.size(4.dp))

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
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Button(
                onClick = {
                    authViewModel.signUp(email, password, firstName, lastName)
                    Toast.makeText(
                        context,
                        "Sign up has been done successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    email = ""
                    password = ""
                    firstName = ""
                    lastName = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Sign up", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onTertiary)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    "Already have an account?"
                )

                Text(
                    text = "Sign in.",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { onNavigateToLogin() },
                    color = MaterialTheme.colorScheme.primary
                )

            }
        }
    }
}
