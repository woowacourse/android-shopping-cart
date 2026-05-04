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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.CartActivity
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.PageRequest
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.SHOPPING_PAGE_SIZE
import woowacourse.shopping.domain.toPage
import woowacourse.shopping.ui.productdetail.component.mintButton
import woowacourse.shopping.ui.shopping.component.productItem
import woowacourse.shopping.ui.shopping.component.productListTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun productListScreen(
    products: Products,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var currentPageIndex by rememberSaveable { mutableStateOf(0) }
    val visibleCount = (currentPageIndex + 1) * SHOPPING_PAGE_SIZE
    val visibleProducts = products.products.toPage(PageRequest(0, visibleCount))

    Scaffold(
        topBar = {
            productListTopAppBar(
                onClick = {
                    val intent = Intent(context, CartActivity::class.java)
                    context.startActivity(intent)
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
                    productItem(product)
                }
                if (products.hasNextPage(currentPageIndex = currentPageIndex)) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        mintButton(
                            onClick = {
                                currentPageIndex++
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
private fun productListScreenPreview() {
    productListScreen(
        products = Products(ProductFixture.productList),
    )
}
