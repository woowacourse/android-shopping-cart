package woowacourse.shopping.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class DetailActivity : ComponentActivity() {
    companion object {
        const val PRODUCT_ID = "id"

        fun getIntent(
            context: Context,
            id: String,
        ): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, id)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val id = intent.getStringExtra(PRODUCT_ID)
            if (id == null) {
                Toast.makeText(this, "유효하지 않은 상품입니다.", Toast.LENGTH_SHORT).show()
                finish()
                return@setContent
            }
            AndroidshoppingTheme {
                DetailScreen(
                    id = id,
                    onNavigateToCart = {
                        startActivity(CartActivity.getIntent(this))
                    },
                    onProductNotFound = {
                        Toast.makeText(this, "유효하지 않은 상품입니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(this, "이미 장바구니에 담긴 상품입니다.", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier,
                )
            }
        }
    }
}
