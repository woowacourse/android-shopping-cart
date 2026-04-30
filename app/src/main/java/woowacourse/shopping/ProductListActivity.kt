package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.shopping.screen.productListScreen
import woowacourse.shopping.ui.theme.androidshoppingTheme

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            androidshoppingTheme {
                productListScreen(
                    products = Products(ProductFixture.productList),
                )
            }
        }
    }
}
