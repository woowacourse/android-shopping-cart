package woowacourse.shopping

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun SingleProductItem(
    imageUrl: String,
    title: String,
    money: Int,
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current
    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier
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
        Text(
            title,
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            "${
                DecimalFormat("#,###").format(money)
            }원",
        )
    }
}

@Preview
@Composable
private fun PreviewSingleProduct() {
    SingleProductItem(
        imageUrl = "asd",
        title = "Pet보틀-정사각형 50000ml",
        money = 12000,
        modifier = Modifier
            .width(160.dp)
            .padding(horizontal = 16.dp),
    )
}
