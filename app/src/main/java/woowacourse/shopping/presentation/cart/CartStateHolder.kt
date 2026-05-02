package woowacourse.shopping.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartStateHolder(
    private val cartRepository: CartRepository,
) {
    private val pageSize = 5

    private var allCart by mutableStateOf(Cart())
    private var pagedCart by mutableStateOf(Cart())
    private var currentPageIndex by mutableStateOf(0)

    val cart: Cart
        get() = pagedCart

    val currentPage: Int
        get() = currentPageIndex + 1

    val totalItemCount: Int
        get() = allCart.cartItems.size

    val lastPageIndex: Int
        get() = if (totalItemCount == 0) 0 else (totalItemCount - 1) / pageSize

    val hasPreviousPage: Boolean
        get() = currentPageIndex > 0

    val hasNextPage: Boolean
        get() = currentPageIndex < lastPageIndex

    val hasMoreItems: Boolean
        get() = totalItemCount > pageSize

    init {
        refresh()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProduct(productId: Uuid) {
        cartRepository.deleteProduct(productId)

        refreshAllCart()
        adjustCurrentPage()
        refreshPagedCart()
    }

    fun goToPreviousPage() {
        if (!hasPreviousPage) return

        currentPageIndex--
        refreshPagedCart()
    }

    fun goToNextPage() {
        if (!hasNextPage) return

        currentPageIndex++
        refreshPagedCart()
    }

    private fun refresh() {
        refreshAllCart()
        adjustCurrentPage()
        refreshPagedCart()
    }

    private fun refreshAllCart() {
        allCart = cartRepository.getItems()
    }

    private fun refreshPagedCart() {
        pagedCart =
            cartRepository.getPagingItems(
                page = currentPageIndex,
                pageSize = pageSize,
            )
    }

    private fun adjustCurrentPage() {
        if (currentPageIndex > lastPageIndex) {
            currentPageIndex = lastPageIndex
        }
    }
}
