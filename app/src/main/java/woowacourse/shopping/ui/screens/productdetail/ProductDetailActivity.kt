package woowacourse.shopping.ui.screens.productdetail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId: String = getProductId() ?: return

        setContent {
            AndroidshoppingTheme {
                ProductDetailScreen(
                    productId = productId,
                    onDismiss = { finish() },
                )
            }
        }
    }

    private fun getProductId(): String? {
        val productId = intent.getStringExtra("productId")

        if (productId == null) {
            Log.e("ProductDetailActivity", "삐용삐용 id가 없으면 안됭당께...")
            finish()
            return null
        }
        return productId
    }
}
