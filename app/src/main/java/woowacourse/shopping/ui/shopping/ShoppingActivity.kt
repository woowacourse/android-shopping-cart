package woowacourse.shopping.ui.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.MockProductRepository
import woowacourse.shopping.data.RecentItemRepository
import woowacourse.shopping.data.localdb.ShoppingDB
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.DetailActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ShoppingActivity : ComponentActivity() {
    private val viewModel: ShoppingViewModel by viewModels {
        val database = ShoppingDB.getInstance(applicationContext)
        ShoppingViewModel.provideFactory(
            productRepository = MockProductRepository(),
            cartRepository = CartRepository(database.cartItemDao()),
            recentItemRepository = RecentItemRepository(database.recentItemDao()),
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
                    onIncreaseQuantity = viewModel::increaseQuantity,
                    onDecreaseQuantity = viewModel::decreaseQuantity,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRecentItems()
    }
}
