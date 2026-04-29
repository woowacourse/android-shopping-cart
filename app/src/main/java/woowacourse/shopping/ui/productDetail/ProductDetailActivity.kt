package woowacourse.shopping.ui.productDetail

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
import woowacourse.shopping.mock.MockData
import woowacourse.shopping.repository.product.ProductRepository
import woowacourse.shopping.repository.product.ProductRepositoryMockImpl

class ProductDetailActivity : ComponentActivity() {
    private val productRepository: ProductRepository = ProductRepositoryMockImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getStringExtra("PRODUCT_ID")

        val product = productRepository.getProduct(productId)

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    product = product,
                )
            }
        }
    }
}
