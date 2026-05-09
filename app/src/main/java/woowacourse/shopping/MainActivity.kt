package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.data.database.MockCatalog
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import woowacourse.shopping.ui.viewmodel.ShoppingViewModel
import woowacourse.shopping.ui.viewmodel.ShoppingViewModelFactory
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: ShoppingViewModel = viewModel<ShoppingViewModel>(
                factory = ShoppingViewModelFactory(
                    (application as ShoppingApplication).purchaseProductsRepository,
                    (application as ShoppingApplication).recentlyViewedProductRepository
                )
            )
            val cartState by viewModel.cart.collectAsStateWithLifecycle()
            val viewHistory by viewModel.viewingHistory.collectAsStateWithLifecycle()
            val lastViewedProduct by viewModel.lastViewedProduct.collectAsStateWithLifecycle()

            var currentIndex by rememberSaveable { mutableIntStateOf(0) }
            var currentProducts by rememberSaveable { mutableStateOf(Products()) }

            LaunchedEffect(currentIndex) {
                currentProducts += loadProducts(currentIndex, MAX_PRODUCT)
            }

            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        catalog = currentProducts,
                        recentlyViewedProducts = viewHistory,
                        onRecentlyViewedClick = { product ->
                            viewModel.updateHistory(product)
                            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(IntentKeys.SELECTED_PRODUCT_KEY, product)
                                putExtra(IntentKeys.LATEST_VIEWED_PRODUCT, lastViewedProduct)
                            }
                            startActivity(intent)
                        },
                        onItemClick = { product ->
                            viewModel.updateHistory(product)
                            val intent =
                                Intent(this, ProductDetailActivity::class.java).apply {
                                    putExtra(IntentKeys.SELECTED_PRODUCT_KEY, product)
                                    putExtra(IntentKeys.LATEST_VIEWED_PRODUCT, lastViewedProduct)
                                }
                            startActivity(intent)
                        },
                        onCartClick = {
                            val intent = Intent(this, CartActivity::class.java)
                            startActivity(intent)
                        },
                        onLoadClick = {
                            currentIndex++
                        },
                        modifier = Modifier.padding(innerPadding),
                        onAdd = { id, updateAmount ->
                            viewModel.updateCountWithID(id, updateAmount)
                        },
                        onMinus = { id, updateAmount ->
                            viewModel.updateCountWithID(id, updateAmount)
                        },
                        onDelete = { viewModel.removeWithID(it) },
                        onAddInCart = { viewModel.addPurchaseProduct(it) },
                        isContainedInCart = { cartState.isContain(it) },
                        specificProductCount = { cartState.totalCountOfSpecificPurchaseProduct(it) },
                        totalCount = { cartState.totalCountOfPurchaseProducts() },
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
