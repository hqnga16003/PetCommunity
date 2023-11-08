package com.example.petcommunity.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.petcommunity.R
import com.example.petcommunity.model.Pet


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemDogCard(dog: Pet, onItemClicked: (dog: Pet) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemClicked(dog) }),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            GlideImage(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = dog.image,
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = dog.name.toString(),
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildString {
                        append(dog.age)
                        append("yrs | ")
                        append(dog.gender)
                    },
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    style = MaterialTheme.typography.caption
                )

                Row(verticalAlignment = Alignment.Bottom) {


                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp, 16.dp),
                        tint = Color.Red
                    )

                    Text(
                        text = dog.location.toString(),
                        modifier = Modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                        color = MaterialTheme.colors.surface,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    GenderTag(dog.gender.toString())
                }
                Icon(Icons.Default.Favorite, tint = Color.Red, contentDescription = "")
                Text(text = dog.favorite.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
            }

        }
    }
}


@Composable
fun GenderTag(name: String) {
    val color = if (name == "Male") R.color.blue else R.color.red
    ChipView(gender = name, colorResource = colorResource(id = color))
}

@Composable
fun ChipView(gender: String, colorResource: Color) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource.copy(.08f))
        ) {
            Text(
                text = gender, modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 6.dp),
                style = MaterialTheme.typography.caption,
                color = colorResource
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

    }
}