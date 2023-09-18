package com.example.petcommunity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Favorite,
        BottomBarScreen.Profile,
        BottomBarScreen.Settings
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { it ->
            BottomNavigationItem(
                selected = currentRoute == it.route,
                onClick = { navController.navigate(it.route) },
                icon = { Icon(it.icon, contentDescription = "") },
                label = { Text(text = it.title) },
                unselectedContentColor = Color.White.copy(0.4f),
                selectedContentColor = Color.White
            )
        }


    }
}

@Composable
fun FloatingActionButtonScaffold() {
    FloatingActionButton(
        onClick = { /*TODO*/ }, shape = CircleShape
    ) {
        androidx.compose.material3.Icon(
            Icons.Default.Add,
            contentDescription = "add",
            tint = Color.White
        )
    }
}

@Composable
fun BottomBarScaffold(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                    onClick = { navController.navigate(BottomBarScreen.Home.route) },
                    icon = {
                        Icon(
                            BottomBarScreen.Home.icon,
                            contentDescription = ""
                        )
                    })
                BottomNavigationItem(selected = currentRoute == BottomBarScreen.Favorite.route,
                    onClick = { navController.navigate(BottomBarScreen.Favorite.route) },
                    icon = {
                        Icon(
                            BottomBarScreen.Favorite.icon,
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
                    onClick = { navController.navigate(BottomBarScreen.Profile.route) },
                    icon = {
                        Icon(
                            BottomBarScreen.Profile.icon,
                            contentDescription = ""
                        )
                    })

                BottomNavigationItem(selected = currentRoute == BottomBarScreen.Settings.route,
                    onClick = { navController.navigate(BottomBarScreen.Settings.route) },
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