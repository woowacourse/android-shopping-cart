@file:Suppress("FunctionName")

package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.ui.ProductListScreen
import woowacourse.shopping.ui.component.MoreButton
import woowacourse.shopping.ui.pagination.ProductPageStateHolder
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ProductListActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var savedCurrentPage by rememberSaveable { mutableIntStateOf(0) }
            AndroidShoppingTheme {
                val productPageStateHolder =
                    remember {
                        ProductPageStateHolder(
                            products = productRepository.getProducts(),
                            initialPage = savedCurrentPage,
                        )
                    }
                ProductListScreen(
                    products = productPageStateHolder.getItems(),
                    onProductClick = { productId ->
                        DetailProductActivity.start(this, productId)
                    },
                    onNavigateToCartClick = {
                        ShoppingCartActivity.start(this)
                    },
                ) {
                    MoreButton(
                        onClick = {
                            productPageStateHolder.nextPage()
                            savedCurrentPage = productPageStateHolder.currentPage
                        },
                    )
                }
            }
        }
    }
}
