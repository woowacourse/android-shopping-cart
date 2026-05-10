package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.productdetail.ui.theme.AndroidshoppingcartTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getStringExtra(EXTRA_PRODUCT_ID)

        if (id.isNullOrBlank()) {
            Toast.makeText(this, "해당 상품 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            AndroidshoppingcartTheme {
                ProductDetailRoute(
                    onNavigateToHome = {
                        finish()
                    },
                    onNavigateLatestProduct = { id ->
                        val intent = newIntent(this, id)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    },
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "product_id"

        fun newIntent(
            context: Context,
            productId: String,
        ): Intent = Intent(context, ProductDetailActivity::class.java)
            .putExtra(EXTRA_PRODUCT_ID, productId)
    }
}
