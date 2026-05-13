@file:Suppress("FunctionName")

package woowacourse.shopping.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.ProductListScreen
import woowacourse.shopping.ui.component.MoreButton
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import woowacourse.shopping.viewmodel.ProductListViewModel
import woowacourse.shopping.viewmodel.ViewModelFactory

class ProductListActivity : ComponentActivity() {
    private val productListViewModel: ProductListViewModel by viewModels {
        ViewModelFactory((application as ShoppingApplication).appContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                productListViewModel.event.collect { event ->
                    when (event) {
                        is ProductListViewModel.ProductListEvent.NavigateToDetailProduct ->
                            DetailProductActivity.start(
                                context = this@ProductListActivity,
                                productId = event.productId,
                                showLastViewed = event.showLastViewed,
                            )

                        ProductListViewModel.ProductListEvent.NavigateToShoppingCart ->
                            ShoppingCartActivity.start(this@ProductListActivity)
                    }
                }
            }
            AndroidShoppingTheme {
                ProductListScreen(
                    shoppingItems = uiState.shoppingItems,
                    recentViewedShoppingItems = uiState.recentViewedShoppingItems,
                    shoppingCartTotalCount = uiState.shoppingCartTotalCount,
                    isNetworkConnected = uiState.isNetworkConnected,
                    onAddToCartClick = { shoppingItem ->
                        productListViewModel.addProductToCart(shoppingItem)
                    },
                    onQuantityPlusClick = { shoppingItem ->
                        productListViewModel.increaseProductQuantity(shoppingItem)
                    },
                    onQuantityMinusClick = { shoppingItem ->
                        productListViewModel.decreaseProductQuantity(shoppingItem)
                    },
                    onProductClick = productListViewModel::onProductClick,
                    onRecentViewedProductClick = productListViewModel::onRecentViewedProductClick,
                    onNavigateToCartClick = productListViewModel::onNavigateToCartClick,
                    bottomContent =
                        if (uiState.canLoadNextPage) {
                            {
                                MoreButton(
                                    onClick = {
                                        productListViewModel.loadNextPage()
                                    },
                                )
                            }
                        } else {
                            null
                        },
                )
            }
        }
    }
}
