package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.CartProvider
import woowacourse.shopping.domain.Product
import java.util.UUID
import kotlin.math.min

class CartStateHolder(
    initialPage: Int = 0,
) {
    var cart by mutableStateOf(CartProvider.cart)

    var currentPage by mutableIntStateOf(initialPage)

    fun onPrevious() {
        if (checkPreviousAvailable()) currentPage--
    }

    fun onNext() {
        if (checkNextAvailable()) currentPage++
    }

    fun onDeleteProduct(id: UUID) {
        val removingItem = cart.cartProducts.findWithId(id) ?: return
        CartProvider.removeItem(removingItem)
        cart = CartProvider.cart

        val maxValidPage = if (cart.size() == 0) 0 else (cart.size() - 1) / ONE_PAGE_ITEM_COUNT
        if (currentPage > maxValidPage) currentPage = maxValidPage
    }

    fun checkPreviousAvailable(): Boolean = currentPage > 0

    fun checkNextAvailable(): Boolean = currentPage < (cart.size() - 1) / ONE_PAGE_ITEM_COUNT

    fun isPageable(): Boolean = cart.size() > ONE_PAGE_ITEM_COUNT

    fun getPartedItem(
        page: Int,
        pageSize: Int = ONE_PAGE_ITEM_COUNT,
    ): List<Product> {
        require(page >= 0) { "페이지는 0이상이여야 합니다" }
        require(pageSize > 0) { "페이지 사이즈는 0보다 커야 합니다" }

        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cart.size())
        if (fromIndex >= toIndex || cart.size() == 0) return emptyList()
        return cart.cartProducts.items.subList(fromIndex, toIndex)
    }

    companion object {
        const val ONE_PAGE_ITEM_COUNT = 5
    }
}
