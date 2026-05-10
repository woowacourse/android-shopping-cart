package woowacourse.shopping.ui.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun QuantitySelector(
    count: Int,
    onAdd: () -> Unit,
    onMinus: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = { },
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .width(126.dp)
                .height(42.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(42.dp)
                    .align(Alignment.CenterStart)
                    .clickable(
                        onClick = if (count == 1) onDelete else onMinus,
                    ),
        ) {
            Text(
                text = "-",
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF555555),
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(42.dp)
                    .align(Alignment.Center),
        ) {
            Text(
                text = count.toString(),
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF555555),
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(42.dp)
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = onAdd),
        ) {
            Text(
                text = "+",
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF555555),
            )
        }
    }
}

@Preview
@Composable
fun QuantitySelectorPreview() {
    QuantitySelector(
        count = 3,
        onAdd = { },
        onMinus = { },
        onDelete = { },
    )
}
