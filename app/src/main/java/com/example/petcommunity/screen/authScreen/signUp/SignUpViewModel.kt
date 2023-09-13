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

}

class MutableSignUpFormState : SignUpFormState {
    override var email: String by mutableStateOf("")
    override var password: String by mutableStateOf("")
    override var conformPassword: String by mutableStateOf("")

}

data class ValidationResult(val successful: Boolean, val errorMessage: String? = null)
@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthRepository)  : ViewModel() {
    private var _uiStateSignUp = MutableSignUpFormState()
    val uiStateSignUp: SignUpFormState get() = _uiStateSignUp

    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "The email can't be blank")
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(successful = false, errorMessage = "That's not a valid email")

        }
        return ValidationResult(successful = true, errorMessage = "That's not a valid email")

    }

    fun validatePassword(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 6 characters"
            )
        }

        return ValidationResult(successful = true, errorMessage = "That's not a valid email")
    }

    fun validateRepeatedPassword(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(successful = true)

    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        repository.signUp(email, password)
    }



    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

}