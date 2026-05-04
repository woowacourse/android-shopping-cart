package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.CartItemUiModel

@Composable
fun CartItemBody(
    items: List<CartItemUiModel>,
    showPagination: Boolean,
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    onDeleteClick: (ProductId) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = items, key = { it.productId }) { item ->
            CartItemUnit(
                item = item,
                onDeleteClick = { onDeleteClick(item.productId) },
            )
        }

        if (showPagination) {
            item {
                Spacer(modifier = Modifier.height(15.dp))

                CartPaging(
                    currentPage = currentPage,
                    totalPages = totalPages,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CartItemBodyPreview() {
    CartItemBody(
        items =
            listOf(
                CartItemUiModel(
                    productId = InMemoryProductRepository.APPLE.id,
                    name = InMemoryProductRepository.APPLE.name,
                    imageUrl = InMemoryProductRepository.APPLE.imageUrl,
                    price = InMemoryProductRepository.APPLE.price.value,
                    quantity = 2,
                ),
                CartItemUiModel(
                    productId = InMemoryProductRepository.BBOYAMI.id,
                    name = InMemoryProductRepository.BBOYAMI.name,
                    imageUrl = InMemoryProductRepository.BBOYAMI.imageUrl,
                    price = InMemoryProductRepository.BBOYAMI.price.value,
                    quantity = 1,
                ),
            ),
        onDeleteClick = {},
        showPagination = true,
        currentPage = 1,
        totalPages = 5,
        onPreviousClick = {},
        onNextClick = {},
    )
}
