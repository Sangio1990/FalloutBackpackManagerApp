package fallout.inventory.manager.screen

import PipBoyGreen
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fallout.inventory.manager.MyApp
import pipBoyTextFieldColors

@Composable
fun NewAmmoScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "NUOVE MUNIZIONI",
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
        var name by remember { mutableStateOf("") }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Calibro") },
            placeholder = { Text("") },
            modifier = Modifier.fillMaxWidth(),
            colors = pipBoyTextFieldColors()
        )

        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {

        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, color = PipBoyGreen, shape = CircleShape),
            onClick = {
                TODO()
            }
        ) {
            Text("Aggiungi Munizioni")
        }
    }
}

@Preview
@Composable
fun NewAmmoScreenPreview() {
    MyApp(startingDestination = "newAmmo")
}