package woowacourse.shopping.ui.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.ui.theme.AndroidshoppingcartTheme

class ShoppingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingcartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val productRepo = InMemoryProductRepository

                    ShoppingScreen(
                        products = productRepo.products,
                        modifier = Modifier.padding(innerPadding),
                        onCartClick = {
                            startActivity(Intent(this, CartActivity::class.java))
                        },
                        onProductClick = {
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("PRODUCT", it)
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
