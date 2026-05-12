package woowacourse.shopping.ui.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.ui.component.ShoppingAppBar
import woowacourse.shopping.ui.model.CartItemUiModel
import woowacourse.shopping.ui.model.ProductUiModel

@Composable
fun CartScreen(
    uiState: CartUiState,
    onBackClick: () -> Unit,
    onDeleteItem: (String) -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    onQuantityChange: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                                .clickable { onBackClick() },
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
            if (uiState.totalCartSize > 5) {
                CartPageSection(
                    page = uiState.page + 1,
                    onNext = { onNextPage() },
                    onPrevious = { onPreviousPage() },
                    isCanMoveNext = uiState.isCanMoveNext,
                    modifier = Modifier.navigationBarsPadding(),
                )
            }
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        CartContent(
            onQuantityChange = onQuantityChange,
            onDeleteItem = {
                onDeleteItem(it)
            },
            cartItems = uiState.items,
            errorMessage = uiState.errorMessage,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        )
    }
}

@Composable
private fun CartContent(
    onQuantityChange: (String, Int) -> Unit,
    onDeleteItem: (String) -> Unit,
    cartItems: ImmutableList<CartItemUiModel>,
    errorMessage: String?,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        if (errorMessage != null) {
            item {
                Text(
                    text = errorMessage,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }

        items(
            items = cartItems,
            key = { it.product.id },
        ) { item ->
            val product = item.product
            CartCard(
                productName = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
                quantity = item.quantity,
                onQuantityChange = { quantity ->
                    onQuantityChange(product.id, quantity)
                },
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
    CartScreen(
        uiState = CartUiState(),
        onBackClick = {},
        onDeleteItem = {},
        onNextPage = {},
        onPreviousPage = {},
        onQuantityChange = { _, _ -> },
    )
}

@Preview(showBackground = true)
@Composable
private fun CartContentPreview() {
    CartContent(
        onDeleteItem = {},
        onQuantityChange = { _, _ -> },
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
        errorMessage = null,
    )
}
