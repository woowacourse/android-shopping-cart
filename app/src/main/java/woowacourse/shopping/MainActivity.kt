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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.repository.CartRepository
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productDetailIntent = Intent(this, ProductDetailActivity::class.java)
        val cartIntent = Intent(this, CartActivity::class.java)

        val startForProductDetailResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val product =
                        result.data?.getParcelableExtra<Product>(IntentKeys.STORED_PRODUCT_KEY)
                    if (product != null) {
                        CartRepository.addProduct(product)
                    }
                }
            }

        val startForCartResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

        setContent {
            val coroutineScope = rememberCoroutineScope()
            var currentIndex by rememberSaveable { mutableIntStateOf(0) }
            var currentProducts by rememberSaveable { mutableStateOf(Products()) }
            LaunchedEffect(Unit) {
                if(currentProducts.isEmpty()) {
                    currentProducts = loadProducts(currentIndex, MAX_PRODUCT)
                }
            }

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        catalog = currentProducts,
                        onItemClick = { product ->
                            productDetailIntent.putExtra(IntentKeys.SELECTED_PRODUCT_KEY, product)
                            startForProductDetailResult.launch(productDetailIntent)
                        },
                        onCartClick = {
                            startForCartResult.launch(cartIntent)
                        },
                        onLoadClick = {
                            coroutineScope.launch {
                                currentIndex++
                                currentProducts += loadProducts(currentIndex, MAX_PRODUCT)
                            }
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
    ) = MockCatalog.loadMoreProducts(currentIndex, size)

    companion object {
        const val MAX_PRODUCT = 20
    }
}
