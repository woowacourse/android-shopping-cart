package woowacourse.shopping.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantityControlButton(
    count: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        ControlButton(
            roundedCornerShape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp),
            onClick = onDecreaseClick,
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "제거 아이콘",
                tint = Color.DarkGray,
            )
        }

        Box(
            modifier =
                Modifier
                    .size(42.dp)
                    .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = count.toString(),
                color = Color.DarkGray,
                fontWeight = FontWeight.W500,
                fontSize = 22.sp,
            )
        }

        ControlButton(
            roundedCornerShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
            onClick = onIncreaseClick,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "추가 아이콘",
                tint = Color.DarkGray,
            )
        }
    }
}

@Composable
private fun ControlButton(
    roundedCornerShape: RoundedCornerShape,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(42.dp)
                .clickable(onClick = onClick)
                .clip(roundedCornerShape)
                .background(color = Color.White),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Preview
@Composable
private fun QuantityControlButtonPreview() {
    QuantityControlButton(
        count = 2,
        onIncreaseClick = {},
        onDecreaseClick = {},
    )
}
