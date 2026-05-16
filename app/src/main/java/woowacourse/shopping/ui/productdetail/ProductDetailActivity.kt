package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.common.theme.ShoppingTheme
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedProductId: String =
            intent.getStringExtra(EXTRA_PRODUCT_ID)
                ?: error("ProductDetailActivity를 실행하려면 반드시 Intent에 Product ID 데이터가 포함되어야 합니다.")
        val container = (application as ShoppingApplication).appContainer

        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: ProductDetailViewModel =
                        viewModel(
                            factory = ProductDetailViewModel.provideFactory(
                                productRepo = container.productRepository,
                                cartRepo = container.cartRepository,
                                recentProductRepo = container.recentProductRepository,
                                receivedProductId = receivedProductId
                            ),
                        )

                    ProductDetailScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = {
                            finish()
                        },
                        onAddToCartClick = ::finish,
                        onLastViewedProductClick = {
                            val intent =
                                newIntent(context = this, productId = it.id, isFromBanner = true)
                            startActivity(intent)
                            finish()
                        },
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "com.woowacourse.shopping.PRODUCT_ID"
        const val EXTRA_IS_FROM_BANNER = "com.woowacourse.shopping.IS_FROM_BANNER"

        fun newIntent(
            context: Context,
            productId: UUID,
            isFromBanner: Boolean = false,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId.toString())
                putExtra(EXTRA_IS_FROM_BANNER, isFromBanner)
            }
    }
}
