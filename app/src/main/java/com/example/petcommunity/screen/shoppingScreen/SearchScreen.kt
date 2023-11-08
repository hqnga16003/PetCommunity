import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.petcommunity.BottomBarScaffold
import com.example.petcommunity.FloatingActionButtonScaffold

@Composable
fun SearchScreen(navHostController: NavHostController) {
    val listAddress = listOf<Address>(
        Address(
            "Hồ Chí Minh",
            "https://hnm.1cdn.vn/2023/01/06/hanoimoi.com.vn-uploads-images-lequyen-2023-01-_1-3-.jpg"
        ),
        Address(
            "Đà Nẵng",
            "https://hodadi.s3.amazonaws.com/production/blogs/pictures/000/000/028/original/du-lich-da-nang.jpg?1501897690"
        ),
        Address(
            "Hà Nội",
            "https://owa.bestprice.vn/images/destinations/uploads/trung-tam-thanh-pho-ha-noi-603da1f235b38.jpg"
        ),
        Address(
            "Cần thơ",
            "https://media.vneconomy.vn/images/upload/2023/07/14/anh-thanh-pho-2.jpg"
        )
    )
    androidx.compose.material.Scaffold(
        floatingActionButton = { FloatingActionButtonScaffold(navHostController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBarScaffold(navHostController)
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
        ) {
            items(listAddress){
                it-> ItemAddressView(it){
                    navHostController.navigate("search/"+it.title)
                }
            }
        }
    }
}


data class Address(val title: String, val img: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAddressView(address: Address, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(90.dp)
            .width(120.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = onClick

    ) {
        Box() {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = address.img,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = address.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}