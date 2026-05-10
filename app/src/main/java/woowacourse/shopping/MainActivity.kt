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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.repository.CatalogProductRepository
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.stateholder.retainCatalogScreenStateHolder
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedCart = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("extra_cart", Cart::class.java)
                } else {
                    result.data?.getParcelableExtra("extra_cart")
                }
                updatedCart?.let { 
                    cart = it
                }
            }
        }

    private var cart by mutableStateOf(Cart(CartProducts(emptyList())))

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
        val catalogRepository = CatalogProductRepository(MockCatalog)

        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stateHolder = retainCatalogScreenStateHolder(catalogRepository, cart)

                    LaunchedEffect(cart) {
                        stateHolder.updateCart(cart)
                    }

                    LaunchedEffect(stateHolder.cart) {
                        cart = stateHolder.cart
                    }

                    CatalogScreen(
                        catalog = stateHolder.uiStates,
                        cartTotalAmount = stateHolder.cart.getTotalQuantity(),
                        onItemClick = { id ->
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra("id", id.toString())
                                putExtra("extra_cart", stateHolder.cart)
                            }
                            activityLauncher.launch(intent)
                        },
                        onCartClick = {
                            val intent = Intent(this, CartActivity::class.java).apply {
                                putExtra("extra_cart", stateHolder.cart)
                            }
                            activityLauncher.launch(intent)
                        },
                        onLoadClick = { stateHolder.onLoadClick() },
                        onIncrease = { id -> stateHolder.onIncrease(id) },
                        onDecrease = { id -> stateHolder.onDecrease(id) },
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
