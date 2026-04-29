package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailActivity : ComponentActivity() {

    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        val productId = requireNotNull(intent.getStringExtra("woowacourse.shopping.product_id")).let(Uuid::parse)
        val product = Products(ProductFixture.productList).findProductById(productId)

        setContent {
            AndroidshoppingTheme {
                ProductDetailScreen(
                    product = product,
                    onClose = { finish() }
                )
            }
        }
    }
}