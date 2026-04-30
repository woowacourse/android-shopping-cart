package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.shopping.component.ShoppingBody
import woowacourse.shopping.ui.shopping.component.ShoppingHeader

@Composable
fun ShoppingScreen(
    products: Products,
    hasNext: Boolean,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShoppingHeader(onCartClick = onCartClick)

        ShoppingBody(
            products = products,
            showMoreButton = hasNext,
            modifier = Modifier
                .padding(20.dp)
                .weight(1f),
            onProductClick = onProductClick,
            onMoreClick = onMoreClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        products = InMemoryProductRepository.products,
        hasNext = true,
        onCartClick = {},
        onProductClick = {},
        onMoreClick = {}
    )
}
