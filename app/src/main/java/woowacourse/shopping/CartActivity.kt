package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.ui.component.screen.CartScreen
import java.util.UUID

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val ids = intent.getStringExtra("id")
            ?.split(",")
            ?.filter { it.isNotBlank() } ?: emptyList()

        var items by mutableStateOf(ids.map { it ->
            val id = UUID.fromString(it.trim())
            MockCatalog.findProductById(id)
        })

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                CartScreen(
                    cart = Cart(CartProducts(items)),
                    onClose = {
                        val itemsId = items.joinToString(",") { it.uuid.toString() }
                        intent.putExtra("id", itemsId)
                        setResult(RESULT_OK, intent)
                        finish()
                    },
                    onDelete = { it ->
                        val product = MockCatalog.findProductById(it)
                        items = items.minus(product)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                )
            }
        }
    }
}
