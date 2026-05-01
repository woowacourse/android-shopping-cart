package woowacourse.shopping.ui.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.screen.ProductListScreen
import woowacourse.shopping.ui.theme.androidshoppingTheme

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            androidshoppingTheme {
                ProductListScreen(
                    products = Products(ProductFixture.productList),
                    onCartIconClick = {
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    },
                    onItemClick = { productId ->
                        val intent =
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("woowacourse.shopping.product_id", productId)
                            }
                        startActivity(intent)
                    },
                )
            }
        }
    }
}
