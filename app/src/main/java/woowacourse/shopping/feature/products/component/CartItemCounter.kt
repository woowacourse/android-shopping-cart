package woowacourse.shopping.feature.products.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.core.designsystem.theme.LightGreen

@Composable
fun CartItemCounter(
    cartItemCount: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .sizeIn(maxWidth = 24.dp)
            .aspectRatio(1f)
            .background(Color.LightGreen, CircleShape)
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Text(
            text = cartItemCount,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.W700
        )
    }
}

@Preview
@Composable
fun CartItemCounterPreview(
) {
    CartItemCounter(
        cartItemCount = "3"
    )
}
