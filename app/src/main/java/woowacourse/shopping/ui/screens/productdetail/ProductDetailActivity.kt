package woowacourse.shopping.ui.screens.productdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getStringExtra("productId")

        if (productId == null) {
            finish()
            return
        }

        setContent {
            AndroidshoppingTheme {
                ProductDetailScreen(
                    productId = productId,
                    onDismiss = { finish() },
                )
            }
        }
    }
}
