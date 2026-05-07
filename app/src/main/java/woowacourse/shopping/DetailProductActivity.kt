@file:Suppress("FunctionName")

package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import woowacourse.shopping.ui.DetailProductScreen
import woowacourse.shopping.ui.WonMoney
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class DetailProductActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productId = intent.getStringExtra(ProductListActivity.EXTRA_PRODUCT_ID)
                if (productId == null) {
                    Text(stringResource(R.string.product_not_found_message))
                    return@AndroidShoppingTheme
                }

                val product = productRepository.getProduct(productId)
                if (product == null) {
                    Text(stringResource(R.string.product_not_found_message))
                    return@AndroidShoppingTheme
                }
                DetailProductScreen(
                    productTitle = product.getTitle(),
                    productImageUrl = product.imageUrl,
                    productPrice = WonMoney(product.getPrice()),
                    onAddToCartClick = {
                        ShoppingApplication.shoppingCartRepository.add(product)
                        this.finish()
                    },
                    onBackClick = this::finish,
                )
            }
        }
    }
}
