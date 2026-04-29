@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import woowacourse.shopping.ui.ProductListScreen
import woowacourse.shopping.ui.pagination.ProductPaginationStateHolder
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ProductListActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productPaginationStateHolder =
                    remember {
                        ProductPaginationStateHolder(productRepository.getProducts())
                    }
                ProductListScreen(
                    products = productPaginationStateHolder.getItems(),
                    onProductClick = { productId ->
                        val intent = Intent(this, DetailProductActivity::class.java)
                        intent.putExtra("productId", productId)
                        startActivity(intent)
                    },
                    onNavigateToCartClick = {
                        startActivity(Intent(this, ShoppingCartActivity::class.java))
                    },
                    onMoreProductClick = productPaginationStateHolder::nextPage,
                )
            }
        }
    }
}
