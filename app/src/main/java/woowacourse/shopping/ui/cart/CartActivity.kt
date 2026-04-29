package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository
import woowacourse.shopping.ui.theme.ShoppingTheme

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val cartRepo = InMemoryCartRepository
                    var cart by remember { mutableStateOf(cartRepo.showAll()) }

                    CartScreen(
                        cart = cart,
                        modifier = Modifier.padding(innerPadding),
                        onBackClick = ::finish,
                        onDeleteClick = {
                            cartRepo.delete(it)
                            cart = cartRepo.showAll()
                        }
                    )
                }
            }
        }
    }
}
