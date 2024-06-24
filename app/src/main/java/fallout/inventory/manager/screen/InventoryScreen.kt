package fallout.inventory.manager.screen

import PipBoyGreen
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fallout.inventory.manager.MyApp
import fallout.inventory.manager.data.Item
import fallout.inventory.manager.data.ItemParser
import fallout.inventory.manager.data.ListTestData

@Composable
fun InventoryScreen() {
    val activity = (LocalContext.current as? Activity)
    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            val data = ListTestData().fakeData()
            if (data.size == 0)
                Text(text = "Inventory Screen")
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
                            textAlign = TextAlign.End
                        )
                        Text(
                            "150",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            "Peso Totale:",
                            modifier = Modifier.weight(2f),
                            textAlign = TextAlign.End
                        )
                        Text(
                            "150",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
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
                    ItemList(data)

                }
            }
        }
    }
}


@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
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

@Composable
fun ItemList(itemList: List<Item>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(itemList.size) { item ->
            ItemCard(item = itemList[item])
        }
    }
}

@Preview
@Composable
fun InventoryScreenPreview() {
    MyApp()
}

