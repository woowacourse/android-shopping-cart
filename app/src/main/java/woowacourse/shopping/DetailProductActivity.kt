@file:Suppress("FunctionName")

package woowacourse.shopping

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import woowacourse.shopping.R
import woowacourse.shopping.ui.DetailProductScreen
import woowacourse.shopping.ui.ProductDto
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class DetailProductActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository

    companion object {
        private const val INVALID_PRODUCT_ID = -1L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productId = intent.getLongExtra(ProductListActivity.EXTRA_PRODUCT_ID, INVALID_PRODUCT_ID)
                if (productId != INVALID_PRODUCT_ID) {
                    val product = productRepository.getProduct(productId)
                    if (product != null) {
                        DetailProductScreen(
                            product =
                                ProductDto(
                                    id = product.id,
                                    title = product.getTitle(),
                                    price = DecimalFormat(stringResource(R.string.price_format_pattern)).format(product.getPrice()),
                                    imageUrl = product.imageUrl,
                                ),
                            onAddToCartClick = {
                                ShoppingApplication.shoppingCartRepository =
                                    ShoppingApplication.shoppingCartRepository.add(product)
                                this.finish()
                            },
                            onBackClick = this::finish,
                        )
                    } else {
                        Text(stringResource(R.string.product_not_found_message))
                    }
                } else {
                    Text(stringResource(R.string.product_not_found_message))
                }
            }
        }
    }
}
