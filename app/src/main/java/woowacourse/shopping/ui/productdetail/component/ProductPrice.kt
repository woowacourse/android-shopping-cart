package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.util.intFormatter

@Composable
fun ProductPrice(
    price: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "가격",
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.Black,
        )
        Text(
            text = "${intFormatter(price)}원",
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.Black,
        )
    }
}

@Preview
@Composable
private fun ProductPricePreview() {
    ProductPrice(100)
}
