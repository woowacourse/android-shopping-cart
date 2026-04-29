@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.shopping.repository.MemoryShoppingCartRepository
import woowacourse.shopping.ui.ShoppingCartScreen
import woowacourse.shopping.ui.pagination.ShoppingCartPaginationStateHolder
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ShoppingCartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var shoppingCartItems by remember { mutableStateOf(MemoryShoppingCartRepository.getShoppingItems()) }
            AndroidShoppingTheme {
                val shoppingCartPaginationStateHolder =
                    remember(shoppingCartItems) {
                        ShoppingCartPaginationStateHolder(shoppingCartItems)
                    }
                ShoppingCartScreen(
                    shoppingCartItems = shoppingCartPaginationStateHolder.getItems(),
                    onBackClick = {
                        startActivity(Intent(this, ProductListActivity::class.java))
                    },
                    onRemoveShoppingItemClick = { shoppingCartItem ->
                        MemoryShoppingCartRepository.remove(shoppingCartItem)
                        shoppingCartItems = MemoryShoppingCartRepository.getShoppingItems()
                    },
                    currentPage = shoppingCartPaginationStateHolder.currentPage,
                    canMoveToPreviousPage = shoppingCartPaginationStateHolder.canMoveToPreviousPage(),
                    canMoveToNextPage = shoppingCartPaginationStateHolder.canMoveToNextPage(),
                    onBeforePageClick = shoppingCartPaginationStateHolder::beforePage,
                    onNextPageClick = shoppingCartPaginationStateHolder::nextPage,
                )
            }
        }
    }
}
