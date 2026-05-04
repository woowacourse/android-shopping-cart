package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository
import woowacourse.shopping.ui.theme.ShoppingTheme

class CartActivity : ComponentActivity() {
    val cartRepo = InMemoryCartRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CartScreen(
                        cartRepo = cartRepo,
                        modifier = Modifier.padding(innerPadding),
                        onBackClick = ::finish
                    )
                }
            }
        }
    }
}
