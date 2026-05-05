package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.detail.ui.DetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = this.intent.getStringExtra(INTENT_PRODUCT_ID)
        if (id.isNullOrBlank()) {
            Toast.makeText(this, R.string.invalid_product, Toast.LENGTH_SHORT).show()
            this.finish()
            return
        }

        setContent {
            AndroidshoppingTheme {
                DetailScreen(
                    id = id,
                    onNavigateToCart = { result ->
                        when (result) {
                            is AddItemResult.NewAdded -> {
                                val intent = Intent(this, CartActivity::class.java)
                                startActivity(intent)
                            }
                            is AddItemResult.DuplicateItem ->
                                Toast.makeText(this, R.string.already_product_in_cart, Toast.LENGTH_SHORT).show()
                        }
                    },
                )
            }
        }
    }

    companion object {
        private const val INTENT_PRODUCT_ID = "id"

        fun newIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, DetailActivity::class.java)
                .putExtra(INTENT_PRODUCT_ID, productId)
    }
}
