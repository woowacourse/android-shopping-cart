package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import java.util.UUID
import kotlin.math.min

class CartStateHolder(
    initialCart: Cart,
    initialPage: Int = 0,
) {
    var cart by mutableStateOf(initialCart)

    var currentPage by mutableIntStateOf(initialPage)

    fun onPrevious() {
        if (hasPreviousPage()) currentPage--
    }

    fun onNext() {
        if (hasNextPage()) currentPage++
    }

    fun onDeleteProduct(id: UUID) {
        cart = cart.removeProduct(id)

        val maxValidPage = if (cart.getUniqueItemCount() == 0) 0 else (cart.getUniqueItemCount() - 1) / ONE_PAGE_ITEM_COUNT
        if (currentPage > maxValidPage) currentPage = maxValidPage
    }

    fun hasPreviousPage(): Boolean = currentPage > 0

    fun hasNextPage(): Boolean = currentPage < (cart.getUniqueItemCount() - 1) / ONE_PAGE_ITEM_COUNT

    fun isPageable(): Boolean = cart.getUniqueItemCount() > ONE_PAGE_ITEM_COUNT

    fun getPartedItem(
        page: Int,
        pageSize: Int = ONE_PAGE_ITEM_COUNT,
    ): List<CartProduct> {
        require(page >= 0) { "페이지는 0이상이여야 합니다" }
        require(pageSize > 0) { "페이지 사이즈는 0보다 커야 합니다" }

        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cart.getUniqueItemCount())
        if (fromIndex >= toIndex || cart.getUniqueItemCount() == 0) return emptyList()
        return cart.cartProducts.items.subList(fromIndex, toIndex)
    }

    companion object {
        const val ONE_PAGE_ITEM_COUNT = 5
    }
}
