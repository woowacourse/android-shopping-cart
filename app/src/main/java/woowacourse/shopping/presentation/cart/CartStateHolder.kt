package woowacourse.shopping.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartStateHolder(
    private val cartRepository: CartRepository,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
) {
    var cart by mutableStateOf(Cart())
        private set

    var currentPageIndex by mutableStateOf(0)
        private set

    val currentPage: Int
        get() = currentPageIndex + 1

    val totalItemCount: Int
        get() = cartRepository.getTotalItemCount()

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
        cartRepository.decreaseQuantity(productId)

        refresh()
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
        adjustCurrentPage()
        refreshPagedCart()
    }

    private fun refreshPagedCart() {
        cart =
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

    companion object {
        private const val DEFAULT_PAGE_SIZE = 5

        val Saver: Saver<CartStateHolder, Int> =
            Saver(
                save = { it.currentPageIndex },
                restore = { CartStateHolder(AppContainer.cartRepository, it) },
            )
    }
}
