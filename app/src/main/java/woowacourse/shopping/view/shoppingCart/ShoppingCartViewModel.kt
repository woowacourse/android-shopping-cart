package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.PaginationItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _shoppingCart: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val shoppingCart: LiveData<List<ShoppingCartItem>> get() = _shoppingCart

    private val _event: MutableLiveData<ShoppingCartEvent> = MutableLiveData()
    val event: LiveData<ShoppingCartEvent> get() = _event

    private var page: Int = MINIMUM_PAGE

    fun updateShoppingCart() {
        shoppingCartRepository.load(page, COUNT_PER_PAGE) { result ->
            result
                .onSuccess { products: List<Product> ->
                    if (handleLastPage(products)) return@load
                    val paginationItem: PaginationItem = createPaginationItem()
                    _shoppingCart.postValue(products.map(::ProductItem) + paginationItem)
                }
                .onFailure {
                    _event.postValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE)
                }
        }
    }

    private fun handleLastPage(products: List<Product>): Boolean {
        if (products.isEmpty() && page != MINIMUM_PAGE) {
            minusPage()
            updateShoppingCart()
            return true
        }
        return false
    }

    private fun createPaginationItem(): PaginationItem {
        val paginationItem: PaginationItem =
            (shoppingCart.value?.last() as? PaginationItem)?.copy(
                page = page,
                nextEnabled = shoppingCartRepository.hasNext,
                previousEnabled = shoppingCartRepository.hasPrevious
            )
                ?: PaginationItem(
                    page,
                    shoppingCartRepository.hasNext,
                    false,
                )
        return paginationItem
    }

    fun removeShoppingCartProduct(product: Product) {
        shoppingCartRepository.remove(product) { result ->
            result
                .onSuccess {
                    updateShoppingCart()
                }
                .onFailure {
                    _event.postValue(ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE)
                }
        }
    }

    fun plusPage() {
        page++
        updateShoppingCart()
    }

    fun minusPage() {
        page = page.minus(1).coerceAtLeast(MINIMUM_PAGE)
        updateShoppingCart()
    }

    companion object {
        private const val MINIMUM_PAGE = 1
        private const val COUNT_PER_PAGE: Int = 5
    }
}
