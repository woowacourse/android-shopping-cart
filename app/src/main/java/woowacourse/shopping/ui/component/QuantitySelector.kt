package woowacourse.shopping.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Black,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .width(126.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White),
    ) {
        IconButton(
            onClick = { onQuantityChange((quantity - 1).coerceAtLeast(0)) },
            modifier = Modifier.size(42.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "감소",
                tint = contentColor,
                modifier = Modifier.size(14.dp),
            )
        }

        Text(
            text = "$quantity",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 14.dp),
        )

        IconButton(
            onClick = { onQuantityChange(quantity + 1) },
            modifier = Modifier.size(42.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "증가",
                tint = contentColor,
                modifier =
                    Modifier.size(14.dp),
            )
        }
    }
}

@Preview
@Composable
private fun QuantitySelectorPreview() {
    QuantitySelector(
        quantity = 1,
        onQuantityChange = {},
    )
}
