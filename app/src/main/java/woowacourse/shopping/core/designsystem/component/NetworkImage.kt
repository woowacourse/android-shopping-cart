package woowacourse.shopping.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R

@Composable
fun NetworkImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RectangleShape,
) {
    val isPreviewMode = LocalInspectionMode.current

    if (isPreviewMode) {
        Image(
            painter = painterResource(R.drawable.preview_image),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier.clip(shape),
        )
    } else {
        AsyncImage(
            model = imageUrl,
            error = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier.clip(shape),
        )
    }
}

@Preview
@Composable
private fun NetworkImagePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        NetworkImage(
            imageUrl = "",
            modifier = Modifier.size(100.dp),
        )

        NetworkImage(
            imageUrl = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(100.dp),
        )

        NetworkImage(
            imageUrl = "",
            shape = CircleShape,
            modifier = Modifier.size(100.dp),
        )
    }
}
