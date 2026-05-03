package woowacourse.shopping.ui.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.detail.DetailActivity
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ShoppingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                ShoppingScreen(
                    onProductClick = { id ->
                        val intent = DetailActivity.getIntent(this, id)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
