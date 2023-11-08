package com.example.petcommunity.screen.createPostScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.R
import com.example.petcommunity.SharePreferences
import com.example.petcommunity.model.Pet
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Objects


@SuppressLint("NewApi")
@Composable
fun CreatePostScreen(viewModel: CreatePostScreenViewModel, navHostController: NavHostController) {

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val file = context.createImageFile();
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context), context.packageName + ".provider", file
    )
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
        } else {

        }
    }
    val openCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.uiState.capturedImageUri = uri
        }
    }

    val pickPhoto =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                viewModel.uiState.capturedImageUri = it
            }
        }
    val localFocusManager = LocalFocusManager.current

    var gender:String? = null;
    var address:String? = null;
    var category:String? = null;

    val db = FirebaseFirestore.getInstance()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.background))
        .padding(start = 16.dp, end = 16.dp), topBar = {
        TopBarCreatePostScreen() {
            val uId = SharePreferences.getSharePreferences(context)
            val pet = Pet(id = "",
                name = category,
                age = viewModel.uiState.petAge,
                gender = gender,
                color = viewModel.uiState.petColor,
                weight = viewModel.uiState.petWeight,
                location = address,
                content = viewModel.uiState.content,
                phoneNumber = viewModel.uiState.phoneNumber,
                uid = uId,
                createAt = LocalDate.now().toString(),
                check = false,
                userCheck = true,
                favorite = "0"
            )
            viewModel.addPost(pet, uId, viewModel.uiState.capturedImageUri)
            navHostController.navigate(BottomBarScreen.Home.route)

        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
                Divider(
                    modifier = Modifier.fillMaxWidth(), thickness = 2.dp, color = Color(0xFFA2A3A3)
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (viewModel.uiState.capturedImageUri.path?.isNotEmpty() == true) {
                    ImagePet(rememberAsyncImagePainter(model = viewModel.uiState.capturedImageUri))
                } else {
                    ImagePet(painterResource(id = R.drawable.img_default))
                }
                ButtonPhoto(onClickGetPhoto = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                        context, android.Manifest.permission.READ_MEDIA_IMAGES
                    )
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    } else {
                        requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                    }
                }, onClickOpenCamera = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                        context, android.Manifest.permission.CAMERA
                    )
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        openCamera.launch(uri)
                    } else {
                        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                })
                Spacer(modifier = Modifier.height(10.dp))
                TextFieldInput(
                    tile = "Nội dung",
                    input = viewModel.uiState.content,
                    typeKeyboardType = KeyboardType.Text,
                    localFocusManager = localFocusManager,
                ) {
                    viewModel.uiState.content = it
                }
                Spacer(modifier = Modifier.height(10.dp))
                ExposedDropdownMenuBoxCategory(){
                    category = it;
                }


                Spacer(modifier = Modifier.height(10.dp))

                ExposedDropdownMenuBoxGender(){
                    gender = it
                }
                Spacer(modifier = Modifier.height(10.dp))

                TextFieldInput(
                    tile = "Tháng tuổi",
                    input = viewModel.uiState.petAge,
                    typeKeyboardType = KeyboardType.Number,
                    localFocusManager = localFocusManager,
                ) {
                    viewModel.uiState.content = it
                }
                Spacer(modifier = Modifier.height(10.dp))

                TextFieldInput(
                    tile = "Màu",
                    input = viewModel.uiState.petColor,
                    typeKeyboardType = KeyboardType.Text,
                    localFocusManager = localFocusManager,
                ) {
                    viewModel.uiState.petColor = it
                }
                Spacer(modifier = Modifier.height(10.dp))

                TextFieldInput(
                    tile = "Cân nặng",
                    input = viewModel.uiState.petWeight,
                    typeKeyboardType = KeyboardType.Number,
                    localFocusManager = localFocusManager,
                ) {
                    viewModel.uiState.content = it
                }
                Spacer(modifier = Modifier.height(10.dp))

                ExposedDropdownMenuBoxAddress(){
                    address = it
                }
                Spacer(modifier = Modifier.height(10.dp))

                TextFieldInput(
                    tile = "Số điện thoại",
                    input = viewModel.uiState.phoneNumber,
                    typeKeyboardType = KeyboardType.Number,
                    localFocusManager = localFocusManager,
                ) {
                    viewModel.uiState.address = it
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxGender(onClick: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
            TextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isExpanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { false }) {
                DropdownMenuItem(
                    text = { Text(text = "Male") },
                    onClick = {
                        gender = "Male"
                        isExpanded = false
                        onClick(gender)
                    })
                DropdownMenuItem(
                    text = { Text(text = "Female") },
                    onClick = {
                        gender = "Female"
                        isExpanded = false
                        onClick(gender)

                    })
                DropdownMenuItem(
                    text = { Text(text = "Other") },
                    onClick = {
                        gender = "Other"
                        isExpanded = false
                        onClick(gender)

                    })
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxAddress(onClick: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var address by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
            TextField(
                value = address,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isExpanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { false }) {
                DropdownMenuItem(
                    text = { Text(text = "Hà Nội") },
                    onClick = {
                        address = "Hà Nội"
                        isExpanded = false
                        onClick(address)
                    })
                DropdownMenuItem(
                    text = { Text(text = "Hồ Chí Minh") },
                    onClick = {
                        address = "Hồ Chí Minh"
                        isExpanded = false
                        onClick(address)

                    })
                DropdownMenuItem(
                    text = { Text(text = "Đà Nẵng") },
                    onClick = {
                        address = "Đà Nẵng"
                        isExpanded = false
                        onClick(address)

                    })
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxCategory(onClick: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
            TextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isExpanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { false }) {
                DropdownMenuItem(
                    text = { Text(text = "Dog") },
                    onClick = {
                        category = "Dog"
                        isExpanded = false
                        onClick(category)
                    })
                DropdownMenuItem(
                    text = { Text(text = "Cat") },
                    onClick = {
                        category = "Cat"
                        isExpanded = false
                        onClick(category)

                    })

            }
        }
    }
}



@Composable
private fun TextFieldInput(
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
fun ImagePet(painter: Painter) {
    val height = LocalConfiguration.current.screenHeightDp
    val heightImage = height * 0.4
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightImage.dp),
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ButtonPhoto(onClickGetPhoto: () -> Unit, onClickOpenCamera: () -> Unit) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        ButtonTakePhoto("", modifier = Modifier.weight(1f), onClickOpenCamera)

        ItemButtonGetPhoto("", modifier = Modifier.weight(1f), onClickGetPhoto)
    }
}

@Composable
fun ButtonTakePhoto(title: String, modifier: Modifier = Modifier, onClickOpenCamera: () -> Unit) {
    Button(modifier = modifier, onClick = onClickOpenCamera) {
        Icon(
            painterResource(id = R.drawable.ic_open_camera),
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )
        Text(text = title)
    }
}

@Composable
fun ItemButtonGetPhoto(title: String, modifier: Modifier = Modifier, onClickGetPhoto: () -> Unit) {
    Button(modifier = modifier, onClick = onClickGetPhoto) {
        Icon(Icons.Default.Add, contentDescription = "")
        Text(text = title)
    }
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy-MM-dd").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(imageFileName, ".jpg", externalCacheDir)
    return image
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCreatePostScreen(onClick: () -> Unit) {
    TopAppBar(title = {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")

                Button(
                    onClick = onClick,
                ) {
                    Text(
                        text = "Đăng",
                        style = androidx.compose.material.MaterialTheme.typography.body2,
                    )
                }
            }
        }
    })
}

