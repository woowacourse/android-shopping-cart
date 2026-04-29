package woowacourse.shopping.ui.productdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.productdetail.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.productlist.ProductDetailScreen

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingcartTheme {
                ProductDetailScreen(
                    imageUrl = "",
                    title = "임시",
                    price = "1,000원",
                )
            }
        }
    }
}
