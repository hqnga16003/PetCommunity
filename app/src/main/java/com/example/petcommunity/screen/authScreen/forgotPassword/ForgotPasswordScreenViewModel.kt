package com.example.petcommunity.screen.authScreen.forgotPassword

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.petcommunity.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@Stable
interface ForgotPasswordScreenState {
    var email: String
}
private class MutableForgotPasswordScreenState : ForgotPasswordScreenState {
    override var email: String by mutableStateOf("")

}

@HiltViewModel
class ForgotPasswordScreenViewModel @Inject constructor(val repository: AuthRepository):ViewModel() {
    private val _uiState = MutableForgotPasswordScreenState()
    val uiState: ForgotPasswordScreenState get() = _uiState
     fun sendPasswordResetEmail(email:String){
         Log.d("XXX","12312321")
        repository.sendPasswordResetEmail(email)
    }

}