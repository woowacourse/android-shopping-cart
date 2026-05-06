@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import woowacourse.shopping.R
import woowacourse.shopping.ui.DetailProductScreen
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class DetailProductActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository
    private val shoppingCartRepository = ShoppingApplication.shoppingCartRepository

    companion object {
        private const val EXTRA_PRODUCT_ID = "productId"
        private const val INVALID_PRODUCT_ID = -1L

        fun start(
            context: Context,
            productId: Long,
        ) {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, INVALID_PRODUCT_ID)
        val product =
            if (productId == INVALID_PRODUCT_ID) {
                null
            } else {
                productRepository.getProductOrNull(productId)
            }
        setContent {
            AndroidShoppingTheme {
                if (product != null) {
                    DetailProductScreen(
                        product = product,
                        onAddToCartClick = {
                            shoppingCartRepository.add(product)
                            this.finish()
                        },
                        onBackClick = this::finish,
                    )
                } else {
                    Text(stringResource(R.string.product_not_found_message))
                }
            }
        }
    }
}
