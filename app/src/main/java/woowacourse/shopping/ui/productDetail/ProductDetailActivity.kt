package woowacourse.shopping.ui.productDetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.mock.MockData
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.cart.MemoryCartRepository
import woowacourse.shopping.repository.product.ProductRepository
import woowacourse.shopping.repository.product.ProductRepositoryMockImpl
import woowacourse.shopping.ui.cart.CartActivity

class ProductDetailActivity : ComponentActivity() {
    private val productRepository: ProductRepository = ProductRepositoryMockImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getStringExtra("PRODUCT_ID")

        val product = productRepository.getProduct(productId)

        val viewModel = ProductDetailViewModel(product = product, cartRepository = MemoryCartRepository)

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    onAddToCartClick = {
                        // 장바구니 담기 로직 추가
                        viewModel.addToCart()
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
