package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.repository.cartRepository.CartRepository
import java.util.UUID
import kotlin.math.min

class CartStateHolder(
    private val cartRepository: CartRepository,
    val coroutineScope: CoroutineScope,
    initialPage: Int = 0,
) {
    var cart by mutableStateOf(cartRepository.cart)
        private set

    var currentPage by mutableIntStateOf(initialPage)

    init {
        coroutineScope.launch {
            cartRepository.cartFlow.collect { newCart ->
                cart = newCart
                val maxValidPage =
                    if (newCart.getUniqueItemCount() == 0) 0 else (newCart.getUniqueItemCount() - 1) / ONE_PAGE_ITEM_COUNT
                if (currentPage > maxValidPage) currentPage = maxValidPage
            }
        }
    }

    fun onPrevious() {
        if (hasPreviousPage()) currentPage--
    }

    fun onNext() {
        if (hasNextPage()) currentPage++
    }

    fun onIncreaseProduct(id: UUID) {
        val cartProduct = cart.cartProducts.findSameProduct(id) ?: return
        coroutineScope.launch {
            cartRepository.addProduct(cartProduct.product, 1)
        }
    }

    fun onDecreaseProduct(id: UUID) {
        val cartProduct = cart.cartProducts.findSameProduct(id) ?: return
        coroutineScope.launch {
            if (cartProduct.amount > 1) {
                cartRepository.decreaseProduct(id, 1)
            } else {
                cartRepository.removeProduct(id)
            }
        }
    }

    fun onDeleteProduct(id: UUID) {
        coroutineScope.launch {
            cartRepository.removeProduct(id)
        }
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
