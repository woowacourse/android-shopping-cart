package woowacourse.shopping.ui.productlist

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SingleProductItem(
    imageUrl: String,
    title: String,
    money: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        PreviewableAsyncImage(imageUrl = imageUrl, description = title)
        Spacer(modifier = Modifier.height(9.dp))
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
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            color = Color(0xff555555),
        )
        Spacer(modifier = Modifier.height(12.dp))
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
