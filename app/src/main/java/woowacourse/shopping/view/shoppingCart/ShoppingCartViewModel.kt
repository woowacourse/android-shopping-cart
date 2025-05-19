package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.MutableSingleLiveData
import woowacourse.shopping.view.common.SingleLiveData
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.PaginationItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _shoppingCart: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val shoppingCart: LiveData<List<ShoppingCartItem>> get() = _shoppingCart

    private val _event: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ShoppingCartEvent> get() = _event

    private var page: Int = MINIMUM_PAGE
    private var hasPreviousPage: Boolean = false
    private var hasNextPage: Boolean = false

    fun updateShoppingCart() {
        val offset = (page - 1) * COUNT_PER_PAGE
        val limit = COUNT_PER_PAGE + 1
        shoppingCartRepository.load(offset, limit) { result ->
            result
                .onSuccess { products ->
                    if (handleEmptyPage(products)) return@load

                    updatePaginationState(products)

                    val items = createShoppingCartItems(products)
                    _shoppingCart.postValue(items)
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE)
                }
        }
    }

    private fun handleEmptyPage(products: List<Product>): Boolean =
        if (products.isEmpty() && page != MINIMUM_PAGE) {
            minusPage()
            true
        } else {
            false
        }

    private fun updatePaginationState(products: List<Product>) {
        hasNextPage = products.size > COUNT_PER_PAGE
        hasPreviousPage = page > MINIMUM_PAGE
    }

    private fun createShoppingCartItems(products: List<Product>): List<ShoppingCartItem> {
        val visibleProducts = products.take(COUNT_PER_PAGE)
        val paginationItem =
            PaginationItem(
                page = page,
                nextEnabled = hasNextPage,
                previousEnabled = hasPreviousPage,
            )

        return visibleProducts.map(::ProductItem) + paginationItem
    }

    fun removeShoppingCartProduct(product: Product) {
        shoppingCartRepository.remove(product) { result ->
            result
                .onSuccess {
                    updateShoppingCart()
                }.onFailure {
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
