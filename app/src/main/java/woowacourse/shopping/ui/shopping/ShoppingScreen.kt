package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.shopping.component.ProductGroup
import woowacourse.shopping.ui.shopping.component.ShoppingHeader

@Composable
fun ShoppingScreen(
    products: Products,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        ShoppingHeader(onCartClick = onCartClick)

        ProductGroup(
            products = products,
            modifier = Modifier.padding(20.dp),
            onProductClick = onProductClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        products = InMemoryProductRepository.products,
        onCartClick = {},
        onProductClick = {}
    )
}
