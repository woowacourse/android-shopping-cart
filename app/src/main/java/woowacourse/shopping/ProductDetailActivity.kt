package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.ui.component.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import java.util.UUID

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = UUID.fromString(intent.getStringExtra("id"))
        val product = MockCatalog.findProductById(productId)

        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        onAddRequest = {
                            intent.putExtra("id", product.uuid.toString())
                            setResult(RESULT_OK, intent)
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
