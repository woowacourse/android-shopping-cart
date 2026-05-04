package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.productdetail.screen.productDetailScreen
import woowacourse.shopping.ui.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

const val PRODUCT_ID_EXTRA_KEY = "woowacourse.shopping.product_id"

class ProductDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        val productId =
            requireNotNull(intent.getStringExtra(PRODUCT_ID_EXTRA_KEY)).let(
                Uuid.Companion::parse,
            )
        val product = Products(ProductFixture.productList).findProductById(productId)

        setContent {
            androidshoppingTheme {
                productDetailScreen(
                    product = product,
                    onClose = { finish() },
                )
            }
        }
    }
}
