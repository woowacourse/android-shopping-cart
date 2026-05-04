package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.shopping.screen.ProductListScreen
import woowacourse.shopping.ui.theme.androidshoppingTheme

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            androidshoppingTheme {
                ProductListScreen(
                    products = Products(ProductFixture.productList(packageName)),
                    onClick = {
                        val intent = Intent(this, CartActivity::class.java)
                        this.startActivity(intent)
                    },
                )
            }
        }
    }
}
