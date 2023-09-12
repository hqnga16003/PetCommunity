package com.example.petcommunity.screen.authScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.petcommunity.ui.theme.GreenText

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavHostController) {

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
                            navController.navigate(BottomBarScreen.Home.route)
                        } else {
                            Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_LONG).show()
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
    viewModel: LoginViewModel,
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
        ButtonSignIn("Sign In", onClickSignIn = onClickSignIn)
        Spacer(modifier = Modifier.height(10.dp))
        TextSignUp(onClickSignUp = onClickSignUp)
        Spacer(modifier = Modifier.height(45.dp))

        DividerUILogin()
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLoginOther()


    }


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TextFieldLogin(
    tile: String,
    input: String,
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
        keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) })
    )
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

@Composable
fun ButtonSignIn(title: String, onClickSignIn: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClickSignIn,
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenText,
            contentColor = Color.White
        )
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun TextSignUp(onClickSignUp: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = "Don't have an account?",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(end = 3.dp)
        )
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.titleSmall.copy(color = GreenText),
            modifier = Modifier.clickable(onClick = onClickSignUp)
        )


    }
}

@Composable
fun DividerUILogin() {
    val currencyWith = LocalConfiguration.current.screenWidthDp;
    val dividerWith = currencyWith / 2
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            modifier = Modifier
                .width(dividerWith.dp)
                .weight(1f),
            thickness = 1.dp,
            color = Color(0xFFA2A3A3)
        )
        Text(
            text = "Or", style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray),
            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
        )
        Divider(
            modifier = Modifier
                .width(dividerWith.dp)
                .weight(1f),
            thickness = 1.dp,
            color = Color(0xFFA2A3A3)
        )
    }
}

@Composable
fun ItemButtonLoginOther(tile: String, img: Int) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
    ) {

        Image(
            painter = painterResource(id = img),
            contentDescription = tile,
            modifier = Modifier
                .size(30.dp)
                .clip(shape = CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = tile,
            style = MaterialTheme.typography.titleSmall.copy(color = Color.Black)
        )

    }
}

@Composable
fun ButtonLoginOther() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ItemButtonLoginOther(tile = "Continue with Google", img = R.drawable.ic_login_google)
        ItemButtonLoginOther(tile = "Continue with Facebook", img = R.drawable.icon_login_facebook)
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

//
//@Composable
//fun LoginScreen(navController: NavHostController) {
//    val viewModel: AuthViewModel = hiltViewModel()
//    val loginFlow = viewModel.loginFlow.collectAsState()
//    val uiState = viewModel.uiState.collectAsState()
//
//    Box(modifier = Modifier.fillMaxSize())
//    {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Spacer(modifier = Modifier.height(20.dp))
//            HeadUILoginScreen()
//            Spacer(modifier = Modifier.height(100.dp))
//            BodyUILoginScreen(
//                viewModel = viewModel,
//                uiState = uiState,
//                navController = navController
//            )
//
//            loginFlow?.value.let {
//                when (it) {
//                    is Resource.Failure -> {
//                        Toast.makeText(
//                            LocalContext.current,
//                            it.exception.message,
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                    is Resource.Loading -> {
//                        Box(
//                            contentAlignment = Alignment.Center,
//                            modifier = Modifier.fillMaxSize()
//                        ) {
//                            Text(text = "12312sdfsdfsd",)
//                            CircularProgressIndicator()
//                        }
//
//                    }
//
//                    is Resource.Success -> {
//                        LaunchedEffect(Unit) {
//                            navController.navigate(BottomBarScreen.Home.route) {
//                                popUpTo(BottomBarScreen.Home.route) { inclusive = true }
//                            }
//                        }
//
//                    }
//
//                    else -> {}
//                }
//            }
//        }
//    }
//
//}

