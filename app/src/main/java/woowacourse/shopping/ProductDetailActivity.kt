package woowacourse.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import woowacourse.ProductDetailSource
import woowacourse.shopping.domain.Products
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
        }
        val product = Products(ProductFixture.productList(packageName)).findProductById(productId)
        if (product == null) {
            finish()
            return
        }
        val sourceName =
            intent.getStringExtra(PRODUCT_DETAIL_SOURCE_KEY)
                ?: ProductDetailSource.PRODUCT_LIST.name

        val source =
            runCatching { ProductDetailSource.valueOf(sourceName) }
                .getOrDefault(ProductDetailSource.PRODUCT_LIST)
        setContent {
            AndroidShoppingTheme {
                ProductDetailScreen(
                    viewModel = viewModel,
                    product = product,
                    source = source,
                    onProductClick = { clickedProductId ->
                        ProductDetailActivity.start(
                            this,
                            clickedProductId,
                            ProductDetailSource.LAST_VIEWED_CARD
                        )
                        finish()
                    },
                    onClose = { finish() },
                )
            }
        }
    }

    companion object {
        private const val PRODUCT_ID_EXTRA_KEY = "woowacourse.shopping.product_id"
        private const val PRODUCT_DETAIL_SOURCE_KEY =
            "woowacourse.shopping.product_detail_source"

        @OptIn(ExperimentalUuidApi::class)
        fun start(
            context: Context,
            productId: Uuid,
            source: ProductDetailSource = ProductDetailSource.PRODUCT_LIST
        ) {
            val intent =
                Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra(PRODUCT_ID_EXTRA_KEY, productId.toString())
                    putExtra(PRODUCT_DETAIL_SOURCE_KEY, source.name)
                }
            context.startActivity(intent)
        }
    }
}
