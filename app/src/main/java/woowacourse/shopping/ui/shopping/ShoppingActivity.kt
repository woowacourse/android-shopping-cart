package woowacourse.shopping.ui.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.theme.ShoppingTheme

class ShoppingActivity : ComponentActivity() {
    private val viewModel: ShoppingViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.reloadVisibleState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShoppingScreen(
                        products = uiState.products,
                        recentProducts = uiState.recentProducts,
                        cartQuantity = uiState.cartQuantity,
                        hasNext = uiState.hasNext,
                        isLoading = uiState.isLoading,
                        isNetworkConnected = uiState.isNetworkConnected,
                        errorMessage = uiState.errorMessage,
                        modifier = Modifier.padding(innerPadding),
                        onCartClick = {
                            startActivity(Intent(this, CartActivity::class.java))
                        },
                        onProductClick = {
                            ProductDetailActivity.startActivity(this, it.id)
                        },
                        onMoreClick = viewModel::loadMore,
                        onAddToCart = viewModel::addToCart,
                        onIncreaseQuantity = viewModel::increaseQuantity,
                        onDecreaseQuantity = viewModel::decreaseQuantity,
                    )
                }
            }
        }
    }
}
