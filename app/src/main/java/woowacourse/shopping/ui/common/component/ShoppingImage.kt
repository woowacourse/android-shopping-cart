package woowacourse.shopping.ui.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun ShoppingImage(
    model: Any?,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        placeholder = rememberVectorPainter(Icons.Default.Image),
        error = rememberVectorPainter(Icons.Default.WarningAmber),
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}
