package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import woowacourse.shopping.R

@Composable
fun PreviewableAsyncImage(
    imageUrl: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current
    AsyncImage(
        model = imageUrl,
        contentDescription = description,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        placeholder =
        painterResource(R.drawable.ic_launcher_background),
        error =
        if (isPreview) {
            painterResource(R.drawable.ic_launcher_background)
        } else {
            null
        },
    )
}
