package woowacourse.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import woowacourse.shopping.AppContainer.productRepository
import woowacourse.shopping.ui.productdetail.screen.ProductDetailScreen
import woowacourse.shopping.ui.productdetail.viewmodel.ProductDetailViewModel
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        val productIdString = intent.getStringExtra(PRODUCT_ID_EXTRA_KEY)
        if (productIdString == null) {
            finish()
            return
        }
        val productId =
            runCatching {
                Uuid.parse(productIdString)
            }.getOrNull()

        if (productId == null) {
            finish()
            return
        }
        val viewModel = ProductDetailViewModel(
            recentViewedProductsRepository = AppContainer.recentlyViewedProductsRepository,
            currentProductId = productId,
        )

        lifecycleScope.launch {
            AppContainer.recentlyViewedProductsRepository.saveViewedProduct(productId = productId)
            val product = productRepository.getProductById(productId)
            if (product == null) {
                finish()
                return@launch
            }
            val shouldHideLastViewedProductCard =
                intent.getBooleanExtra(HIDE_LAST_VIEWED_PRODUCT_CARD_KEY, false)
            setContent {
                AndroidShoppingTheme {
                    ProductDetailScreen(
                        viewModel = viewModel,
                        product = product,
                        shouldHideLastViewedProductCard = shouldHideLastViewedProductCard,
                        onProductClick = { clickedProductId ->
                            ProductDetailActivity.start(
                                context = this@ProductDetailActivity,
                                productId = clickedProductId,
                                shouldHideLastViewedProductCard = true,
                                shouldClearTop = true
                            )
                        },
                        onClose = { finish() },
                    )
                }
            }
        }


    }

    companion object {
        private const val PRODUCT_ID_EXTRA_KEY = "woowacourse.shopping.product_id"
        private const val HIDE_LAST_VIEWED_PRODUCT_CARD_KEY =
            "woowacourse.shopping.hide_last_viewed_product_card"

        @OptIn(ExperimentalUuidApi::class)
        fun start(
            context: Context,
            productId: Uuid,
            shouldHideLastViewedProductCard: Boolean = false,
            shouldClearTop: Boolean = false
        ) {
            val intent =
                Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra(PRODUCT_ID_EXTRA_KEY, productId.toString())
                    putExtra(HIDE_LAST_VIEWED_PRODUCT_CARD_KEY, shouldHideLastViewedProductCard)
                    if (shouldClearTop) {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                }
            context.startActivity(intent)
        }
    }
}
