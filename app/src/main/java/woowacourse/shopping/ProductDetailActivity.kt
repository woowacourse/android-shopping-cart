package woowacourse.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import woowacourse.shopping.ui.component.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = runCatching { UUID.fromString(intent.getStringExtra("id")) }.getOrNull()
        if(productId == null) {
            finish()
            return
        }
        val product = MockCatalog.findProductById(productId)
        val toast = Toast.makeText(this, "장바구니에 담았습니다", Toast.LENGTH_SHORT)

        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        onAddRequest = {
                            CartProvider.addItem(product)
                            toast.show()
                            finish()
                        },
                        onClose = { finish() },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
