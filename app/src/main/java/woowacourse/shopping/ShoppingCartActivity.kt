@file:Suppress("FunctionName")

package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.ui.ShoppingCartScreen
import woowacourse.shopping.ui.component.PageNavigation
import woowacourse.shopping.ui.pagination.ShoppingCartPageStateHolder
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ShoppingCartActivity : ComponentActivity() {
    private val shoppingCartRepository = ShoppingApplication.shoppingCartRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var shoppingCartItems by remember { mutableStateOf(shoppingCartRepository.getShoppingItems()) }
            var savedCurrentPage by rememberSaveable { mutableIntStateOf(0) }
            AndroidShoppingTheme {
                val shoppingCartPageStateHolder =
                    remember(shoppingCartItems) {
                        ShoppingCartPageStateHolder(shoppingCartItems).apply {
                            restoreCurrentPage(savedCurrentPage)
                        }
                    }
                ShoppingCartScreen(
                    shoppingCartItems = shoppingCartPageStateHolder.getItems(),
                    onBackClick = this::finish,
                    onRemoveShoppingItemClick = { shoppingCartItem ->
                        shoppingCartRepository.remove(shoppingCartItem)
                        shoppingCartPageStateHolder.updateItems(shoppingCartRepository.getShoppingItems())
                        savedCurrentPage = shoppingCartPageStateHolder.currentPage
                    },
                ) {
                    PageNavigation(
                        currentPage = shoppingCartPageStateHolder.currentPage,
                        canMoveToPreviousPage = shoppingCartPageStateHolder.canMoveToPreviousPage(),
                        canMoveToNextPage = shoppingCartPageStateHolder.canMoveToNextPage(),
                        onBeforePageClick = {
                            shoppingCartPageStateHolder.beforePage()
                            savedCurrentPage = shoppingCartPageStateHolder.currentPage
                        },
                        onNextPageClick = {
                            shoppingCartPageStateHolder.nextPage()
                            savedCurrentPage = shoppingCartPageStateHolder.currentPage
                        },
                    )
                }
            }
        }
    }
}
