package woowacourse.shopping.presentation.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.saveable.rememberSaveable
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productdetail.mapper.toUiModel
import woowacourse.shopping.presentation.shopping.screen.ProductListScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class ProductListActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            androidshoppingTheme {
                val stateHolder =
                    rememberSaveable(saver = ProductListStateHolder.Saver) {
                        ProductListStateHolder(
                            productRepository = AppContainer.productRepository,
                        )
                    }
                ProductListScreen(
                    stateHolder = stateHolder,
                    onCartIconClick = {
                        startActivity(CartActivity.newIntent(this))
                    },
                    onItemClick = { product ->
                        startActivity(ProductDetailActivity.newIntent(this, product.toUiModel()))
                    },
                )
            }
        }
    }
}
