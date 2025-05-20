package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.PaginationItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private var allShoppingCartItems: List<Product> = emptyList()
    private var page: Int = FIRST_PAGE

    private val _shoppingCartItems: MutableLiveData<List<Product>> = MutableLiveData()
    val shoppingCartItems: LiveData<List<ShoppingCartItem>> =
        _shoppingCartItems.map { it.toShoppingCartItems }

    private val startInclusive: Int
        get() =
            (page.minus(1) * COUNT_PER_PAGE).coerceAtMost(
                allShoppingCartItems.size,
            )
    private val endExclusive: Int
        get() =
            (page * COUNT_PER_PAGE).coerceAtMost(
                allShoppingCartItems.size,
            )

    private val List<Product>.toShoppingCartItems: List<ShoppingCartItem>
        get() {
            val hasNext = endExclusive < allShoppingCartItems.size
            val hasPrevious = page > FIRST_PAGE
            val paginationItem = PaginationItem(page, hasNext, hasPrevious)

            return map(::ProductItem) + paginationItem
        }

    private val _event: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ShoppingCartEvent> get() = _event

    init {
        updateShoppingCart()
    }

    fun updateShoppingCart() {
        runCatching {
            allShoppingCartItems = shoppingCartRepository.load()
            allShoppingCartItems.subList(startInclusive, endExclusive)
        }.onSuccess { products: List<Product> ->
            _shoppingCartItems.value = products
        }.onFailure {
            _event.setValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE)
        }
    }

    fun removeShoppingCartProduct(product: Product) {
        runCatching {
            shoppingCartRepository.remove(product)
        }.onSuccess {
            updateShoppingCart()
        }.onFailure {
            _event.setValue(ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE)
        }
    }

    fun plusPage() {
        page++
        updateShoppingCart()
    }

    fun minusPage() {
        if (page == FIRST_PAGE) {
            _shoppingCartItems.value = emptyList()
            return
        }

        page--
        updateShoppingCart()
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val COUNT_PER_PAGE = 5
    }
}
