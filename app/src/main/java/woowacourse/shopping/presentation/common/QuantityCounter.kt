package woowacourse.shopping.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.Gray50

@Composable
fun QuantityCounter(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp),
                ).padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Remove,
            contentDescription = stringResource(R.string.decrease_quantity),
            tint = Gray50,
            modifier =
                Modifier
                    .size(22.dp)
                    .clickable { onDecrease() },
        )
        Text(
            text = quantity.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Gray50,
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.decrease_quantity),
            tint = Gray50,
            modifier =
                Modifier
                    .size(22.dp)
                    .clickable { onIncrease() },
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun QuantityCounterPreview() {
    QuantityCounter(
        quantity = 1,
        onIncrease = {},
        onDecrease = {},
    )
}
