package fallout.inventory.manager.screen

import PipBoyGreen
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fallout.inventory.manager.MyApp
import fallout.inventory.manager.data.Data
import fallout.inventory.manager.data.DataUtility
import pipBoyTextFieldColors

@Composable
fun ExtraScreen() {
    val activity = (LocalContext.current as? Activity)

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Text(
                text = "Extra Screen",
                modifier = Modifier.weight(1f)
            )
            var str by remember { mutableStateOf(DataUtility.getSTR().toString()) }
            OutlinedTextField(
                value = str,
                onValueChange = { str = it },
                label = { Text("Forza") },
                placeholder = { Text("") },
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
                    .padding(5.dp)
                    .wrapContentHeight(),
                colors = pipBoyTextFieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            var mod by remember { mutableStateOf(DataUtility.getModifier().toString()) }
            OutlinedTextField(
                value = mod,
                onValueChange = { mod = it },
                label = { Text("Modificatore") },
                placeholder = { Text("") },
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
                    .padding(5.dp)
                    .wrapContentHeight(),
                colors = pipBoyTextFieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, color = PipBoyGreen, shape = CircleShape)
                    .weight(1f)
                    .padding(15.dp)
                    .wrapContentHeight(),
                onClick = {
                    if (mod.isNullOrEmpty() || str.isNullOrEmpty()) {
                        Toast.makeText(
                            activity?.baseContext,
                            "Forza e modificatore sono obbligatori",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        try {
                            val tmpStr = str.toInt()
                            try {
                                val tempMod = mod.toInt()
                                DataUtility.setStrength(tmpStr)
                                DataUtility.setModifier(tempMod)
                                Toast.makeText(
                                    activity?.baseContext,
                                    "Dati salvati",
                                    Toast.LENGTH_SHORT
                                ).show()
                                activity?.onBackPressed()

                            } catch (e: Exception) {
                                Toast.makeText(
                                    activity?.baseContext,
                                    "Il modificatore deve essere un numero intero",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                activity?.baseContext,
                                "La forza deve essere un numero intero",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            ) {
                Text("Salva")
            }
        }
    )
}


@Preview
@Composable
fun ExtraScreenPreview() {
    DataUtility.data = Data(5, 10, mutableListOf(), mutableListOf())
    MyApp(startingDestination = "extra")
}