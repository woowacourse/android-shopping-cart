package woowacourse.shopping.features.generalComponent

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun QuantityControlRow(
    quantity: Int,
    minusEnabled: Boolean,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            enabled = minusEnabled,
            onClick = onDecrementClick,
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "개수 감소 버튼"
            )
        }
        Text(
            text = quantity.toString(),
            fontSize = 22.sp
        )
        IconButton(
            onClick = onIncrementClick
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "개수 증가 버튼"
            )
        }
    }
}
