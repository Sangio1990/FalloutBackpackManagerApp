package fallout.inventory.manager.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fallout.inventory.manager.MyApp
import fallout.inventory.manager.data.Item
import fallout.inventory.manager.data.ListTestData

@Composable
fun AmmoScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val data = mutableListOf<Item>()
        if (data.size == 0)
            Text(
                "Sei senza munizioni, le pistole non sparano aria!",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )
    }
}

@Preview
@Composable
fun AmmoScreenPreview() {
    MyApp(startingDestination="Ammo")
}