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
import androidx.compose.ui.Modifier
import woowacourse.shopping.MockCatalog.catalog
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productDetailIntent = Intent(this, ProductDetailActivity::class.java)
        val cartIntent = Intent(this, CartActivity::class.java)
        var items = ""

        val startForProductDetailResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uuid = result.data?.getStringExtra("id")
                items += if (items.isEmpty()) uuid else ",$uuid"
            }
        }

        val startForCartResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                items = result.data?.getStringExtra("id") ?: items
            }
        }

        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        catalog = catalog,
                        onItemClick = { id ->
                            productDetailIntent.putExtra("id", id.toString())
                            startForProductDetailResult.launch(productDetailIntent)
                        },
                        onCartClick = {
                            cartIntent.putExtra("id", items)
                            startForCartResult.launch(cartIntent)
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
