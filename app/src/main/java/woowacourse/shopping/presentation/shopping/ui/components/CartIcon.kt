package woowacourse.shopping.presentation.shopping.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import woowacourse.shopping.ui.theme.Green40

@Composable
fun CartIcon(
    quantity: Int,
    isShowCartQuantityIcon: Boolean,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = stringResource(R.string.shopping_cart),
            tint = Color.White,
            modifier =
                Modifier
                    .size(24.dp)
                    .clickable { onNavigateToCart() },
        )
        if (isShowCartQuantityIcon) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Green40),
            ) {
                Text(
                    text = quantity.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartIconPreview() {
    AndroidshoppingTheme {
        CartIcon(
            quantity = 1,
            onNavigateToCart = {},
            isShowCartQuantityIcon = true,
        )
    }
}
