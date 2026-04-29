package woowacourse.shopping.ui.productdetail

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
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.productdetail.ui.theme.AndroidshoppingcartTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedProduct = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("PRODUCT", Product::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("PRODUCT")
        }

        if(receivedProduct == null) {
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            AndroidshoppingcartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductDetailScreen(
                        product = receivedProduct,
                        modifier = Modifier.padding(innerPadding),
                        onCloseClick = ::finish,
                        onAddToCart = { product, _ ->
                            InMemoryCartRepository.add(product)
                            finish()
                        }
                    )
                }
            }
        }
    }
}
