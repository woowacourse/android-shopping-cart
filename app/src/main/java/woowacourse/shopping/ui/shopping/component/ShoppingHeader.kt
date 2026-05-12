package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import woowacourse.shopping.ui.ShoppingTypography
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun ShoppingHeader(
    modifier: Modifier = Modifier,
    cartQuantity: Int,
    onCartClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = ShoppingColors.Gray5)
                .padding(start = 26.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.shopping_title),
            color = Color.White,
            style = ShoppingTypography.titleMedium,
        )
        Row(
            modifier = Modifier.clickable(onClick = onCartClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = stringResource(R.string.content_description_cart),
                tint = Color.White,
            )
            if (cartQuantity > 0) {
                CartCountBadge(
                    quantity = cartQuantity,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingHeaderPreview() {
    ShoppingHeader(
        cartQuantity = 3,
        onCartClick = {},
    )
}
