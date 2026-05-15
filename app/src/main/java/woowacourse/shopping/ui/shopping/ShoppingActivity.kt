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
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.remote.NetworkMonitor
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.common.theme.ShoppingTheme
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

class ShoppingActivity : ComponentActivity() {
    private val loadSize = 20

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val container = (application as ShoppingApplication).appContainer
                    val viewModel: ShoppingViewModel =
                        viewModel(
                            factory =
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T =
                                        ShoppingViewModel(
                                            networkMonitor = NetworkMonitor(applicationContext),
                                            productRepo = container.productRepository,
                                            cartRepo = container.cartRepository,
                                            recentProductRepo = container.recentProductRepository,
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
