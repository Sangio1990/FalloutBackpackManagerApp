import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fallout.inventory.manager.R

val Monofonto = FontFamily(
    Font(R.font.monofonto)
)

// Definiamo i colori del Pip-Boy
val PipBoyGreen = Color(0xFF00FF00)
val PipBoyBlack = Color(0xFF000000)

val PipBoyColors = darkColorScheme(
    primary = PipBoyBlack,
    onPrimary = PipBoyBlack,
    primaryContainer = PipBoyBlack,
    onPrimaryContainer = PipBoyGreen,
    secondary = PipBoyGreen,
    onSecondary = PipBoyBlack,
    secondaryContainer = PipBoyBlack,
    onSecondaryContainer = PipBoyGreen,
    background = PipBoyBlack,
    onBackground = PipBoyGreen,
    surface = PipBoyBlack,
    onSurface = PipBoyGreen,
    error = Color(0xFFCF6679),
    onError = PipBoyBlack
)

val PipBoyTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Monofonto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = PipBoyGreen // Colore del testo
    )
)

@Composable
fun PipBoyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PipBoyColors,
        typography = PipBoyTypography,
        content = content
    )
}
