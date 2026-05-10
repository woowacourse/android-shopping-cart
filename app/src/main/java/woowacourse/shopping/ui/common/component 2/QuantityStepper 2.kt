package woowacourse.shopping.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.ProductId

@Composable
fun QuantityStepper(
    quantity: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onIncreaseQuantity: (ProductId) -> Unit,
    onDecreaseQuantity: (ProductId) -> Unit,
) {
    Box (
        onClick = onClick,
        modifier = modifier.width(126.dp).height(42.dp),
        shape = RoundedCornerShape(5.dp),
        contentPadding = PaddingValues(11.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF555555),
        ),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "수량 감소",
                tint = Color(0xFF555555),
                modifier = Modifier.size(20.dp),
            )

            Text(
                text = quantity.toString()
            )

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "수량 증가",
                tint = Color(0xFF555555),
                modifier = Modifier.size(20.dp)
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun QuantityStepperPreview() {
    QuantityStepper(
        quantity = 1,
    ) {}
}