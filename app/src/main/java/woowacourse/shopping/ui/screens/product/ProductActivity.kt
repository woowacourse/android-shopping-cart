package woowacourse.shopping.ui.screens.product

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.screens.cart.CartActivity
import woowacourse.shopping.ui.screens.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.screens.productdetail.ProductDetailActivity.Companion.PRODUCT_ID
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartIntent = Intent(this, CartActivity::class.java)
        val productDetailIntent = Intent(this, ProductDetailActivity::class.java)
        setContent {
            AndroidshoppingTheme {
                ProductScreen(
                    onCartClick = { startActivity(cartIntent) },
                    onProductCardClick = { id ->
                        productDetailIntent.putExtra(PRODUCT_ID, id)
                        startActivity(productDetailIntent)
                    },
                )
            }
        }
    }
}
