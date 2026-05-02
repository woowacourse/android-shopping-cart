package woowacourse.shopping.ui.productdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.navigation.IntentKeys
import woowacourse.shopping.ui.productdetail.screen.ProductDetailErrorScreen
import woowacourse.shopping.ui.productdetail.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val product =
            intent
                .getStringExtra(IntentKeys.PRODUCT_ID)
                ?.toUuidOrNull()
                ?.let { productId ->
                    Products(ProductFixture.productList).findProductById(productId)
                }

        setContent {
            androidshoppingTheme {
                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        onClose = { finish() },
                    )
                } else {
                    ProductDetailErrorScreen(onClose = { finish() })
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
private fun String.toUuidOrNull(): Uuid? = runCatching { Uuid.parse(this) }.getOrNull()
