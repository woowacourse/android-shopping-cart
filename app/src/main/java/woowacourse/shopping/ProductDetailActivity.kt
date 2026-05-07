package woowacourse.shopping

import android.os.Bundle
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
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.ui.component.screen.ProductDetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product =
            intent?.getParcelableExtra<Product>(IntentKeys.SELECTED_PRODUCT_KEY) ?: run {
                finish()
                return
            }

        enableEdgeToEdge()
        setContent {
            var count by rememberSaveable { mutableIntStateOf(1) }

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductDetailScreen(
                        product = product,
                        count = count,
                        onAdd = { count++ },
                        onMinus = {
                            if (count != 1) count-- else count
                        },
                        onAddRequest = {
                            intent.putExtra(IntentKeys.STORED_PRODUCT_KEY,
                                PurchaseProduct(product, count)
                            )
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
