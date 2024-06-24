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
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
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
import fallout.inventory.manager.screen.AmmoScreen
import fallout.inventory.manager.screen.InventoryScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    PipBoyTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Navigation(navController, innerPadding)
        }
    }
}

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
fun TopBar() {
    val activity = (LocalContext.current as? Activity)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        navigationIcon = {
            Button(
                modifier = Modifier.border(width = 2.dp, color = PipBoyGreen, shape = CircleShape),
                onClick = {
                    Toast.makeText(activity?.baseContext, "Aggiunto Oggetto", Toast.LENGTH_SHORT)
                        .show()
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
                "Genstione Inventario",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",
                    tint = colorScheme.secondary
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )

}

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController,
        startDestination = "inventory",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("inventory") {
            BackBehavior()
            InventoryScreen()
        }
        composable("ammo") {
            BackBehavior()
            AmmoScreen()
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MyApp()
}