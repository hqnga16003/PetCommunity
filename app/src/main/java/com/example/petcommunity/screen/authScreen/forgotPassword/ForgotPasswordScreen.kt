package com.example.petcommunity.screen.authScreen.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.petcommunity.screen.authScreen.ButtonAuth
import com.example.petcommunity.screen.authScreen.TextFieldEmail

@Composable
fun ForgotPasswordScreen() {
    val viewModel: ForgotPasswordScreenViewModel = hiltViewModel()
    val uistate = viewModel.uiState
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val localFocusManager = LocalFocusManager.current

        TextFieldEmail(
            tile = "Email",
            input = uistate.email,
            typeKeyboardType = KeyboardType.Email,
            localFocusManager = localFocusManager,
            onValueChange = { email -> uistate.email = email }
        )

        ButtonAuth(title = "Send Email", enabled = true) {
            viewModel.sendPasswordResetEmail(viewModel.uiState.email)
        }
    }
}