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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.di.AppContainer
import woowacourse.shopping.ui.common.theme.ShoppingTheme
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    val productRepo = AppContainer.productRepository
    val cartRepo = AppContainer.cartRepository
    val recentProductRepo = AppContainer.recentProductRepository

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedProductId: String =
            intent.getStringExtra(EXTRA_PRODUCT_ID)
                ?: error("ProductDetailActivity를 실행하려면 반드시 Intent에 Product ID 데이터가 포함되어야 합니다.")
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: ProductDetailViewModel =
                        viewModel(
                            factory =
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(
                                        modelClass: Class<T>,
                                        extras: CreationExtras,
                                    ): T {
                                        val savedStateHandle = extras.createSavedStateHandle()

                                        return ProductDetailViewModel(
                                            savedStateHandle = savedStateHandle,
                                            productRepo = productRepo,
                                            cartRepo = cartRepo,
                                            recentProductRepo = recentProductRepo,
                                            productId = UUID.fromString(receivedProductId),
                                        ) as T
                                    }
                                },
                        )

                    ProductDetailScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = {
                            finish()
                        },
                        onAddToCartClick = ::finish,
                        onLastViewedProductClick = {
                            val intent = newIntent(context = this, productId = it.id, isFromBanner = true)
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
