package fallout.inventory.manager.screen

import PipBoyBlack
import PipBoyBlacklight
import PipBoyGreen
import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fallout.inventory.manager.MyApp
import fallout.inventory.manager.R
import fallout.inventory.manager.data.Data
import fallout.inventory.manager.data.DataUtility
import fallout.inventory.manager.data.Item
import fallout.inventory.manager.data.ItemViewModel
import fallout.inventory.manager.data.ListTestData

@Composable
fun AmmoScreen(fake: Boolean = false, navController: NavHostController) {
    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            var data: List<Item>
            if (fake) {
                data = ListTestData().fakeAmmoData()
            } else {
                data = DataUtility.loadAmmo()
            }
            if (data.size == 0)
                Text(
                    "Inventario vuoto, spendili quei tappi!",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                )
            else {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Calibro", modifier = Modifier.weight(3f))
                        Text(
                            text = "-5",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "-1",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "+1",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "+5",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Tot",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                    AmmoList(data, navController = navController)

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AmmoList(
    ammoList: List<Item>,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        items(ammoList.size) { ammo ->
            AmmoCard(
                item = ammoList[ammo],
                modifier = Modifier
                    .background(PipBoyBlacklight)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            ItemViewModel.selectedItem = ammoList[ammo]
                            ItemViewModel.isItem = false
                            navController.navigate("modifyItem")
                        })
            )
        }
    }
}

@Composable
fun AmmoCard(item: Item, modifier: Modifier = Modifier) {
    var ammo by remember { mutableStateOf(item.quantity.toString()) }
    Card(
        modifier = modifier
            .background(PipBoyBlack)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(PipBoyBlacklight)
                .padding(5.dp)
        ) {
            Text(text = item.name, modifier = Modifier.weight(3f))
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "-5",
                tint = PipBoyGreen,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = {
                            if (ammo.toInt() >= 5) {
                                ammo = (ammo.toInt()-5).toString()
                                DataUtility.changeAmmoQuantity(item, -5)
                            }
                        }
                    )
            )
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                contentDescription = "-1",
                tint = PipBoyGreen,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = {
                            if (ammo.toInt() >= 1) {
                                ammo = (ammo.toInt()-1).toString()
                                DataUtility.changeAmmoQuantity(item, -1)
                            }
                        }
                    )
            )
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = "+1",
                tint = PipBoyGreen,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = {
                            ammo = (ammo.toInt()+1).toString()
                            DataUtility.changeAmmoQuantity(item, 1)
                        }
                    )
            )
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = "+5",
                tint = PipBoyGreen,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = {
                            ammo = (ammo.toInt()+5).toString()
                            DataUtility.changeAmmoQuantity(item, 5)
                        }
                    )
            )

            Text(
                text = ammo,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}


@Preview
@Composable
fun AmmoScreenPreview() {
    DataUtility.data = Data(5, 10, mutableListOf(), mutableListOf())
    MyApp(startingDestination = "Ammo", fake = true)
}