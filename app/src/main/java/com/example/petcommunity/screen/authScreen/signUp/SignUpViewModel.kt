package com.example.petcommunity.screen.authScreen.signUp

import android.util.Patterns
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcommunity.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
interface SignUpFormState {
    var email: String
    var password: String
    var conformPassword: String
    var emailValidation: Boolean
    var passwordValidation: Boolean
    var conformPasswordValidation: Boolean

}

class MutableSignUpFormState() : SignUpFormState {
    override var email: String by mutableStateOf("")
    override var password: String by mutableStateOf("")
    override var conformPassword: String by mutableStateOf("")
    override var emailValidation: Boolean by mutableStateOf(false)
    override var passwordValidation: Boolean by mutableStateOf(false)
    override var conformPasswordValidation: Boolean by mutableStateOf(false)
}


@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private var _uiStateSignUp = MutableSignUpFormState()
    val uiStateSignUp: SignUpFormState get() = _uiStateSignUp


    fun validateEmail(email: String): Boolean {

        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiStateSignUp.emailValidation = true
            true
        } else{
            _uiStateSignUp.emailValidation = false
            false
        }

    }
    fun validatePassword(password: String): Boolean {
        if (password.length < 6) {
            _uiStateSignUp.passwordValidation = false
            return false
        }
        _uiStateSignUp.passwordValidation = true
        return true
    }
    fun validateRepeatedPassword(password: String, repeatedPassword: String): Boolean {
        if (password != repeatedPassword) {
            _uiStateSignUp.conformPasswordValidation = false
            return false
        }
        _uiStateSignUp.conformPasswordValidation = true
        return true
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        repository.signUp(email, password)
    }


}