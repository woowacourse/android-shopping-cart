package woowacourse.shopping

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.ui.component.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = runCatching { UUID.fromString(intent.getStringExtra("id")) }.getOrNull()
        var cart = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("extra_cart", Cart::class.java)
        } else {
            intent.getParcelableExtra("extra_cart")
        }

        if (productId == null || cart == null) {
            finish()
            return
        }
        val product = MockCatalog.findProductById(productId)
        val toast = Toast.makeText(this, "장바구니에 담았습니다", Toast.LENGTH_SHORT)

        enableEdgeToEdge()
        setContent {
            var amount by rememberSaveable { mutableIntStateOf(1) }
            AndroidshoppingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        amount = amount,
                        onAddRequest = {
                            cart = cart?.addProduct(product, amount)
                            val resultIntent = Intent().apply {
                                putExtra("extra_cart", cart)
                            }
                            setResult(RESULT_OK, resultIntent)
                            toast.show()
                            finish()
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
