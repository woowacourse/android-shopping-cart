package woowacourse.shopping.ui.component.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun ProductImage(
    imageUri: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = imageUri,
        contentDescription = "상품 사진",
        modifier = modifier,
    )
}
