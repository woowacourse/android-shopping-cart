package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productDetailIntent = Intent(this, ProductDetailActivity::class.java)
        val cartIntent = Intent(this, CartActivity::class.java)
        var cart = Cart()

        val startForProductDetailResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val product = result.data?.getParcelableExtra<Product>(IntentKeys.PRODUCT_KEY)
                    if (product != null) {
                        cart = cart.addProduct(product)
                    }
                }
            }

        val startForCartResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    cart = result.data?.getParcelableExtra(IntentKeys.CART_KEY) ?: Cart()
                }
            }

        setContent {
            var currentIndex by rememberSaveable { mutableIntStateOf(0) }
            var currentProducts by rememberSaveable { mutableStateOf(getCurrentProducts(currentIndex, MAX_PRODUCT)) }

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        catalog = currentProducts,
                        onItemClick = { product ->
                            productDetailIntent.putExtra(IntentKeys.PRODUCT_KEY, product)
                            startForProductDetailResult.launch(productDetailIntent)
                        },
                        onCartClick = {
                            cartIntent.putExtra(IntentKeys.CART_KEY, cart)
                            startForCartResult.launch(cartIntent)
                        },
                        onLoadClick = {
                            currentIndex++
                            currentProducts += getCurrentProducts(currentIndex, MAX_PRODUCT)
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    fun getCurrentProducts(
        currentIndex: Int,
        size: Int,
    ) = MockCatalog.loadMoreProducts(currentIndex, size)

    companion object {
        const val MAX_PRODUCT = 20
    }
}
