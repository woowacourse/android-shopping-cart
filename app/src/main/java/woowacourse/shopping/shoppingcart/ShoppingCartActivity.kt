package woowacourse.shopping.shoppingcart

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ShoppingCartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                ShoppingCartScreen(
                    onBackClick = {
                        setResult(RESULT_OK)
                        this.finish()
                    },
                )
            }
        }
    }
}
