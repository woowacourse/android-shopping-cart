package woowacourse.shopping

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.stateholder.retainCartStateHolder
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    private var cart by mutableStateOf(Cart(CartProducts(emptyList())))

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedCart = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("extra_cart", Cart::class.java)
                } else {
                    result.data?.getParcelableExtra("extra_cart")
                }
                updatedCart?.let { cart = it }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (savedInstanceState != null) {
            val savedCart = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable("extra_cart", Cart::class.java)
            } else {
                @Suppress("DEPRECATION")
                savedInstanceState.getParcelable("extra_cart")
            }
            if (savedCart != null) cart = savedCart
        }

        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stateHolder = retainCartStateHolder()
                    CatalogScreen(
                        catalog = stateHolder.catalog,
                        cartTotalAmount = cart.getTotalQuantity(),
                        onItemClick = { id ->
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("id", id.toString())
                                putExtra("extra_cart", cart)
                            }
                            activityLauncher.launch(intent)
                        },
                        onCartClick = {
                            val intent = Intent(this, CartActivity::class.java).apply {
                                putExtra("extra_cart", cart)
                            }
                            activityLauncher.launch(intent)
                        },
                        onLoadClick = { stateHolder.onLoadClick() },
                        onIncrease = { id ->
                            val product = stateHolder.catalog.find { it.productId == id }
                            if (product != null) {
                                cart = cart.addProduct(product)
                            }
                        },
                        onDecrease = { id ->
                            val cartProduct = cart.cartProducts.findSameProduct(id)
                            if (cartProduct != null) {
                                if (cartProduct.amount > 1) {
                                    cart = cart.decreaseProduct(id)
                                } else {
                                    cart = cart.removeProduct(id)
                                }
                            }
                        },
                        getQuantity = { id ->
                            cart.cartProducts.findSameProduct(id)?.amount ?: 0
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("extra_cart", cart)
    }
}
