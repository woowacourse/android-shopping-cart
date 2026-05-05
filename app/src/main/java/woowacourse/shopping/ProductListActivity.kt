package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.shopping.screen.ProductListScreen
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class ProductListActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                ProductListScreen(
                    products = Products(ProductFixture.productList(packageName)),
                    onCartClick = { startActivity(Intent(this, CartActivity::class.java)) },
                    onProductClick = { productId ->
                        ProductDetailActivity.start(this, productId)
                    },
                )
            }
        }
    }
}
