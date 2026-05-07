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
import woowacourse.shopping.di.AppContainer
import woowacourse.shopping.ui.theme.ShoppingTheme
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    val productRepo = AppContainer.productRepository
    val cartRepo = AppContainer.cartRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val receivedProductId: String = intent.getStringExtra(EXTRA_PRODUCT_ID)
            ?: error("ProductDetailActivity를 실행하려면 반드시 Intent에 Product ID 데이터가 포함되어야 합니다.")

        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = rememberProductDetailScreenState(productRepo, cartRepo)

                    ProductDetailScreen(
                        productId = UUID.fromString(receivedProductId),
                        state = state,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = ::finish,
                        onAddToCartClick = ::finish,
                    )
                }
            }
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "com.woowacourse.shopping.PRODUCT.ID"

        fun newIntent(
            context: Context,
            productId: UUID,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId.toString())
            }
    }
}
