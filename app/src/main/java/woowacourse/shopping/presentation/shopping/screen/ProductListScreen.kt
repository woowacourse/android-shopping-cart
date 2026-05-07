package woowacourse.shopping.presentation.shopping.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.presentation.shopping.component.ProductListContent
import woowacourse.shopping.presentation.shopping.component.ProductListScaffold
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ProductListScreen(
    products: Products,
    hasNextPage: Boolean,
    productQuantity: (Uuid) -> Int,
    onLoadMore: () -> Unit,
    onCartIconClick: () -> Unit,
    onItemClick: (Product) -> Unit,
    onQuantityIncrease: (Product) -> Unit,
    onQuantityDecrease: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    ProductListScaffold(
        onClick = onCartIconClick,
        modifier = modifier,
    ) {
        ProductListContent(
            products = products,
            productQuantity = productQuantity,
            hasNextPage = hasNextPage,
            onLoadMore = onLoadMore,
            onItemClick = onItemClick,
            onQuantityIncrease = onQuantityIncrease,
            onQuantityDecrease = onQuantityDecrease,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductListScreenPreview() {
    ProductListScreen(
        products = Products(emptyList()),
        hasNextPage = true,
        productQuantity = { 0 },
        onLoadMore = {},
        onCartIconClick = {},
        onItemClick = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
    )
}
