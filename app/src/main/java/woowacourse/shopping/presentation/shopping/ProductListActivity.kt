package woowacourse.shopping.presentation.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.navigation.IntentKeys
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.shopping.screen.ProductListScreen
import woowacourse.shopping.presentation.theme.androidshoppingTheme

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
                                putExtra(IntentKeys.PRODUCT_ID, productId)
                            }
                        startActivity(intent)
                    },
                )
            }
        }
    }
}
