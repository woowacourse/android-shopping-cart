package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
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
        val productId =
            requireNotNull(intent.getStringExtra("woowacourse.shopping.product_id")).let(Uuid::parse)
        val productName = intent.getStringExtra("woowacourse.shopping.product_name").orEmpty()
        val imageUrl = intent.getStringExtra("woowacourse.shopping.image_url").orEmpty()
        val price = intent.getIntExtra("woowacourse.shopping.price", 0)

        setContent {
            AndroidshoppingTheme {
                ProductDetailScreen(
                    product = Product(productId, imageUrl, productName, Price(price)),
                    onClose = { finish() }
                )
            }
        }
    }
}