package woowacourse.shopping.ui.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.theme.ShoppingTheme

private const val PAGE_SIZE = 20

class ShoppingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val productRepo = InMemoryProductRepository

                    var visibleCount by rememberSaveable { mutableIntStateOf(PAGE_SIZE) }
                    val visibleProducts = productRepo.getProducts(0, visibleCount).toList()
                    val hasNext = productRepo.hasNext(visibleProducts.size - 1)

                    ShoppingScreen(
                        products = Products(visibleProducts),
                        hasNext = hasNext,
                        modifier = Modifier.padding(innerPadding),
                        onCartClick = {
                            startActivity(Intent(this, CartActivity::class.java))
                        },
                        onProductClick = {
                            val intent =
                                Intent(this, ProductDetailActivity::class.java).apply {
                                    putExtra("PRODUCT", it)
                                }
                            startActivity(intent)
                        },
                        onMoreClick = {
                            visibleCount = minOf(visibleCount + PAGE_SIZE, productRepo.size)
                        },
                    )
                }
            }
        }
    }
}
