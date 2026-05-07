package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import woowacourse.shopping.AppContainer
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ProductPrice(
    price: Int,
    quantity: Int,
    increaseQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.price_format, price),
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.Black,
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .size(126.dp, 42.dp)
                .padding(horizontal = 15.dp, vertical = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(13.dp, 19.dp)
                    .clickable { if (quantity > 1) decreaseQuantity() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.minus_icon),
                    contentDescription = "감소 버튼"
                )
            }
            Text(
                text = quantity.toString(),
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .size(13.dp, 19.dp)
                    .clickable { increaseQuantity() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.plus_icon),
                    contentDescription = "증가 버튼"
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductPricePreview() {
    ProductPrice(
        price = 10000,
        quantity = 1,
        increaseQuantity = {},
        decreaseQuantity = {}
    )
}
