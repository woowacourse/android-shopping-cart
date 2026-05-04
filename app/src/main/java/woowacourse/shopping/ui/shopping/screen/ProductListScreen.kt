package woowacourse.shopping.ui.shopping.screen

import android.content.Intent
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
import woowacourse.shopping.PRODUCT_ID_EXTRA_KEY
import woowacourse.shopping.ProductDetailActivity
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.PageRequest
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.SHOPPING_PAGE_SIZE
import woowacourse.shopping.domain.toPage
import woowacourse.shopping.ui.productdetail.component.mintButton
import woowacourse.shopping.ui.shopping.component.productItem
import woowacourse.shopping.ui.shopping.component.productListTopAppBar
import woowacourse.shopping.ui.shopping.state.rememberProductListState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ProductListScreen(
    products: Products,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberProductListState()
    val context = LocalContext.current
    val visibleCount = (state.currentPageIndex + 1) * SHOPPING_PAGE_SIZE
    val visibleProducts = products.products.toPage(PageRequest(0, visibleCount))

    Scaffold(
        topBar = {
            productListTopAppBar(
                onClick = {
                    onClick()
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
                items(visibleProducts.items) { product ->
                    productItem(
                        product = product,
                        onClick = {
                            val intent =
                                Intent(context, ProductDetailActivity::class.java).apply {
                                    putExtra(PRODUCT_ID_EXTRA_KEY, product.productId.toString())
                                }
                            context.startActivity(intent)
                        },
                    )
                }
                if (products.hasNextPage(currentPageIndex = state.currentPageIndex)) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        mintButton(
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

@Preview
@Composable
private fun ProductListScreenPreview() {
    val packageName = LocalContext.current.packageName

    ProductListScreen(
        products = Products(ProductFixture.productList(packageName)),
        onClick = {}
    )
}
