package woowacourse.shopping.ui.component.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantityCounter(
    quantity: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
                .background(Color.White)
                .clickable { onMinusClick() },
        ) {
            Text(
                text = "-",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF555555),
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(Color.White),
        ) {
            Text(
                text = quantity.toString(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF555555),
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                .background(Color.White)
                .clickable { onPlusClick() },
        ) {
            Text(
                text = "+",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF555555),
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Preview
@Composable
private fun QuantityCounterPreview() {
    QuantityCounter(
        quantity = 1,
        onPlusClick = {},
        onMinusClick = {},
    )
}
