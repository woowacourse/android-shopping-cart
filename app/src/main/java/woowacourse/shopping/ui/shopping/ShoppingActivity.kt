package woowacourse.shopping.ui.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.DetailActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ShoppingActivity : ComponentActivity() {
    private val viewModel: ShoppingViewModel by viewModels {
        val appContainer = (application as ShoppingApplication).appContainer
        ShoppingViewModel.provideFactory(
            productRepository = appContainer.productRepository,
            cartRepository = appContainer.cartRepository,
            recentItemRepository = appContainer.recentItemRepository,
            networkObserver = appContainer.networkObserver,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AndroidshoppingTheme {
                ShoppingScreen(
                    uiState = uiState,
                    onLoad = viewModel::loadMore,
                    onProductClick = { id ->
                        val intent = DetailActivity.getIntent(this, id)
                        startActivity(intent)
                    },
                    onCartClick = { startActivity(CartActivity.getIntent(applicationContext)) },
                    onQuantityChange = viewModel::updateQuantity,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRecentItems()
    }
}
