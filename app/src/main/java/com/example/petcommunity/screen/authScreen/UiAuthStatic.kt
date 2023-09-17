package com.example.petcommunity.screen.authScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.petcommunity.R
import com.example.petcommunity.ui.theme.GreenText


@OptIn(ExperimentalMaterial3Api::class)
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

@Composable
fun TextAuth(titleFirst:String, titleSecond:String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = titleFirst,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(end = 3.dp)
        )
        Text(
            text = titleSecond,
            style = MaterialTheme.typography.titleSmall.copy(color = GreenText),
            modifier = Modifier.clickable(onClick = onClick)
        )


    }
}

@Composable
fun ButtonAuth(title: String,enabled:Boolean, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenText,
            contentColor = Color.White
        ),
        enabled = enabled
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
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