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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productDetailIntent = Intent(this, ProductDetailActivity::class.java)
        val cartIntent = Intent(this, CartActivity::class.java)
        var items = ""

        val startForProductDetailResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val uuid = result.data?.getStringExtra("id")
                    items += if (items.isEmpty()) uuid else ",$uuid"
                }
            }

        val startForCartResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    items = result.data?.getStringExtra("id") ?: items
                }
            }

        var currentIndex = 0
        var currentProducts = mutableStateOf(getCurrentProducts(currentIndex, MAX_PRODUCT))

        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        catalog = currentProducts.value,
                        onItemClick = { id ->
                            productDetailIntent.putExtra("id", id.toString())
                            startForProductDetailResult.launch(productDetailIntent)
                        },
                        onCartClick = {
                            cartIntent.putExtra("id", items)
                            startForCartResult.launch(cartIntent)
                        },
                        onLoadClick = {
                            currentIndex++
                            currentProducts.value += getCurrentProducts(currentIndex,MAX_PRODUCT)
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
