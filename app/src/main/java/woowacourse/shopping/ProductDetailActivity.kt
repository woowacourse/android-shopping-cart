package woowacourse.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import woowacourse.shopping.ui.component.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = runCatching { UUID.fromString(intent.getStringExtra("id")) }.getOrNull()
        if (productId == null) {
            finish()
            return
        }
        val app = application as ShoppingApplication
        val productRepository = app.productRepository
        val product = productRepository.getProductById(productId) ?: return finish()
        val cartRepository = app.cartRepository
        val recentProductRepository = app.recentProductRepository
        val toast = Toast.makeText(this, "장바구니에 담았습니다", Toast.LENGTH_SHORT)

        lifecycleScope.launch {
            recentProductRepository.addRecentProduct(product)
        }

        enableEdgeToEdge()
        setContent {
            var amount by rememberSaveable { mutableIntStateOf(1) }
            val recentProducts by recentProductRepository.recentProducts.collectAsStateWithLifecycle(
                initialValue = emptyList()
            )
            val lastViewedProduct = recentProducts.firstOrNull { it.productId != productId }

            AndroidshoppingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        amount = amount,
                        lastViewedProduct = lastViewedProduct,
                        onAddRequest = {
                            lifecycleScope.launch {
                                cartRepository.addProduct(product, amount)
                                toast.show()
                                finish()
                            }
                        },
                        onClose = { finish() },
                        onIncrease = { amount++ },
                        onDecrease = { if (amount > 1) amount-- },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
