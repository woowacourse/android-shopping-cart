package woowacourse.shopping.ui.productList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.repository.product.ProductRepositoryMockImpl
import woowacourse.shopping.ui.productList.component.ProductCardGrid
import woowacourse.shopping.ui.productList.component.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel,
    onCartClick: () -> Unit = {},
    onProductClick: (Product) -> Unit = {},
) {
    val productListState by viewModel.productListUiState.collectAsState()

    Column(modifier = modifier) {
        TopAppBar(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            onClick = onCartClick,
        )

        ProductCardGrid(
            products = productListState.products,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            onProductClick = { product -> onProductClick(product) },
            onMoreClick = { viewModel.moreProducts() },
            currentProductCount = productListState.currentProductCount,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductListScreen(viewModel = ProductListViewModel(ProductRepositoryMockImpl()))
}
