package woowacourse.shopping.ui.productList

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productDetail.ProductDetailActivity

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appContainer = (application as ShoppingApplication).appContainer
        setContent {
            val viewModel: ProductListViewModel =
                viewModel(
                    factory =
                        ProductListViewModel.factory(
                            productRepository = appContainer.productRepository,
                            cartRepository = appContainer.cartRepository,
                            recentProductRepository = appContainer.recentProductRepository,
                        ),
                )

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductListScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    onCartClick = {
                        val cartIntent = Intent(this, CartActivity::class.java)
                        startActivity(cartIntent)
                    },
                    onProductClick = { product ->
                        startActivity(ProductDetailActivity.newIntent(this, product.id))
                    },
                )
            }
        }
    }
}
