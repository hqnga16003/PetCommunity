package com.example.petcommunity.screen.authScreen.signUp


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.screen.authScreen.ButtonAuth
import com.example.petcommunity.screen.authScreen.ButtonLoginOther
import com.example.petcommunity.screen.authScreen.DividerUILogin
import com.example.petcommunity.screen.authScreen.TextAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.uiStateSignUp

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (refHeader, refBody, refLoading) = createRefs()

        HeadUIRegisterScreen(modifier = Modifier
            .constrainAs(refHeader) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .wrapContentSize())


        BodyUIRegisterScreen(modifier = Modifier.constrainAs(refBody) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)

        }, viewModel = viewModel, onClickSignIn = {
            navController.navigate("login")
        }, onClickSignUp = {
            viewModel.signUp(viewModel.uiStateSignUp.email, viewModel.uiStateSignUp.password)
                .invokeOnCompletion {
                    navController.navigate(BottomBarScreen.Home.route)
                }
        })

    }

}


@Composable
private fun HeadUIRegisterScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Welcome To Pet Community!", style = MaterialTheme.typography.titleLarge)
        Text(
            modifier = Modifier.alpha(0.5f),
            text = "Please Sign up to continue",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun BodyUIRegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    onClickSignIn: () -> Unit,
    onClickSignUp: () -> Unit
) {

    val localFocusManager = LocalFocusManager.current
    Column(modifier = modifier) {
        TextFieldSignUp(
            "Email Address",
            viewModel.uiStateSignUp.email,
            isError = !viewModel.validateEmail(viewModel.uiStateSignUp.email),
            KeyboardType.Email,
            localFocusManager
        ) { email ->
            viewModel.uiStateSignUp.email = email
        }

        PasswordFieldSignUp(
            tile = "Password",
            input = viewModel.uiStateSignUp.password,
            isError = false,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) })

        ) { password ->
            viewModel.uiStateSignUp.password = password
        }
        PasswordFieldSignUp(
            tile = "Conform Password",
            input = viewModel.uiStateSignUp.conformPassword,
            isError = false,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() })

        ) { conformPassword ->
            viewModel.uiStateSignUp.conformPassword = conformPassword
        }
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(!viewModel.validatePassword(viewModel.uiStateSignUp.password)) {
            ConditionRow("Minimun 6 characters", false)

        }
        AnimatedVisibility(
            visible = !viewModel.validateRepeatedPassword(
                viewModel.uiStateSignUp.password, viewModel.uiStateSignUp.conformPassword
            )
        ) {
            ConditionRow(
                "Conform password", false
            )
        }


        ButtonAuth(
            "Sign Up",
            viewModel.uiStateSignUp.emailValidation && viewModel.uiStateSignUp.passwordValidation && viewModel.uiStateSignUp.conformPasswordValidation,
            onClick = onClickSignUp
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextAuth("Already have an account?", "Sign In", onClick = onClickSignIn)
        Spacer(modifier = Modifier.height(45.dp))
        DividerUILogin()
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLoginOther()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldSignUp(
    tile: String,
    input: String,
    isError: Boolean,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = input,
        label = { Text(text = tile, style = MaterialTheme.typography.titleSmall) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = imeAction
        ),
        placeholder = { Text(text = "Password") },
        keyboardActions = keyboardActions,
        visualTransformation = PasswordVisualTransformation(),
        isError = isError
    )

}

@Composable
fun ConditionRow(condition: String, check: Boolean) {
    val color by animateColorAsState(
        targetValue = if (check) Color.Green else Color.Red, label = "text color"
    )
    val icon = if (check) Icons.Default.Check else Icons.Default.Close
    Row {
        Icon(icon, contentDescription = "Status icon")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = condition, color = color)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSignUp(
    tile: String,
    input: String,
    isError: Boolean,
    typeKeyboardType: KeyboardType,
    localFocusManager: FocusManager,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = input,
        label = { Text(text = tile, style = MaterialTheme.typography.titleSmall) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = typeKeyboardType, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        isError = isError
    )
}