package woowacourse.shopping.feature.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.feature.cart.component.CartScreen
import woowacourse.shopping.feature.productlist.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AndroidshoppingTheme {
                CartScreen(
                    onCloseClick = { finish() },
                    activityFinish = { finish() },
                )
            }
        }
    }
}
