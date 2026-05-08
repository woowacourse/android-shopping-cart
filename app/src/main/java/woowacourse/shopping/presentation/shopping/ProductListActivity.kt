package woowacourse.shopping.presentation.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productdetail.mapper.toUiModel
import woowacourse.shopping.presentation.shopping.screen.ProductListScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class ProductListActivity : ComponentActivity() {
    private val viewModel: ProductListViewModel by viewModels {
        ProductListViewModelFactory(
            productRepository = AppContainer.productRepository,
            cartRepository = AppContainer.cartRepository,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            androidshoppingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ProductListScreen(
                    products = uiState.products,
                    productQuantities = uiState.productQuantities,
                    hasNextPage = viewModel.hasNextPage,
                    totalQuantity = uiState.totalQuantity,
                    onLoadMore = viewModel::loadMore,
                    onCartIconClick = {
                        startActivity(CartActivity.newIntent(this))
                    },
                    onItemClick = { product ->
                        startActivity(ProductDetailActivity.newIntent(this, product.toUiModel()))
                    },
                    onQuantityIncrease = viewModel::increaseQuantity,
                    onQuantityDecrease = viewModel::decreaseQuantity,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCart()
    }
}
