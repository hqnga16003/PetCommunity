package com.example.petcommunity.screen.authScreen.login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.R
import com.example.petcommunity.screen.authScreen.ButtonAuth
import com.example.petcommunity.screen.authScreen.ButtonLoginOther
import com.example.petcommunity.screen.authScreen.DividerUILogin
import com.example.petcommunity.screen.authScreen.LoginState
import com.example.petcommunity.screen.authScreen.LoginScreenViewModel
import com.example.petcommunity.screen.authScreen.TextAuth
import com.example.petcommunity.screen.authScreen.TextFieldLogin
import com.example.petcommunity.ui.theme.GreenText

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginScreen(viewModel: LoginScreenViewModel, navController: NavHostController) {

    val loginState = viewModel.loginState.collectAsState()
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (refHeader, refBody, refLoading) = createRefs()

        HeadUILoginScreen(modifier = Modifier
            .constrainAs(refHeader) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .wrapContentSize())

        BodyUILoginScreen(
            modifier = Modifier.constrainAs(refBody) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

            },
            viewModel = viewModel,
            onClickSignUp = { navController.navigate("signUp") },
            onClickSignIn = {
                viewModel.login(viewModel.uiState.email, viewModel.uiState.password)
                    .invokeOnCompletion {
                        if (viewModel.loginState.value == LoginState.Success) {
                            navController.popBackStack()
                            navController.navigate(BottomBarScreen.Home.route)

                        } else {
                            Toast.makeText(context, R.string.login_Error, Toast.LENGTH_LONG).show()
                        }

                    }
            })
        if (loginState.value == LoginState.Loading) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(refLoading) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Log.d("XXX", " LoginState.Loading")
        }


    }
}

@Composable
private fun HeadUILoginScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Welcome Back!", style = MaterialTheme.typography.titleLarge)
        Text(
            modifier = Modifier.alpha(0.5f),
            text = "Please Sign in to continue",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun BodyUILoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit

) {
    val localFocusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        TextFieldLogin(
            "Email Address",
            viewModel.uiState.email,
            KeyboardType.Email, localFocusManager
        ) { email ->
            viewModel.uiState.email = email
        }
        PasswordFieldLogin(
            "Password",
            viewModel.uiState.password,
            KeyboardType.Password, localFocusManager
        ) { password ->
            viewModel.uiState.password = password
        }
        Spacer(modifier = Modifier.height(10.dp))

        TextForgotPassword() {
            // event
        }
        Spacer(modifier = Modifier.height(30.dp))
        ButtonAuth("Sign In", onClick = onClickSignIn)

        Spacer(modifier = Modifier.height(10.dp))
        TextAuth("Don't have an account?", "Sign Up", onClick = onClickSignUp)
        Spacer(modifier = Modifier.height(45.dp))

        DividerUILogin()
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLoginOther()


    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldLogin(
    tile: String,
    input: String,
    typeKeyboardType: KeyboardType,
    localFocusManager: FocusManager,
    onValueChange: (String) -> Unit
) {
    var passordVisibility by remember {
        mutableStateOf(false)
    }

    val icon = if (passordVisibility)
        painterResource(id = R.drawable.ic_visibility_password)
    else
        painterResource(id = R.drawable.icon_visible_password)

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = input,
        label = { Text(text = tile, style = MaterialTheme.typography.titleSmall) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = typeKeyboardType, imeAction = ImeAction.Done
        ),
        placeholder = { Text(text = "Password") },
        trailingIcon = {
            IconButton(modifier = Modifier.size(20.dp), onClick = {
                passordVisibility = !passordVisibility
            }) {
                Icon(painter = icon, contentDescription = "visibility Icon")
            }
        },
        visualTransformation = if (passordVisibility)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() })
    )
}

@Composable
fun TextForgotPassword(onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), Arrangement.End) {
        Text(
            text = "Forgot password?",
            style = MaterialTheme.typography.titleSmall.copy(color = GreenText),
            modifier = Modifier.clickable(onClick = onClick),
        )
    }

}





@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    // LoginScreen(rememberNavController())
}

@Preview
@Composable
private fun HeadUILoginScreenPreView(modifier: Modifier = Modifier) {
    HeadUILoginScreen(modifier = modifier)
}


