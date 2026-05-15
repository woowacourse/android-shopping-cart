package woowacourse.shopping.ui.productlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.data.remote.api.ShoppingMockServer
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ShoppingMockServer.start()

        enableEdgeToEdge()

        setContent {
            AndroidshoppingTheme {
                ProductListRoute(
                    onNavigateToDetail = { id ->
                        startActivity(ProductDetailActivity.Companion.newIntent(this, id))
                    },
                    onNavigateToCart = {
                        startActivity(Intent(this, CartActivity::class.java))
                    },
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ShoppingMockServer.shutdown()
    }
}
