package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.PaginationItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _shoppingCart: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val shoppingCart: LiveData<List<ShoppingCartItem>> get() = _shoppingCart

    private val _event: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ShoppingCartEvent> get() = _event

    private var page: Int = FIRST_PAGE

    fun updateShoppingCart() {
        thread {
            runCatching {
                shoppingCartRepository.load(page, COUNT_PER_PAGE)
            }.onSuccess { products: List<Product> ->
                val paginationItem =
                    PaginationItem(
                        page,
                        shoppingCartRepository.hasNext,
                        shoppingCartRepository.hasPrevious,
                    )

                _shoppingCart.postValue(products.map(::ProductItem) + paginationItem)
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
        page++
        updateShoppingCart()
    }

    fun minusPage() {
        if (page == FIRST_PAGE) {
            _shoppingCart.value = emptyList()
            return
        }

        page--
        updateShoppingCart()
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val COUNT_PER_PAGE: Int = 5
    }
}
