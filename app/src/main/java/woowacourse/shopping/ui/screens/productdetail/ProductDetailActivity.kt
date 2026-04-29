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
        val productId: String = intent.getStringExtra("productId") ?: throw IllegalArgumentException("삐용삐용 id가 없으면 안됭당께...")
        setContent {
            AndroidshoppingTheme {
                ProductDetailScreen(
                    productDetailStateHolder = ProductDetailStateHolder(targetProductId = productId),
                    onDismiss = { finish() },
                )
            }
        }
    }
}
