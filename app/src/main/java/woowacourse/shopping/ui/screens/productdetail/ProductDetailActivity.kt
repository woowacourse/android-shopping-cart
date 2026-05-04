package woowacourse.shopping.ui.screens.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productId = intent.getStringExtra(PRODUCT_ID)

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

    companion object {
        private const val PRODUCT_ID = "productId"

        fun newIntent(
            context: Context,
            productId: String,
        ): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT_ID, productId)

            return intent
        }
    }
}
