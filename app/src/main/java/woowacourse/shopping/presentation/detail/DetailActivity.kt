package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.R
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.detail.ui.DetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import kotlin.getValue

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val id = this.intent.getStringExtra(INTENT_PRODUCT_ID)
        val isFromLastSeen = this.intent.getBooleanExtra(IS_FROM_LAST_SEEN, false)
        if (id.isNullOrBlank()) {
            Toast.makeText(this, R.string.invalid_product, Toast.LENGTH_SHORT).show()
            this.finish()
            return
        }

        setContent {
            AndroidshoppingTheme {
                DetailScreen(
                    id = id,
                    isFromLastSeen = isFromLastSeen,
                    onNavigateToCart = {
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    },
                    onClickLastProductCard = { lastProductId ->
                        val intent =
                            Intent(this, DetailActivity::class.java).apply {
                                putExtra(INTENT_PRODUCT_ID, lastProductId)
                                putExtra(IS_FROM_LAST_SEEN, true)
                            }
                        startActivity(intent)
                        finish()
                    },
                )
            }
        }
    }

    companion object {
        private const val INTENT_PRODUCT_ID = "id"
        private const val IS_FROM_LAST_SEEN = "is_from_last_seen"

        fun newIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, DetailActivity::class.java)
                .putExtra(INTENT_PRODUCT_ID, productId)
    }
}
