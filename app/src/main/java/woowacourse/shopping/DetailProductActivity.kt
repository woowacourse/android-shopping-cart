@file:Suppress("FunctionName")

package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import woowacourse.shopping.ui.DetailProductScreen
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class DetailProductActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository
    private val shoppingCartRepository = ShoppingApplication.shoppingCartRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productId = intent.getLongExtra("productId", -1)
                if (productId != -1L) {
                    val product = productRepository.getProduct(productId)
                    DetailProductScreen(
                        product = product,
                        onAddToCartClick = {
                            shoppingCartRepository.add(product)
                            this.finish()
                        },
                        onBackClick = this::finish,
                    )
                } else {
                    Text("상품을 찾을 수 없습니다")
                }
            }
        }
    }
}
