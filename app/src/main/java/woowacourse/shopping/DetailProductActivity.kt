@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import woowacourse.shopping.repository.MemoryProductRepository
import woowacourse.shopping.repository.MemoryShoppingCartRepository
import woowacourse.shopping.ui.DetailProductScreen
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class DetailProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productId = intent.getLongExtra("productId", -1)
                if (productId != -1L) {
                    val product = MemoryProductRepository.getProduct(productId)
                    DetailProductScreen(
                        product = product,
                        onAddToCartClick = {
                            MemoryShoppingCartRepository.add(product)
                            startActivity(
                                Intent(
                                    this@DetailProductActivity,
                                    ProductListActivity::class.java,
                                ),
                            )
                        },
                        onBackClick = {
                            startActivity(
                                Intent(
                                    this@DetailProductActivity,
                                    ShoppingCartActivity::class.java,
                                ),
                            )
                        },
                    )
                } else {
                    Text("상품을 찾을 수 없습니다")
                }
            }
        }
    }
}
