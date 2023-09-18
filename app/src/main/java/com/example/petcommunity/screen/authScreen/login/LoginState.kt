package com.example.petcommunity.screen.authScreen.login

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val msg: String) : LoginState()
}
