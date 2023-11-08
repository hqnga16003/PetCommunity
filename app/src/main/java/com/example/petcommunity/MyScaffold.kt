package com.example.petcommunity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingActionButtonScaffold(navHostController: NavHostController) {
    var isShowBottomSheet by remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    val coroutine = rememberCoroutineScope()
    FloatingActionButton(
        onClick = { navHostController.navigate("createPostScreen") }, shape = CircleShape
    ) {
        androidx.compose.material3.Icon(
            Icons.Default.Add,
            contentDescription = "add",
            tint = Color.White
        )

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarScaffold(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route



    BottomAppBar(
        cutoutShape = CircleShape, modifier = Modifier.clip(
            RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
        )
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val centerVerticalGuide = createGuidelineFromStart(0.5f)
            val (leftMenus, rightMenus) = createRefs()
            Row(
                modifier = Modifier.constrainAs(leftMenus) {
                    start.linkTo(parent.start)
                    end.linkTo(centerVerticalGuide, margin = 32.dp)
                    width = Dimension.fillToConstraints
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                BottomNavigationItem(selected = currentRoute == BottomBarScreen.Home.route,
                    onClick = { navHostController.navigate(BottomBarScreen.Home.route) },
                    icon = {
                        Icon(
                            BottomBarScreen.Home.icon,
                            contentDescription = ""
                        )
                    })
                BottomNavigationItem(selected = currentRoute == BottomBarScreen.Shopping.route,
                    onClick = { navHostController.navigate(BottomBarScreen.Shopping.route) },
                    icon = {
                        Icon(
                            BottomBarScreen.Shopping.icon,
                            contentDescription = ""
                        )
                    })

            }
            Row(
                modifier = Modifier.constrainAs(rightMenus) {
                    start.linkTo(centerVerticalGuide, margin = 32.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                BottomNavigationItem(selected = currentRoute == BottomBarScreen.Profile.route,
                    onClick = { navHostController.navigate(BottomBarScreen.Profile.route) },
                    icon = {
                        Icon(
                            BottomBarScreen.Profile.icon,
                            contentDescription = ""
                        )
                    })
                BottomNavigationItem(selected = currentRoute == BottomBarScreen.Settings.route,
                    onClick = { navHostController.navigate(BottomBarScreen.Settings.route) },
                    icon = {
                        Icon(
                            BottomBarScreen.Settings.icon,
                            contentDescription = ""
                        )
                    })
            }
        }


    }
}


//
//if (isShowBottomSheet) {
//    ModalBottomSheet(modifier = Modifier.fillMaxSize(),
//        onDismissRequest = {
//            coroutine.launch { sheetState.show() }.invokeOnCompletion {
//                isShowBottomSheet = false
//
//            }
//        },
//        sheetState = sheetState,
//
//        ) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            CreatePostScreen()
//        }
//    }
//}
