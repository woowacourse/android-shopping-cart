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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import woowacourse.shopping.data.database.MockCatalog
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import kotlin.jvm.java

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

            val startForProductDetailResult =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
                ) { result ->
                    if (result.resultCode == RESULT_OK) {
                        val purchaseProduct = result.data?.getParcelableExtra<PurchaseProduct>(IntentKeys.STORED_PRODUCT_KEY)
                        if (purchaseProduct != null) {
                            cart = cart.add(purchaseProduct)
                        }
                    }
                }

            val startForCartResult =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
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
                            val intent =
                                Intent(this, ProductDetailActivity::class.java).apply {
                                    putExtra(IntentKeys.SELECTED_PRODUCT_KEY, product)
                                }
                            startForProductDetailResult.launch(intent)
                        },
                        onCartClick = {
                            val intent =
                                Intent(this, CartActivity::class.java).apply {
                                    putExtra(IntentKeys.CART_KEY, cart)
                                }
                            startForCartResult.launch(intent)
                        },
                        onLoadClick = {
                            currentIndex++
                        },
                        modifier = Modifier.padding(innerPadding),
                        onAdd = { id, updateAmount ->
                            cart = cart.updateCountWithId(id, updateAmount)
                        },
                        onMinus = { id, countUpdateType ->
                            cart = cart.updateCountWithId(id, countUpdateType)
                        },
                        onDelete = { cart = cart.removeWithId(it) },
                        onAddInCart = { cart = cart.add(it) },
                        isContainedInCart = { cart.isContain(it) },
                        specificProductCount = { cart.totalCountOfSpecificPurchaseProduct(it) },
                        totalCount = { cart.totalCountOfPurchaseProducts() },
                    )
                }
            }
        }
    }

    suspend fun loadProducts(
        currentIndex: Int,
        size: Int,
    ): Products {
        return MockCatalog.loadMoreProducts(currentIndex, size)
    }

    companion object {
        const val MAX_PRODUCT = 20
    }
}
