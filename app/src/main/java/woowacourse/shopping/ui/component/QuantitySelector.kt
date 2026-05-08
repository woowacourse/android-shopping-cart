package woowacourse.shopping.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.ui.theme.Gray40

@Composable
fun QuantitySelector(
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    quantity: Int,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.width(126.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Remove,
            contentDescription = "감소",
            tint = if (quantity > 1) Color.Black else Gray40,
            modifier =
                Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(enabled = quantity > 1) {
                        onDecreaseQuantity()
                    },
        )

        Text(
            text = "$quantity",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 14.dp)

        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "증가",
            tint = Color.Black,
            modifier =
                Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onIncreaseQuantity()
                    },
        )
    }
}

@Preview
@Composable
private fun QuantitySelectorPreview() {
    QuantitySelector(
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
        quantity = 1,
    )
}