@file:Suppress("FunctionName")

package woowacourse.shopping.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.ShoppingCartScreen
import woowacourse.shopping.ui.component.PageNavigation
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import woowacourse.shopping.viewmodel.ShoppingCartItemViewModel
import woowacourse.shopping.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
class ShoppingCartActivity : ComponentActivity() {
    private val shoppingCartItemViewModel: ShoppingCartItemViewModel by viewModels {
        ViewModelFactory((application as ShoppingApplication).appContainer)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ShoppingCartActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val shoppingCartItemsState by shoppingCartItemViewModel.shoppingCartItems.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                shoppingCartItemViewModel.event.collect { event ->
                    when (event) {
                        ShoppingCartItemViewModel.ShoppingCartEvent.NavigateBack -> finish()
                    }
                }
            }
            AndroidShoppingTheme {
                ShoppingCartScreen(
                    shoppingCartItems = shoppingCartItemsState.pagedItems,
                    getQuantityPrice = shoppingCartItemViewModel::getQuantityPrice,
                    onBackClick = shoppingCartItemViewModel::onBackClick,
                    onRemoveShoppingItemClick = { shoppingCartItem ->
                        shoppingCartItemViewModel.removeShoppingItem(shoppingCartItem)
                    },
                    onIncreaseShoppingItemQuantityClick = { shoppingCartItem ->
                        shoppingCartItemViewModel.increaseShoppingItemQuantity(shoppingCartItem)
                    },
                    onDecreaseShoppingItemQuantityClick = { shoppingCartItem ->
                        shoppingCartItemViewModel.decreaseShoppingItemQuantity(shoppingCartItem)
                    },
                ) {
                    PageNavigation(
                        currentPage = shoppingCartItemsState.currentPage,
                        canMoveToPreviousPage = shoppingCartItemsState.canMoveToPreviousPage,
                        canMoveToNextPage = shoppingCartItemsState.canMoveToNextPage,
                        onBeforePageClick = {
                            shoppingCartItemViewModel.moveToPreviousPage()
                        },
                        onNextPageClick = {
                            shoppingCartItemViewModel.moveToNextPage()
                        },
                    )
                }
            }
        }
    }
}
