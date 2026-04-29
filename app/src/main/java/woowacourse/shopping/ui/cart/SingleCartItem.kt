package woowacourse.shopping.ui.cart

import android.R.attr.end
import android.R.attr.text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.productlist.PreviewableAsyncImage

@Composable
fun SingleCartItem(
    imageUrl: String,
    title: String,
    price: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .border(
                shape = RoundedCornerShape(4.dp),
                color = Color(0xffaaaaaa),
                width = 1.dp,
            )
            .padding(horizontal = 12.dp, vertical = 18.dp),

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.W700,
                fontSize = 18.sp,
            )
            Icon(
                painter = painterResource(R.drawable.close),
                contentDescription = stringResource(R.string.close_description),
                tint = Color(0xffaaaaaa),
                modifier = Modifier.padding(end = 10.dp),
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PreviewableAsyncImage(
                imageUrl = imageUrl,
                description = title,
                modifier = Modifier
                    .size(width = 136.dp, height = 72.dp),
            )
            Text(
                text = price,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color(0xff555555),
            )
        }
    }
}

@Preview
@Composable
private fun CartItemPreview() {
    SingleCartItem(
        imageUrl = "",
        title = "프리뷰",
        price = "1,000원",
    )
}
