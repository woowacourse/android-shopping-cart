package woowacourse.shopping.ui.common.component.cartcontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun QuantityStepper(
    quantity: Int,
    modifier: Modifier = Modifier,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .width(126.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp),
                ).padding(horizontal = 12.dp, vertical = 11.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Remove,
            contentDescription = stringResource(R.string.content_description_decrease_quantity),
            tint = ShoppingColors.Gray4,
            modifier =
                Modifier
                    .size(20.dp)
                    .clickable(onClick = onDecreaseQuantity),
        )

        Text(text = quantity.toString())

        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.content_description_increase_quantity),
            tint = ShoppingColors.Gray4,
            modifier =
                Modifier
                    .size(20.dp)
                    .clickable(onClick = onIncreaseQuantity),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun QuantityStepperPreview() {
    QuantityStepper(
        quantity = 1,
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
