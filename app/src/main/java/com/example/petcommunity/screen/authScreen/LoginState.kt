package com.example.petcommunity.screen.authScreen

sealed class LoginState {
    object Default : LoginState()
    object Failure : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val msg: String) : LoginState()
}
