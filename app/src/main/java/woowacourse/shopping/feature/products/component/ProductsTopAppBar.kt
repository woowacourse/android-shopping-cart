package woowacourse.shopping.feature.products.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.core.designsystem.component.BasicTopAppBar

@Composable
fun ProductsTopAppBar(
    onClick: () -> Unit,
    formattedCartItemCount: String,
    modifier: Modifier = Modifier,
) {
    BasicTopAppBar(
        modifier = modifier,
    ) {
        Text(
            text = "Shopping",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_cart_24),
                contentDescription = "장바구니 이동 버튼",
                tint = Color.White,
            )
            if (formattedCartItemCount.toInt() > 0) {
                CartItemCounter(
                    cartItemCount = formattedCartItemCount,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductsTopAppBarPreview() {
    ProductsTopAppBar(
        onClick = {},
        formattedCartItemCount = "3",
    )
}
