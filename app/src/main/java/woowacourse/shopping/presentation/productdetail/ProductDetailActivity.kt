package woowacourse.shopping.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.navigation.IntentKeys
import woowacourse.shopping.presentation.productdetail.model.ProductUiModel
import woowacourse.shopping.presentation.productdetail.screen.ProductDetailErrorScreen
import woowacourse.shopping.presentation.productdetail.screen.ProductDetailScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class ProductDetailActivity : ComponentActivity() {
    val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(
            productRepository = AppContainer.productRepository,
            cartRepository = AppContainer.cartRepository,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val product = intent.getProduct()

        setContent {
            androidshoppingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        quantity = uiState.quantity,
                        onClose = { finish() },
                        onQuantityIncrease = viewModel::increaseQuantity,
                        onQuantityDecrease = viewModel::decreaseQuantity,
                        onAddToCart = viewModel::addToCart,
                    )
                } else {
                    ProductDetailErrorScreen(onClose = { finish() })
                }
            }
        }
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun newIntent(
            context: Context,
            product: ProductUiModel,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(IntentKeys.PRODUCT, product)
            }
    }
}

@Suppress("DEPRECATION")
private fun Intent.getProduct(): ProductUiModel? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(IntentKeys.PRODUCT, ProductUiModel::class.java)
    } else {
        getParcelableExtra(IntentKeys.PRODUCT)
    }
