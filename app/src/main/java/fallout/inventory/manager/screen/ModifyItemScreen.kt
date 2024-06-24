package fallout.inventory.manager.screen

import PipBoyGreen
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fallout.inventory.manager.MyApp
import fallout.inventory.manager.data.DataUtility
import fallout.inventory.manager.data.DataUtility.TAG
import fallout.inventory.manager.data.Item
import fallout.inventory.manager.data.ItemViewModel
import pipBoyTextFieldColors

@Composable
fun ModifyItemScreen(navHostController: NavHostController) {
    val activity = (LocalContext.current as? Activity)
    var item = ItemViewModel.selectedItem!!

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = if (ItemViewModel.isItem) "MODIFICA OGGETTO" else "MODIFICA MUNIZIONE",
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        var name by remember { mutableStateOf(item.name.takeIf { !it.isNullOrEmpty() } ?: "") }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(if (ItemViewModel.isItem) "Nome dell'oggetto" else "Calibro della munizione") },
            placeholder = { Text("") },
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(5.dp),
            colors = pipBoyTextFieldColors(),
        )

        var value by remember {
            mutableStateOf(item?.value.toString().takeIf { it.isNotEmpty() } ?: "")
        }
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Valore") },
            placeholder = { Text("") },
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(5.dp),
            colors = pipBoyTextFieldColors(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        var quantity by remember {
            mutableStateOf(
                item?.quantity.toString().takeIf { it.isNotEmpty() } ?: "")
        }
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantità") },
            placeholder = { Text("") },
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(5.dp),
            colors = pipBoyTextFieldColors(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        var weight by remember {
            mutableStateOf(
                item?.weight.toString().takeIf { !it.isNullOrEmpty() } ?: "")
        }
        if (ItemViewModel.isItem) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Peso") },
                placeholder = { Text("") },
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = pipBoyTextFieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        } else {
            weight = 0.0.toString()
        }

        var description by remember {
            mutableStateOf(item?.description.takeIf { !it.isNullOrEmpty() } ?: "")
        }
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrizione") },
            placeholder = { Text("") },
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp),
            colors = pipBoyTextFieldColors(),
            maxLines = 10,
            minLines = 2
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 5.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, color = PipBoyGreen, shape = CircleShape)
                    .padding(2.dp),
                onClick = {
                    if (name.isNullOrEmpty() || quantity.isNullOrEmpty()) {
                        Toast.makeText(
                            activity?.baseContext,
                            "Nome, quantità e peso sono obbligatori",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        try {
                            val qta = quantity.toInt()
                            try {
                                val wgt = weight.toDouble()
                                try {
                                    val vle = value.toInt()
                                    val oldItem = item
                                    item = Item(name, description, qta, wgt, vle)
                                    if (ItemViewModel.isItem) {
                                        DataUtility.removeItem(oldItem)
                                        DataUtility.addItem(item)
                                    } else {
                                        DataUtility.removeAmmo(oldItem)
                                        DataUtility.addNewAmmo(item)
                                    }
                                    navHostController.popBackStack()
                                } catch (e: Exception) {
                                    Log.e(TAG, "Failed to convert value, " + e.message)
                                    Toast.makeText(
                                        activity?.baseContext,
                                        "Valore deve essere un numero intero",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Failed to convert weight, " + e.message)
                                Toast.makeText(
                                    activity?.baseContext,
                                    "Peso deve essere un numero",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Failed to convert quantity, " + e.message)
                            Toast.makeText(
                                activity?.baseContext,
                                "Quantità deve essere un numero intero",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            ) {
                Text("Modifica Oggetto")
            }
        }


        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text("SEI SICURO?")
                },
                text = {
                    Text("Sei sicuro di voler cancellare l'oggetto?\nL'operazione non è reversibile")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if(ItemViewModel.isItem) {
                                DataUtility.removeItem(item)
                            } else {
                                DataUtility.removeAmmo(item)
                            }
                            showDialog = false
                            navHostController.popBackStack()
                        }
                    ) {
                        Text("Conferma")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Annulla")
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, color = PipBoyGreen, shape = CircleShape)
                    .padding(2.dp),
                onClick = {
                    showDialog = true
                }
            ) {
                Text("Cancella Oggetto")
            }
        }
    }
}

@Preview
@Composable
fun ModifyItemScreenPreview() {
    ItemViewModel.selectedItem = Item("Test Preview", "Descrizione Preview", 1, 1.0, 1)
    MyApp(startingDestination = "modifyItem")
}