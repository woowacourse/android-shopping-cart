package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.data.remote.mock.ProductWebServer
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
            val isServerReady by ProductWebServer.isReady.collectAsStateWithLifecycle()

            if (isServerReady) {
                val viewModel: ShoppingViewModel =
                    viewModel<ShoppingViewModel>(
                        factory =
                            ShoppingViewModelFactory(
                                (application as ShoppingApplication).purchaseProductsRepository,
                                (application as ShoppingApplication).recentlyViewedProductRepository,
                                (application as ShoppingApplication).productRepository,
                            ),
                    )
                val cartState by viewModel.cart.collectAsStateWithLifecycle()
                val viewHistory by viewModel.recentlyViewedProducts.collectAsStateWithLifecycle()
                val currentProducts by viewModel.products.collectAsStateWithLifecycle()
                val lastViewedProduct by viewModel.lastViewProductId.collectAsStateWithLifecycle()
                val totalCartCount by viewModel.totalCartCount.collectAsStateWithLifecycle()
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

                AndroidshoppingTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        CatalogScreen(
                            catalog = currentProducts,
                            recentlyViewedProducts = viewHistory,
                            onRecentlyViewedClick = { product ->
                                viewModel.updateHistory(product)
                                val intent =
                                    Intent(this, ProductDetailActivity::class.java).apply {
                                        putExtra(IntentKeys.SELECTED_PRODUCT_ID_KEY, product.id)
                                        putExtra(IntentKeys.LATEST_VIEWED_PRODUCT_ID_KEY, lastViewedProduct)
                                    }
                                startActivity(intent)
                            },
                            onItemClick = { product ->
                                viewModel.updateHistory(product)
                                val intent =
                                    Intent(this, ProductDetailActivity::class.java).apply {
                                        putExtra(IntentKeys.SELECTED_PRODUCT_ID_KEY, product.id)
                                        putExtra(IntentKeys.LATEST_VIEWED_PRODUCT_ID_KEY, lastViewedProduct)
                                    }
                                startActivity(intent)
                            },
                            onCartClick = {
                                val intent = Intent(this, CartActivity::class.java)
                                startActivity(intent)
                            },
                            onLoadClick = {
                                viewModel.loadMore()
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
                            totalCount = { totalCartCount },
                            isLoading = isLoading,
                        )
                    }
                }
            }
        }
    }
}
