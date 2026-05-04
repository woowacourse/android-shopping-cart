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
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
    initialPageIndex: Int = 0,
    private val onPageIndexChanged: (Int) -> Unit,
) {
    var cart by mutableStateOf(Cart())
        private set

    private var currentPageIndex = initialPageIndex
    private var totalItemCount by mutableStateOf(0)

    val currentPage: Int
        get() = currentPageIndex + 1

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

        refresh()
    }

    fun goToPreviousPage() {
        if (!hasPreviousPage) return

        currentPageIndex--
        onPageIndexChanged(currentPageIndex)
        refreshPagedCart()
    }

    fun goToNextPage() {
        if (!hasNextPage) return

        currentPageIndex++
        onPageIndexChanged(currentPageIndex)
        refreshPagedCart()
    }

    private fun refresh() {
        refreshTotalItemCount()
        adjustCurrentPage()
        refreshPagedCart()
    }

    private fun refreshTotalItemCount() {
        totalItemCount = cartRepository.getTotalItemCount()
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
            onPageIndexChanged(currentPageIndex)
        }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 5
    }
}
