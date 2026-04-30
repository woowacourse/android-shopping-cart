@file:Suppress("FunctionName")

package woowacourse.shopping.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CustomColorScheme =
    lightColorScheme(
        primary = Mint40,
        secondary = PurpleGrey80,
        tertiary = Pink80,
        surfaceContainer = Gray80,
        onSurface = White,
        onSurfaceVariant = Gray80,
        onBackground = Black,
        background = White
    )

@Composable
fun AndroidShoppingTheme(content: @Composable () -> Unit) {
    val colorScheme = CustomColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}