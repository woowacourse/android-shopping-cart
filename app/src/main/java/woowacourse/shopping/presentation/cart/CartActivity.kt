package woowacourse.shopping.presentation.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.presentation.cart.screen.CartScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class CartActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        setContent {
            androidshoppingTheme {
                val stateHolder =
                    rememberSaveable {
                        CartStateHolder(
                            cartRepository = CartRepositoryImpl,
                        )
                    }
                CartScreen(
                    cart = stateHolder.cart,
                    currentPage = stateHolder.currentPage,
                    hasMoreItems = stateHolder.hasMoreItems,
                    onPreviousPageClick = { stateHolder.goToPreviousPage() },
                    onNextPageClick = { stateHolder.goToNextPage() },
                    hasPreviousPage = stateHolder.hasPreviousPage,
                    hasNextPage = stateHolder.hasNextPage,
                    onDelete = { productId -> stateHolder.deleteProduct(productId) },
                    onBack = { finish() },
                )
            }
        }
    }
}
