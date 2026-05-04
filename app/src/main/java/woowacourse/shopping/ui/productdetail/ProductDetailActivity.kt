package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository
import woowacourse.shopping.ui.theme.ShoppingTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedProduct =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_PRODUCT, Product::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(EXTRA_PRODUCT)
            } ?: error("ProductDetailActivity를 실행하려면 반드시 Intent에 Product 데이터가 포함되어야 합니다.")

        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val cartRepo = InMemoryCartRepository

                    ProductDetailScreen(
                        product = receivedProduct,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = ::finish,
                        onAddToCart = { product, _ ->
                            cartRepo.add(product)
                            finish()
                        },
                    )
                }
            }
        }
    }

    companion object {
        private const val EXTRA_PRODUCT = "com.woowacourse.shopping.PRODUCT"

        fun newIntent(context: Context, product: Product): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT, product)
            }
        }
    }
}
