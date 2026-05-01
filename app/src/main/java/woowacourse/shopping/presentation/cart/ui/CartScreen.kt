package woowacourse.shopping.presentation.cart.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.presentation.cart.model.CartItemUiModel
import woowacourse.shopping.presentation.common.ShoppingAppBar
import woowacourse.shopping.presentation.common.model.ProductUiModel

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    val activity = LocalActivity.current
    val state = remember { CartStateHolder() }

    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable {
                                    activity?.finish()
                                },
                    )
                    Spacer(modifier = Modifier.width(21.dp))
                    Text(
                        text = "Cart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                    )
                },
            )
        },
        bottomBar = {
            if (state.getTotalCartSize() > 5) {
                CartPageSection(
                    page = state.page + 1,
                    onNext = { state.nextPage() },
                    onPrevious = { state.previousPage() },
                    isCanMoveNext = state.isCanMoveNext,
                )
            }
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        CartContent(
            onDeleteItem = { state.deleteItem(it) },
            cartItems = state.currentCartItems.toImmutableList(),
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        )
    }
}

@Composable
private fun CartContent(
    onDeleteItem: (String) -> Unit,
    cartItems: ImmutableList<CartItemUiModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(
            items = cartItems,
            key = { it.product.id },
        ) { item ->
            val product = item.product
            CartCard(
                productName = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
                onDeleteItem = {
                    onDeleteItem(product.id)
                },
            )
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen()
}

@Preview(showBackground = true)
@Composable
private fun CartContentPreview() {
    CartContent(
        onDeleteItem = {},
        cartItems =
            listOf(
                CartItemUiModel(
                    product =
                        ProductUiModel(
                            id = "1",
                            name = "커피",
                            imageUrl = "",
                            price = 1000,
                        ),
                    quantity = 1,
                    totalPrice = 1000,
                ),
                CartItemUiModel(
                    product =
                        ProductUiModel(
                            id = "2",
                            name = "커피",
                            imageUrl = "",
                            price = 1000,
                        ),
                    quantity = 1,
                    totalPrice = 1000,
                ),
            ).toImmutableList(),
    )
}
