@file:Suppress("FunctionName")

package woowacourse.shopping.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductQuantityBox(
    onQuantityPlusClick: () -> Unit,
    onQuantityMinusClick: () -> Unit,
    quantity: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .width(104.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuantityAction(
            text = "-",
            onClick = onQuantityMinusClick,
            modifier =
                Modifier
                    .width(32.dp)
                    .fillMaxHeight(),
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .width(40.dp)
                    .fillMaxHeight(),
        ) {
            Text(
                text = quantity.toString(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        QuantityAction(
            text = "+",
            onClick = onQuantityPlusClick,
            modifier =
                Modifier
                    .width(32.dp)
                    .fillMaxHeight(),
        )
    }
}

@Composable
private fun QuantityAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductQuantityBoxPreview() {
    ProductQuantityBox(
        onQuantityPlusClick = {},
        onQuantityMinusClick = {},
        quantity = 1,
    )
}
