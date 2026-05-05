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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.productdetail.component.MintButton
import woowacourse.shopping.ui.shopping.component.ProductItem
import woowacourse.shopping.ui.shopping.component.ProductListTopAppBar
import woowacourse.shopping.ui.shopping.state.rememberProductListState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ProductListScreen(
    products: Products,
    onCartClick: () -> Unit,
    onProductClick: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberProductListState()

    Scaffold(
        topBar = {
            ProductListTopAppBar(
                onClick = {
                    onCartClick()
                },
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
                items(state.visibleProducts(products)) { product ->
                    ProductItem(
                        product = product,
                        onClick = { onProductClick(product.productId) },
                    )
                }
                if (products.hasNextPage(currentPageIndex = state.currentPageIndex)) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        MintButton(
                            onClick = {
                                state.increase()
                            },
                            text = stringResource(R.string.see_more),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductListScreenPreview() {
    val packageName = LocalContext.current.packageName

    ProductListScreen(
        products = Products(ProductFixture.productList(packageName)),
        onCartClick = {},
        onProductClick = {},
    )
}
