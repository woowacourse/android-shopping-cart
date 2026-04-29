package woowacourse.shopping.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.component.topbar.NavigateUpTopBar

@Composable
fun CartScreen(
    cartStateHolder: CartStateHolder,
) {
    val cartItems = cartStateHolder.cartItems

    Scaffold(
        topBar = {
            NavigateUpTopBar(
                title = "Cart",
                onNavigateUp = { },
            )
        },
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 24.dp, horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(
                items = cartItems,
                key = { it.product.id },
            ) {
                CartItemCard(
                    imageUrl = it.product.imageUrl,
                    name = it.product.name,
                    price = it.product.price,
                    onDelete = {
                        cartStateHolder.deleteCartItem(it.product.id)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(cartStateHolder = CartStateHolder())
}
