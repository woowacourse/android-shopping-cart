package woowacourse.shopping.ui.shopping.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.ui.productdetail.component.ActionButton
import woowacourse.shopping.ui.shopping.component.ProductItem
import woowacourse.shopping.ui.shopping.component.ProductListTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: Products,
    onCartIconClick: () -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentPageIndex by rememberSaveable { mutableStateOf(0) }
    val visibleProducts = ProductRepositoryImpl.getPagingProducts(page = currentPageIndex).productItems

    Scaffold(
        topBar = {
            ProductListTopAppBar(
                onClick = onCartIconClick,
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(visibleProducts) { product ->
                    ProductItem(
                        product = product,
                        onClick = onItemClick,
                    )
                }
                if (ProductRepositoryImpl.hasNextPage(currentPage = currentPageIndex)) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ActionButton(
                            onClick = {
                                currentPageIndex++
                            },
                            text = "더보기",
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductListScreenPreview() {
    ProductListScreen(
        products = Products(ProductFixture.productList),
        onCartIconClick = {},
        onItemClick = {},
    )
}
