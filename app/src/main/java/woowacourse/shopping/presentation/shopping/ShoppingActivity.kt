package woowacourse.shopping.presentation.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.shopping.ui.ShoppingScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ShoppingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                ShoppingScreen(
                    onNavigateToCart = {
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    },
                )
            }
        }
    }
}
