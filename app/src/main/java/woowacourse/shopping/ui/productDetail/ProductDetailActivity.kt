package woowacourse.shopping.ui.productDetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import woowacourse.shopping.repository.cart.CartRepositoryMockImpl
import woowacourse.shopping.repository.product.ProductRepositoryMockImpl
import woowacourse.shopping.ui.cart.CartActivity

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getStringExtra("PRODUCT_ID") ?: ""
        val viewModel = ProductDetailViewModel(
                productId = productId,
                productRepository = ProductRepositoryMockImpl(),
                cartRepository = CartRepositoryMockImpl,
            )

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    onAddToCartClick = {
                        val cartIntent = Intent(this, CartActivity::class.java)

                        lifecycleScope.launch {
                            viewModel.addToCart()
                            startActivity(cartIntent)
                        }
                    },
                )
            }
        }
    }
}
