package fallout.inventory.manager

import PipBoyGreen
import PipBoyTheme
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fallout.inventory.manager.data.Data
import fallout.inventory.manager.data.DataUtility
import fallout.inventory.manager.data.GsonUtility
import fallout.inventory.manager.screen.AmmoScreen
import fallout.inventory.manager.screen.ExtraScreen
import fallout.inventory.manager.screen.InventoryScreen
import fallout.inventory.manager.screen.ModifyItemScreen
import fallout.inventory.manager.screen.NewItemScreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GsonUtility.gsonInit(applicationContext)
        try {
            DataUtility.dataInit()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Errore nel recuperare i dati", Toast.LENGTH_LONG).show()
            finish()
        }
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp(fake: Boolean = false, startingDestination: String = "inventory") {

    val navController = rememberNavController()
    DataUtility.setSelectedView(startingDestination)

    PipBoyTheme {
        Scaffold(
            topBar = { TopBar(navController) },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Navigation(navController, innerPadding, fake = fake, startingDestination = startingDestination)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun BackBehavior() {
    var doubleBackToExitPressedOnce = false
    val activity = (LocalContext.current as? Activity)
    BackHandler(enabled = true) {
        if (doubleBackToExitPressedOnce) {
            // Uscita dall'applicazione
            if (activity != null) {
                activity.finishAffinity()
            }
        } else {
            // Mostra il messaggio di avviso
            doubleBackToExitPressedOnce = true
            Toast.makeText(activity?.baseContext, "Premi nuovamente per uscire", Toast.LENGTH_SHORT)
                .show()

            // Resetta il flag dopo un certo periodo di tempo
            GlobalScope.launch {
                delay(2000L)
                doubleBackToExitPressedOnce = false
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf("inv") }
    NavigationBar(
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    ImageVector.vectorResource(R.drawable.baseline_backpack_24),
                    contentDescription = "Inventario",
                    tint = colorScheme.secondary
                )
            },
            label = { Text("Inventario") },
            selected = selectedItem == "inv",
            onClick = {
                selectedItem = "inv"
                navController.navigate("inventory")
            },
            modifier = Modifier.border(
                width = 2.dp,
                color = if (selectedItem == "inv") colorScheme.secondary else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    ImageVector.vectorResource(R.drawable.bullet_svgrepo_com),
                    contentDescription = "Munizioni",
                    tint = colorScheme.secondary
                )
            },
            label = { Text("Munizioni") },
            selected = selectedItem == "ammo",
            onClick = {
                selectedItem = "ammo"
                navController.navigate("ammo")
            },
            modifier = Modifier.border(
                width = 2.dp,
                color = if (selectedItem == "ammo") colorScheme.secondary else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        navigationIcon = {
            //Add Button
            Button(
                modifier = Modifier.border(width = 2.dp, color = PipBoyGreen, shape = CircleShape),
                onClick = {
                    if (DataUtility.getSelectedView().equals("inventory")){
                    navController.navigate("newItem")}
                    else if (DataUtility.getSelectedView().equals("ammo")) {
                        navController.navigate("newAmmo")
                    }
                }) {
                Icon(Icons.Filled.Add, "Add Item", tint = colorScheme.secondary)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Gestione Inventario",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },

        actions = {
            IconButton(onClick = { navController.navigate("extra") }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Extra Things",
                    tint = colorScheme.secondary
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )

}

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    fake: Boolean = false,
    startingDestination: String
) {
    NavHost(
        navController,
        startDestination = startingDestination,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("inventory") {
            DataUtility.setSelectedView("inventory")
            BackBehavior()
            InventoryScreen(fake = fake, navController)
        }
        composable("ammo") {
            DataUtility.setSelectedView("ammo")
            BackBehavior()
            AmmoScreen(fake = fake, navController)
        }
        composable("newItem"){
            DataUtility.setSelectedView("newItem")
            NewItemScreen(navController, true)
        }
        composable("newAmmo"){
            DataUtility.setSelectedView("newAmmo")
            NewItemScreen(navController, false)
        }
        composable("modifyItem"){
            DataUtility.setSelectedView("modifyItem")
            ModifyItemScreen(navController)
        }
        composable("extra") {
            DataUtility.setSelectedView("extra")
            ExtraScreen()
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    DataUtility.data = Data(5, 10, mutableListOf(), mutableListOf())
    MyApp(fake = true, startingDestination = "inventory")
}