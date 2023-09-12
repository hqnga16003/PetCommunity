package com.example.petcommunity.screen.authScreen

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcommunity.data.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@Stable
interface LoginScreenUiState {
    var email: String
    var password: String
}

private class MutableLoginScreenUiState : LoginScreenUiState {
    override var email: String by mutableStateOf("2051010201nga@ou.edu.vn")
    override var password: String by mutableStateOf("Quangnga160302@")

}

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: AuthRepository) : ViewModel() {
    private val _uiState = MutableLoginScreenUiState()
    val uiState: LoginScreenUiState get() = _uiState

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Default)
    val loginState get() = _loginState

    val currentUser: FirebaseUser?
        get() = repository.currenUser

    init {
        if (repository.currenUser != null) {
            _loginState.value = LoginState.Success
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginState.Loading
        repository.login(email, password)
        if (currentUser != null) {
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Failure
        }

    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginState.Loading
        repository.signUp(email, password)
        _loginState.value = LoginState.Success
    }


    fun logOut() {
        repository.logOut()
        _loginState.value = LoginState.Default
    }
}