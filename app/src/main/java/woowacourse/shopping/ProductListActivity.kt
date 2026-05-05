@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Intent
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

    companion object {
        const val EXTRA_PRODUCT_ID = "productId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var savedCurrentPage by rememberSaveable { mutableIntStateOf(0) }
            AndroidShoppingTheme {
                val productPaginationStateHolder =
                    remember {
                        ProductPageStateHolder(productRepository.getProducts()).apply {
                            restoreCurrentPage(savedCurrentPage)
                        }
                    }
                ProductListScreen(
                    products = productPaginationStateHolder.getItems(),
                    onProductClick = { productId ->
                        val intent = Intent(this, DetailProductActivity::class.java)
                        intent.putExtra(EXTRA_PRODUCT_ID, productId)
                        startActivity(intent)
                    },
                    onNavigateToCartClick = {
                        startActivity(Intent(this, ShoppingCartActivity::class.java))
                    },
                ) {
                    MoreButton(
                        onClick = {
                            productPaginationStateHolder.nextPage()
                            savedCurrentPage = productPaginationStateHolder.currentPage
                        },
                    )
                }
            }
        }
    }
}
