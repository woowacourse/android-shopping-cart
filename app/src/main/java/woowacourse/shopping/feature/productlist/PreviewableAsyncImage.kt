package woowacourse.shopping.feature.productlist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import woowacourse.shopping.R

@Composable
fun PreviewableAsyncImage(
    imageUrl: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth(),
        error =
        painterResource(R.drawable.ic_launcher_background),
    )
}

@Preview
@Composable
private fun PreviewableAsyncImagePreview() {
    PreviewableAsyncImage(
        imageUrl = "",
        description = "더미 이미지",
    )
}
