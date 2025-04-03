package guerra.chatbotapp.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import guerra.chatbotapp.R
import guerra.chatbotapp.data.Result
import guerra.chatbotapp.viewmodel.AuthViewModel

@Composable
fun SignInScreen(
    onNavigateToSignUp: () -> Unit,
    authViewModel: AuthViewModel,
    context: Context,
    onSignInSuccess: () -> Unit
) {
    val logoId =
        if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_text_white) else painterResource(
            id = R.drawable.ic_text
        )
    var email by remember { mutableStateOf("") }
    var password by remember {
        mutableStateOf("")
    }

    var selectedOptionIndex by remember { mutableStateOf(0) }
    var phoneNumber by remember { mutableStateOf("") }


    var passwordVisible by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    val countryPrefixes = listOf("+55", "+1", "+44", "+91")
    var selectedPrefix by remember { mutableStateOf(countryPrefixes[0]) }

    val result by authViewModel.authResult.observeAsState()

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(result) {
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) { padding ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Image(
                        painter = logoId,
                        contentDescription = "Logo",
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    ToggleSwitch(
                        selectedIndex = selectedOptionIndex,
                        onOptionSelected = { selectedOptionIndex = it }
                    )

                    if (selectedOptionIndex == 0) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        )


                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    } else {
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("Phone Number") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            leadingIcon = {
                                Row(
                                    modifier = Modifier
                                        .clickable { expanded = true }
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = selectedPrefix, fontSize = 14.sp)
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = "Select country code"
                                    )
                                }
                            }
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            offset = DpOffset(0.dp, 0.dp),
                            scrollState = rememberScrollState(),
                            properties = PopupProperties(focusable = true)
                        ) {
                            countryPrefixes.forEach { prefix ->
                                DropdownMenuItem(
                                    text = { Text(text = prefix) },
                                    onClick = {
                                        selectedPrefix = prefix
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }


                    Button(
                        onClick = {
                            if (email.isEmpty() && password.isEmpty()) Toast.makeText(
                                context,
                                "Email and Password are Empty",
                                Toast.LENGTH_SHORT
                            ).show()
                            else if (email.isEmpty()) Toast.makeText(
                                context,
                                "Email is Empty",
                                Toast.LENGTH_SHORT
                            ).show()
                            else if (password.isEmpty()) Toast.makeText(
                                context,
                                "Password is Empty",
                                Toast.LENGTH_SHORT
                            ).show()
                            else {
                                authViewModel.login(email.trim(), password)
                                isLoading = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (!isLoading)
                            Text(
                                text = "Sign in",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        else CircularProgressIndicator(
                            modifier = Modifier.width(24.dp),
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Text(
                            text = "Don't have an account?",
                            fontWeight = FontWeight.Light
                        )

                        Text(
                            text = "Sign up.",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .clickable { onNavigateToSignUp() },
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

    @Composable
    fun ToggleSwitch(
        selectedIndex: Int,
        onOptionSelected: (Int) -> Unit,
        modifier: Modifier = Modifier,
        option1: String = "Email",
        option2: String = "Phone Number"
    ) {
        val toggleWidth = 200.dp
        val toggleHeight = 40.dp
        val optionWidth = toggleWidth / 2

        val indicatorOffset by animateDpAsState(targetValue = if (selectedIndex == 0) 0.dp else optionWidth)

        Box(
            modifier = modifier
                .width(toggleWidth)
                .height(toggleHeight)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(optionWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )

            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onOptionSelected(0) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option1,
                        fontSize = 12.sp,
                        fontWeight = if (selectedIndex == 0)
                            FontWeight.Bold
                        else
                            FontWeight.Light,
                        color = if (selectedIndex == 0)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.primary
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onOptionSelected(1) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option2,
                        fontSize = 12.sp,
                        fontWeight = if (selectedIndex == 1)
                            FontWeight.Bold
                        else
                            FontWeight.Light,
                        color = if (selectedIndex == 1)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }