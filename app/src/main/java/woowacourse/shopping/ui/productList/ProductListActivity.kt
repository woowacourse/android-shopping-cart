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
import woowacourse.shopping.repository.product.ProductRepositoryMockImpl
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productDetail.ProductDetailActivity

class ProductListActivity : ComponentActivity() {
    val cartIntent = Intent(this, CartActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductListScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel =
                        ProductListViewModel(
                            productRepository = ProductRepositoryMockImpl(),
                        ),
                    onCartClick = {
                        startActivity(cartIntent)
                    },
                    onProductClick = { product ->
                        val detailIntent =
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("PRODUCT_ID", product.id)
                            }
                        startActivity(detailIntent)
                    },
                )
            }
        }
    }
}
