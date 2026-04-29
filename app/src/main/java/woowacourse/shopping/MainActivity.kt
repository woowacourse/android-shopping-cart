package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.productlist.ProductListScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val context = this
        val productDetailIntent = Intent(context, ProductDetailActivity::class.java)
        val cartIntent = Intent(context, CartActivity::class.java)
        setContent {
            AndroidshoppingTheme {
                ProductListScreen(
                    onProductClick = {
                        context.startActivity(productDetailIntent)
                    },
                    onCartIconClick = {
                        context.startActivity(cartIntent)
                    },
                )
            }
        }
    }
}
