package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun CartCountBadge(
    quantity: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(20.dp)
                .background(
                    color = ShoppingColors.BrandGreen,
                    shape = CircleShape,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = quantity.toString(),
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun CartCountBadgePreview() {
    CartCountBadge(1)
}
