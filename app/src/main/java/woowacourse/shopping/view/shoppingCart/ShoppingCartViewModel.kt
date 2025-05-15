package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.PaginationItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _shoppingCart: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val shoppingCart: LiveData<List<ShoppingCartItem>> get() = _shoppingCart

    private val _event: MutableLiveData<ShoppingCartEvent> = MutableLiveData()
    val event: LiveData<ShoppingCartEvent> get() = _event

    private val _page: MutableLiveData<Int> = MutableLiveData(1)
    val page: LiveData<Int> get() = _page

    fun updateShoppingCart() {
        thread {
            runCatching {
                shoppingCartRepository.load(page.value ?: 1, COUNT_PER_PAGE)
            }.onSuccess { products: List<Product> ->
                _shoppingCart.postValue(
                    products.map(::ProductItem) + PaginationItem(page.value ?: 1),
                )
            }.onFailure {
                _event.postValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE)
            }
        }
    }

    fun removeShoppingCartProduct(product: Product) {
        thread {
            runCatching {
                shoppingCartRepository.remove(product)
            }.onSuccess {
                updateShoppingCart()
            }.onFailure {
                _event.postValue(ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE)
            }
        }
    }

    fun plusPage() {
        _page.value = (page.value ?: 1).plus(1)
        updateShoppingCart()
    }

    fun minusPage() {
        _page.value = (page.value ?: 1).minus(1).coerceAtLeast(1)
        updateShoppingCart()
    }

    companion object {
        private const val COUNT_PER_PAGE: Int = 5
    }
}
