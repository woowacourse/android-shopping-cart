package woowacourse.shopping.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun ProductAsyncImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "상품 이미지",
        placeholder = rememberVectorPainter(Icons.Default.CloudSync),
        error = rememberVectorPainter(Icons.Default.CloudOff),
        fallback = rememberVectorPainter(Icons.Default.CloudOff),
        contentScale = contentScale,
        modifier = modifier,
    )
}
