package woowacourse.shopping.ui.cart.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.CART_PAGE_SIZE
import woowacourse.shopping.domain.PageRequest
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import woowacourse.shopping.domain.toPage
import woowacourse.shopping.repository.cart.CartRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    var currentPageIndex by mutableStateOf(0)
        private set

    fun lastPageIndex(): Int =
        if (cartRepository
                .getCartProducts()
                .isEmpty()
        ) {
            0
        } else {
            (cartRepository.getCartProducts().size - 1) / CART_PAGE_SIZE
        }

    fun getCartProducts(): List<ProductWithQuantity> = cartRepository.getCartProducts()

    fun visibleProducts(): List<ProductWithQuantity> =
        cartRepository
            .getCartProducts()
            .toPage(PageRequest(index = currentPageIndex, size = CART_PAGE_SIZE))
            .items

    fun canNavigateToRight(): Boolean = currentPageIndex < lastPageIndex()

    fun moveToNextPage() {
        if (canNavigateToRight()) currentPageIndex++
    }

    fun canNavigateToLeft(): Boolean = currentPageIndex > 0

    fun moveToPreviousPage() {
        if (canNavigateToLeft()) currentPageIndex--
    }

    fun adjustCurrentPage() {
        val lastIndex = lastPageIndex()
        if (currentPageIndex > lastIndex) {
            currentPageIndex = lastIndex
        }
    }

    fun addProduct(product: Product) {
        cartRepository.addProduct(product = product, quantityToAdd = 1)
        adjustCurrentPage()
    }

    fun decraseProduct(productId: Uuid) {
        cartRepository.decreaseProduct(productId = productId, quantityToRemove = 1)
        adjustCurrentPage()
    }

    fun deleteProduct(productId: Uuid) {
        cartRepository.deleteProduct(productId)
        adjustCurrentPage()
    }
}
