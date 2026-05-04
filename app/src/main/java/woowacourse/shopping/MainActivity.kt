package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import kotlin.jvm.java
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var cart by rememberSaveable { mutableStateOf(Cart()) }
            var currentIndex by rememberSaveable { mutableIntStateOf(0) }
            var currentProducts by rememberSaveable { mutableStateOf(Products()) }

            LaunchedEffect(currentIndex) {
                currentProducts += loadProducts(currentIndex, MAX_PRODUCT)
            }

            val startForProductDetailResult = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val product = result.data?.getParcelableExtra<Product>(IntentKeys.STORED_PRODUCT_KEY)
                    if (product != null) {
                        cart = cart.addProduct(product)
                    }
                }
            }

            val startForCartResult = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val updatedCart = result.data?.getParcelableExtra<Cart>(IntentKeys.CART_KEY)
                    if (updatedCart != null) {
                        cart = updatedCart
                    }
                }
            }

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        catalog = currentProducts,
                        onItemClick = { product ->
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(IntentKeys.SELECTED_PRODUCT_KEY, product)
                            }
                            startForProductDetailResult.launch(intent)
                        },
                        onCartClick = {
                            val intent = Intent(this, CartActivity::class.java).apply {
                                putExtra(IntentKeys.CART_KEY, cart)
                            }
                            startForCartResult.launch(intent)
                        },
                        onLoadClick = {
                            currentIndex++
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    suspend fun loadProducts(
        currentIndex: Int,
        size: Int,
    ): Products {
        delay(2.seconds)
        return MockCatalog.loadMoreProducts(currentIndex, size)
    }

    companion object {
        const val MAX_PRODUCT = 20
    }
}
