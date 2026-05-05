package woowacourse.shopping.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import woowacourse.shopping.R

@Composable
fun AppImage(
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
        SubcomposeAsyncImage(
            model = imageUrl,
            loading = {
                Box(
                    modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            error = {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "이미지 로딩 중 오류가 발생했습니다.",
                    contentScale = ContentScale.Inside,
                )
            },
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier.clip(shape),
        )
    }
}

@Preview
@Composable
private fun AppImagePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AppImage(
            imageUrl = "",
            modifier = Modifier.size(100.dp),
        )

        AppImage(
            imageUrl = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(100.dp),
        )

        AppImage(
            imageUrl = "",
            shape = CircleShape,
            modifier = Modifier.size(100.dp),
        )
    }
}
