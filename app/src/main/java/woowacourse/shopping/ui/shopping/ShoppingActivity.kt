package woowacourse.shopping.ui.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.di.AppContainer
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.common.theme.ShoppingTheme

class ShoppingActivity : ComponentActivity() {
    val productRepo = AppContainer.productRepository
    val cartRepo = AppContainer.cartRepository
    val recentProductRepo = AppContainer.recentProductRepository
    val loadSize = 20

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: ShoppingViewModel =
                        viewModel(
                            factory =
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T =
                                        ShoppingViewModel(
                                            networkMonitor = NetworkMonitor(applicationContext),
                                            productRepo = productRepo,
                                            cartRepo = cartRepo,
                                            recentProductRepo = recentProductRepo,
                                            loadSize = loadSize,
                                        ) as T
                                },
                        )

                    ShoppingScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding),
                        onCartClick = {
                            startActivity(Intent(this, CartActivity::class.java))
                        },
                        onProductClick = {
                            val intent =
                                ProductDetailActivity.newIntent(context = this, productId = it.id)
                            startActivity(intent)
                        },
                        onRecentProductClick = {
                            val intent =
                                ProductDetailActivity.newIntent(context = this, productId = it.id)
                            startActivity(intent)
                        },
                    )
                }
            }
        }
    }
}
