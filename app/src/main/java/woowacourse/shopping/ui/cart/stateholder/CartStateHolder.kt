package woowacourse.shopping.ui.cart.stateholder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.ui.state.ProductUiModel

@Composable
fun rememberCartStateHolder(initialItems: List<ProductUiModel>): CartStateHolder = rememberSaveable(saver = CartStateHolder.Saver()) {
    CartStateHolder(initialItems)
}

class CartStateHolder(initCartItems: List<ProductUiModel>) {

    var totalCartItems = initCartItems
    var page by mutableIntStateOf(1)
    var cartItems by mutableStateOf(pagination(1, totalCartItems))

    fun isStartPage(): Boolean = page == 1

    fun isEndPage(): Boolean = page >= lastPage()

    private fun lastPage(): Int {
        if (totalCartItems.isEmpty()) return 1
        return (totalCartItems.size + PAGE_SIZE - 1) / PAGE_SIZE
    }

    fun onLeftClick() {
        page -= 1
        cartItems = pagination(page, totalCartItems)
    }

    fun onRightClick() {
        page += 1
        cartItems = pagination(page, totalCartItems)
    }

    private fun pagination(page: Int, productUiModels: List<ProductUiModel>): List<ProductUiModel> {
        val toIndex = minOf(page * PAGE_SIZE, productUiModels.size)
        return productUiModels.subList((page - 1) * PAGE_SIZE, toIndex)
    }

    fun deleteCartItem(id: String) {
        totalCartItems = totalCartItems.filter { it.id != id }
        if (page > lastPage()) {
            page = lastPage()
        }
        cartItems = pagination(page, totalCartItems)
    }

    companion object {
        private const val PAGE_SIZE = 5

        fun Saver(): Saver<CartStateHolder, Any> = listSaver(
            save = { stateHolder ->
                listOf(
                    stateHolder.totalCartItems,
                    stateHolder.page,
                )
            },
            restore = { savedList ->
                val restoredItems = savedList[0] as List<ProductUiModel>
                val restoredPage = savedList[1] as Int

                CartStateHolder(restoredItems).apply {
                    this.page = restoredPage
                    this.cartItems = pagination(this.page, this.totalCartItems)
                }
            },
        )
    }
}
