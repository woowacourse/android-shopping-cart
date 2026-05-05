package woowacourse.shopping.features.productDetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.data.DataProvider
import woowacourse.shopping.data.cart.CartRepositoryMockImpl
import woowacourse.shopping.data.product.ProductRepositoryMockImpl
import woowacourse.shopping.features.cart.CartActivity

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getStringExtra("PRODUCT_ID") ?: ""
        val stateHolder =
            ProductDetailStateHolder(
                productId = productId,
                productRepository = DataProvider.productRepository,
                cartRepository = DataProvider.cartRepository,
            )

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    product = stateHolder.detailProduct(),
                    onAddToCartClick = {
                        val cartIntent = Intent(this, CartActivity::class.java)
                        stateHolder.addToCart()
                        startActivity(cartIntent)
                    },
                )
            }
        }
    }
}
