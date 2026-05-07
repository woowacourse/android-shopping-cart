package woowacourse.shopping.presentation.productdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.presentation.shopping.component.QuantitySelector
import woowacourse.shopping.util.intFormatter

@Composable
fun ProductPriceAndQuantity(
    price: Int,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${intFormatter(price * quantity)}원",
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.Black,
        )
        QuantitySelector(
            quantity = quantity,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
        )
    }
}

@Preview
@Composable
private fun ProductPriceAndQuantityPreview() {
    ProductPriceAndQuantity(
        price = 99800,
        quantity = 1,
        onIncrease = {},
        onDecrease = {},
    )
}
