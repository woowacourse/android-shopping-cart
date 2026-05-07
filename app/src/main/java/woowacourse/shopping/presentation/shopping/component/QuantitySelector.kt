package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun QuantitySelector(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .size(126.dp, 42.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier =
                Modifier
                    .width(13.dp)
                    .clickable { onDecrease() }
                    .align(Alignment.CenterVertically),
        ) {
            Image(
                painter = painterResource(id = R.drawable.minus),
                contentDescription = "수량 감소",
            )
        }

        Text(
            text = quantity.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.align(Alignment.CenterVertically),
        )

        Box(
            modifier =
                Modifier
                    .width(13.dp)
                    .clickable { onIncrease() }
                    .align(Alignment.CenterVertically),
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "수량 증가",
            )
        }
    }
}

@Preview
@Composable
fun QuantitySelectorPreview() {
    QuantitySelector(
        quantity = 1,
        onDecrease = {},
        onIncrease = {},
    )
}
