package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import woowacourse.shopping.presentation.theme.buttonColor

@Composable
fun QuantityLabel(
    quantity: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(buttonColor),
    ) {
        Text(
            text = quantity.toString(),
            color = Color.White,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview
@Composable
fun QuantityLabelPreview() {
    QuantityLabel(
        quantity = 2,
    )
}
