package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    companion object {
        fun getIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                CartScreen()
            }
        }
    }
}
