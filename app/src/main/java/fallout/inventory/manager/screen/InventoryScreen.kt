package fallout.inventory.manager.screen

import PipBoyBlack
import PipBoyBlacklight
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fallout.inventory.manager.MyApp
import fallout.inventory.manager.data.Data
import fallout.inventory.manager.data.DataUtility
import fallout.inventory.manager.data.Item
import fallout.inventory.manager.data.ItemViewModel
import fallout.inventory.manager.data.ListTestData

@Composable
fun InventoryScreen(fake: Boolean = false, navController: NavHostController) {
    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            var data: List<Item>
            if (fake) {
                data = ListTestData().fakeItemData()
            } else {
                data = DataUtility.loadItems()
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
                        Text(
                            "Peso Consentito:",
                            modifier = Modifier.weight(2f),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = DataUtility.getMaxWeight().toString(),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            "Peso Totale:",
                            modifier = Modifier.weight(3f),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = DataUtility.getTotalWeight().toString(),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Nome", modifier = Modifier.weight(3f))
                        Text(
                            text = "Valore",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = "Quant",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = "Peso",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = "Tot",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                    ItemList(data, navController = navController)

                }
            }
        }
    }
}


@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
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
            Text(
                text = item.value.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
            Text(
                text = item.quantity.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
            Text(
                text = item.weight.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
            Text(
                text = (item.weight * item.quantity).toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemList(
    itemList: List<Item>,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        items(itemList.size) { item ->
            ItemCard(
                item = itemList[item],
                modifier = Modifier
                    .background(PipBoyBlacklight)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            ItemViewModel.selectedItem = itemList[item]
                            ItemViewModel.isItem = true
                            navController.navigate("modifyItem")
                        })
            )
        }
    }
}

@Preview
@Composable
fun InventoryScreenPreview() {
    DataUtility.data = Data(5, 10, mutableListOf(), mutableListOf())
    MyApp(fake = true)
}

