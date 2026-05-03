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
import kotlinx.coroutines.runBlocking
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productDetailIntent = Intent(this, ProductDetailActivity::class.java)
        val cartIntent = Intent(this, CartActivity::class.java)

        var currentIndex = 0
        var currentProducts =
            mutableStateOf(
                runBlocking {
                    getCurrentProducts(currentIndex, MAX_PRODUCT)
                },
            )

        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        catalog = currentProducts.value.value,
                        onItemClick = { id ->
                            productDetailIntent.putExtra("id", id.toString())
                            startActivity(productDetailIntent)
                        },
                        onCartClick = {
                            startActivity(cartIntent)
                        },
                        onLoadClick = {
                            currentIndex++
                            currentProducts.value.value += runBlocking {
                                getCurrentProducts(
                                    currentIndex,
                                    MAX_PRODUCT
                                )
                            }.value
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    suspend fun getCurrentProducts(
        currentIndex: Int,
        size: Int,
    ) = mutableStateOf(MockCatalog.loadMoreProducts(currentIndex, size).await())

    companion object {
        const val MAX_PRODUCT = 20
    }
}
