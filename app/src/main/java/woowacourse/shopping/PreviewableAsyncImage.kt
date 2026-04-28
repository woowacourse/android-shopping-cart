package woowacourse.shopping

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage

@Composable
fun PreviewableAsyncImage(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current
    AsyncImage(
        model = imageUrl,
        contentDescription = title,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        placeholder = if (isPreview) {
            painterResource(R.drawable.ic_launcher_background)
        } else {
            null
        },
        error = if (isPreview) {
            painterResource(R.drawable.ic_launcher_background)
        } else {
            null
        },
    )
}
