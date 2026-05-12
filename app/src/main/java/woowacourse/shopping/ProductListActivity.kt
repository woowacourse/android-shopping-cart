package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ui.shopping.screen.ProductListScreen
import woowacourse.shopping.ui.shopping.viewmodel.ProductListViewModel
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class ProductListActivity : ComponentActivity() {
    private val viewModel: ProductListViewModel by viewModels {
        viewModelFactory {
            initializer {
                ProductListViewModel(
                    AppContainer.recentlyViewedProductsRepository,
                    AppContainer.productRepository,
                    AppContainer.cartRepository,
                    AppContainer.networkMonitor,
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                ProductListScreen(
                    viewModel = viewModel,
                    onCartClick = { startActivity(Intent(this, CartActivity::class.java)) },
                    onProductClick = { productId ->
                        val shouldHideLastViewedProductCard =
                            viewModel.recentlyViewedProducts.products
                                .firstOrNull()
                                ?.productId == productId
                        ProductDetailActivity.start(
                            context = this,
                            productId = productId,
                            shouldHideLastViewedProductCard = shouldHideLastViewedProductCard,
                        )
                    },
                    modifier = Modifier.testTag("product_list"),
                )
            }
        }
    }
}
