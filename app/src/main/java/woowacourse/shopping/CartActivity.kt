package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.CartScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class CartActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        setContent {
            AndroidshoppingTheme {
                CartScreen(
                    onDelete = { productId -> CartRepository.deleteProduct(productId) }
                )
            }
        }
    }
}

