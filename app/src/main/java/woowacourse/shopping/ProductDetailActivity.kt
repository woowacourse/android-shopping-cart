package woowacourse.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.productdetail.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        val productIdString = intent.getStringExtra(PRODUCT_ID_EXTRA_KEY)
        if (productIdString == null) {
            finish()
            return
        }
        val productId =
            runCatching {
                Uuid.parse(productIdString)
            }.getOrNull()
        if (productId == null) {
            finish()
            return
        }
        val product = Products(ProductFixture.productList(packageName)).findProductById(productId)
        if (product == null) {
            finish()
            return
        }

        setContent {
            AndroidShoppingTheme {
                ProductDetailScreen(
                    product = product,
                    onAddToCart = { AppContainer.cartRepository.addProduct(it) },
                    onClose = { finish() },
                )
            }
        }
    }

    companion object {
        private const val PRODUCT_ID_EXTRA_KEY = "woowacourse.shopping.product_id"

        @OptIn(ExperimentalUuidApi::class)
        fun start(
            context: Context,
            productId: Uuid,
        ) {
            val intent =
                Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra(PRODUCT_ID_EXTRA_KEY, productId.toString())
                }
            context.startActivity(intent)
        }
    }
}
