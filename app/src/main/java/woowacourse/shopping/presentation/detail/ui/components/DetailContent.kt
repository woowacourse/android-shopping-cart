package woowacourse.shopping.presentation.detail.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.presentation.common.QuantityCounter
import woowacourse.shopping.ui.theme.Gray40
import woowacourse.shopping.util.formattedPrice

@Composable
fun DetailContent(
    imageUrl: String,
    productName: String,
    price: Long,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = productName,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
        )
        Text(
            text = productName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 18.dp),
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Gray40)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = formattedPrice(price),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 26.sp,
                color = Color.Black,
                modifier =
                    Modifier
                        .weight(.5f),
            )
            QuantityCounter(
                quantity = quantity,
                onIncrease = onIncrease,
                onDecrease = onDecrease,
                modifier =
                    Modifier
                        .weight(.5f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreview() {
    DetailContent(
        imageUrl = "",
        productName = "Test",
        price = 10000,
        onDecrease = {},
        onIncrease = {},
        quantity = 3,
    )
}
