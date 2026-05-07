package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.component.CartHeader
import woowacourse.shopping.ui.cart.component.CartItemBody

@Composable
fun CartScreen(
    items: List<CartItemUiModel>,
    currentPage: Int,
    totalPages: Int,
    showPagination: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDeleteClick: (ProductId) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        CartHeader(onBackClick = onBackClick)

        if (isLoading && items.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.padding(20.dp))
            return
        }

        CartItemBody(
            items = items,
            showPagination = showPagination,
            currentPage = currentPage,
            totalPages = totalPages,
            modifier =
                Modifier
                    .padding(top = 8.dp, start = 18.dp, end = 18.dp)
                    .weight(1f),
            onDeleteClick = onDeleteClick,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CartScreenPreview() {
    val items =
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
        )
    CartScreen(
        items = items,
        currentPage = 1,
        totalPages = 1,
        showPagination = false,
        isLoading = false,
        onBackClick = {},
        onDeleteClick = {},
        onPreviousClick = {},
        onNextClick = {},
    )
}
