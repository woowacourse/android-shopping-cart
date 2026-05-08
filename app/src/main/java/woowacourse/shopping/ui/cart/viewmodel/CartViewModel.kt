package woowacourse.shopping.ui.cart.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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

    var cartProducts by mutableStateOf<List<ProductWithQuantity>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            cartRepository.getCartProducts().collect { updatedCartProducts ->
                cartProducts = updatedCartProducts
                adjustCurrentPage()
            }
        }
    }

    fun lastPageIndex(): Int =
        if (cartProducts.isEmpty()) {
            0
        } else {
            (cartProducts.size - 1) / CART_PAGE_SIZE
        }

    fun visibleProducts(): List<ProductWithQuantity> =
        cartProducts
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
        viewModelScope.launch {
            cartRepository.addProduct(product = product, quantityToAdd = 1)
        }
    }

    fun decraseProduct(productId: Uuid) {
        viewModelScope.launch {
            cartRepository.decreaseProduct(productId = productId, quantityToRemove = 1)
        }
    }

    fun deleteProduct(productId: Uuid) {
        viewModelScope.launch {
            cartRepository.deleteProduct(productId)
        }
    }
}
