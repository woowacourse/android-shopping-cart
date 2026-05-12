package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.productdetail.ProductDetailActivity
import woowacourse.shopping.feature.productlist.ProductListScreen
import woowacourse.shopping.feature.productlist.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val toCartIntent = Intent(this, CartActivity::class.java)

        setContent {
            AndroidshoppingTheme {
                ProductListScreen(
                    onProductClick = { id, recentProductId ->
                        val toProductDetailIntent = ProductDetailActivity.newIntent(
                            context = this,
                            id = id,
                            recentProductId = recentProductId,
                        )
                        startActivity(toProductDetailIntent)
                    },
                    onCartIconClick = {
                        startActivity(toCartIntent)
                    },
                    activityFinish = { finish() },
                )
            }
        }
    }
}
