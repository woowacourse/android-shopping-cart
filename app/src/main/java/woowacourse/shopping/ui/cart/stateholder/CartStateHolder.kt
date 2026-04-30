package woowacourse.shopping.ui.cart.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.ui.state.ProductUiModel

class CartStateHolder(var totalCartItems: List<ProductUiModel>) {
    var page by mutableIntStateOf(1)
    var cartItems by mutableStateOf(pagination(1, totalCartItems))

    fun onLeftClick() {
        page -= 1
        cartItems = pagination(page, totalCartItems)
    }

    fun onRightClick() {
        page += 1
        cartItems = pagination(page, totalCartItems)
    }

    private fun pagination(
        page: Int,
        productUiModels: List<ProductUiModel>,
    ): List<ProductUiModel> {
        val toIndex = minOf(page * PAGE_SIZE, productUiModels.size)
        return productUiModels.subList((page - 1) * PAGE_SIZE, toIndex)
    }

    fun deleteCartItem(id: String) {
        totalCartItems = totalCartItems.filter { it.id != id }
        cartItems = pagination(page, totalCartItems)
    }

    companion object {
        const val PAGE_SIZE = 3
    }
}
