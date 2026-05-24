package woowacourse.shopping.ui.catalog

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.NetworkMonitor
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.catalog.component.MainScreen
import woowacourse.shopping.ui.product_detail.ProductDetailActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val networkMonitor = NetworkMonitor(this)

        setContent {
            AndroidshoppingTheme {
                val app = application as ShoppingApplication
                val viewModel: CatalogViewModel = viewModel(
                    factory = CatalogViewModel.provideFactory(
                        app.productRepository,
                        app.cartRepository,
                        app.recentProductRepository
                    )
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LaunchedEffect(Unit) {
                        var isInitial = true
                        networkMonitor.isConnected.collect { connected ->
                            if (isInitial) {
                                isInitial = false
                                return@collect
                            }
                            if (!connected) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "네트워크 연결이 끊겼습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "네트워크가 연결되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    MainScreen(
                        viewModel = viewModel,
                        onItemClick = { product ->
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(ProductDetailActivity.Companion.EXTRA_PRODUCT, product)
                            }
                            startActivity(intent)
                        },
                        onCartClick = {
                            val intent = Intent(this, CartActivity::class.java)
                            startActivity(intent)
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
