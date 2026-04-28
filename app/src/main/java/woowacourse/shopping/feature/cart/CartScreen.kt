package woowacourse.shopping.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.feature.cart.component.CartItem
import woowacourse.shopping.feature.cart.component.CartTopAppBar
import woowacourse.shopping.feature.cart.model.CartInfo

@Composable
fun CartScreen(
    cartItems: ImmutableList<CartInfo>,
    onBackClick: () -> Unit,
    onCartDeleteClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CartTopAppBar(onClick = onBackClick)

        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 18.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            cartItems.forEach { cartInfo ->
                CartItem(
                    productName = cartInfo.productName,
                    productUrl = cartInfo.productImageUrl,
                    price = cartInfo.price,
                    onClick = { onCartDeleteClick(cartInfo.id) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        cartItems = List(5) { CartInfo.PREVIEW }.toImmutableList(),
        onBackClick = {},
        onCartDeleteClick = {}
    )
}
