package woowacourse.shopping.presentation.productdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.presentation.navigation.IntentKeys
import woowacourse.shopping.presentation.productdetail.screen.ProductDetailErrorScreen
import woowacourse.shopping.presentation.productdetail.screen.ProductDetailScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
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
                    ProductRepositoryImpl.findProductById(productId)
                }

        setContent {
            androidshoppingTheme {
                val stateHolder =
                    remember {
                        ProductDetailStateHolder(
                            cartRepository = CartRepositoryImpl,
                        )
                    }
                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        onClose = { finish() },
                        onAddToCart = { product -> stateHolder.addToCart(product) },
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
