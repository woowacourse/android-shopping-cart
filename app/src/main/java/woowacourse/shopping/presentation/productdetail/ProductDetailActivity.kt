package woowacourse.shopping.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.navigation.IntentKeys
import woowacourse.shopping.presentation.productdetail.mapper.toUiModel
import woowacourse.shopping.presentation.productdetail.model.ProductUiModel
import woowacourse.shopping.presentation.productdetail.screen.ProductDetailErrorScreen
import woowacourse.shopping.presentation.productdetail.screen.ProductDetailScreen
import woowacourse.shopping.presentation.shopping.ProductListActivity
import woowacourse.shopping.presentation.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class ProductDetailActivity : ComponentActivity() {
    val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(
            productRepository = AppContainer.productRepository,
            cartRepository = AppContainer.cartRepository,
            recentlyViewedProductRepository = AppContainer.recentlyViewedProductRepository,
            lastViewedProductRepository = AppContainer.lastViewedProductRepository,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val product = intent.getProduct()
        val fromLastViewedProduct = intent.getBooleanExtra(IntentKeys.FROM_LAST_VIEWED_PRODUCT, false)

        if (product != null) {
            viewModel.viewProduct(
                productId = product.productId,
                shouldShowLastViewedProduct = !fromLastViewedProduct,
            )
        }

        setContent {
            androidshoppingTheme {
                if (product != null) {
                    ProductDetailScreen(
                        viewModel = viewModel,
                        product = product,
                        onLastViewedProductClick = {
                            startActivity(
                                newIntent(
                                    context = this,
                                    product = it.toUiModel(),
                                    fromLastViewedProduct = true,
                                ),
                            )
                        },
                        onClose = {
                            val intent = Intent(this, ProductListActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        },
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
            fromLastViewedProduct: Boolean = false,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(IntentKeys.PRODUCT, product)
                putExtra(IntentKeys.FROM_LAST_VIEWED_PRODUCT, fromLastViewedProduct)
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
