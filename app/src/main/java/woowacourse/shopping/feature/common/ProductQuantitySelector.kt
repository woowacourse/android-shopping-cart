package woowacourse.shopping.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun ProductQuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    decreaseEnabled: Boolean = quantity > 0,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White),
    ) {
        IconButton(
            onClick = onDecrease,
            enabled = decreaseEnabled,
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(R.string.decrease_product_description),
                modifier = Modifier
                    .size(20.dp),
                tint = when (quantity > 0) {
                    true -> Color(0xff555555)
                    false -> Color(0xff999999)
                },
            )
        }

        Text(quantity.toString(), fontSize = 22.sp, color = Color(0xff555555))
        IconButton(onClick = onIncrease) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_product_description),
                modifier = Modifier
                    .size(20.dp),
                tint = Color(0xff555555),
            )
        }
    }
}

@Preview
@Composable
private fun ProductQuantitySelectorPreview() {
    ProductQuantitySelector(
        quantity = 0,
        onIncrease = {},
        onDecrease = {},
    )
}
