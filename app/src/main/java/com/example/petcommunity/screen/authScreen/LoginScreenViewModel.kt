package com.example.petcommunity.screen.authScreen

import android.content.Context
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcommunity.R
import com.example.petcommunity.data.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@Stable
interface LoginScreenState {
    var email: String
    var password: String
}

private class MutableLoginScreenUiState : LoginScreenState {
    override var email: String by mutableStateOf("hqnga1604@gmail.com")
    override var password: String by mutableStateOf("Quangnga160302@")

}



@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _uiState = MutableLoginScreenUiState()
    val uiState: LoginScreenState get() = _uiState


    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
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
            _loginState.value = LoginState.Error(R.string.login_Error.toString())
        }

    }



    private fun saveSharePreferences(context: Context) {
        val sharedPref = context.getSharedPreferences(
            R.string.preference_myAccount.toString(),
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.apply {
            putString("email", currentUser!!.email.toString())
            putString("uid", currentUser!!.uid)
            apply()

        }
    }

    fun logOut() {
        repository.logOut()
        _loginState.value = LoginState.Initial
    }
}