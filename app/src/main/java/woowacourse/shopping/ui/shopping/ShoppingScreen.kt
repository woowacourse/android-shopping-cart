package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.shopping.component.ShoppingBody
import woowacourse.shopping.ui.shopping.component.ShoppingHeader

private const val PAGE_SIZE = 20

@Composable
fun ShoppingScreen(
    productRepo: ProductRepository,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    var visibleCount: Int by rememberSaveable { mutableIntStateOf(PAGE_SIZE) }
    val state = rememberShoppingScreenState(productRepo = productRepo, visibleCount = visibleCount)

    ShoppingScreen(
        products = state.visibleProducts,
        hasNext = state.hasNext,
        modifier = modifier,
        onCartClick = onCartClick,
        onProductClick = onProductClick,
        onMoreClick = {
            visibleCount = minOf(visibleCount + PAGE_SIZE, state.sizeInRepo)
            state.loadProducts(visibleCount)
        }
    )
}

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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShoppingHeader(onCartClick = onCartClick)

        ShoppingBody(
            products = products,
            showMoreButton = hasNext,
            modifier =
                Modifier
                    .padding(20.dp)
                    .weight(1f),
            onProductClick = onProductClick,
            onMoreClick = onMoreClick,
        )
    }
}

@Composable
private fun rememberShoppingScreenState(
    productRepo: ProductRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    visibleCount: Int = PAGE_SIZE
): ShoppingScreenState {
    return remember {
        ShoppingScreenState(
            productRepo = productRepo,
            coroutineScope = coroutineScope,
            visibleCount = visibleCount
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
        onMoreClick = {},
    )
}
