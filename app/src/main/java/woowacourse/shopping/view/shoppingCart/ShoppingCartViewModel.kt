package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private var allShoppingCartItems: List<CartItem> = emptyList()
    private var page: Int = FIRST_PAGE

    private val _shoppingCartItems: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val shoppingCartItems: LiveData<List<ShoppingCartItem>> get() = _shoppingCartItems

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

    private val List<CartItem>.toShoppingCartItems: List<ShoppingCartItem>
        get() {
            val hasNext = endExclusive < allShoppingCartItems.size
            val hasPrevious = page > FIRST_PAGE
            val paginationItem = ShoppingCartItem.PaginationItem(page, hasNext, hasPrevious)

            return map(ShoppingCartItem::ProductItem) + paginationItem
        }

    private val _event: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ShoppingCartEvent> get() = _event

    init {
        loadShoppingCart()
    }

    fun loadShoppingCart() {
        shoppingCartRepository.load { result: Result<List<CartItem>> ->
            result
                .onSuccess { cartItems: List<CartItem> ->
                    allShoppingCartItems = cartItems
                    _shoppingCartItems.postValue(
                        allShoppingCartItems
                            .subList(
                                startInclusive,
                                endExclusive,
                            ).toShoppingCartItems,
                    )
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.LOAD_SHOPPING_CART_FAILURE)
                }
        }
    }

    fun updateShoppingCart() {
        val productItems: List<ShoppingCartItem.ProductItem> =
            shoppingCartItems.value?.filterIsInstance<ShoppingCartItem.ProductItem>() ?: return

        val cartItemsToUpdate: List<CartItem> =
            productItems
                .map { productItem: ShoppingCartItem.ProductItem ->
                    CartItem(
                        productItem.cartItem.id,
                        productItem.cartItem.name,
                        productItem.cartItem.price,
                        productItem.quantity,
                    )
                }.filter { cartItem -> cartItem.quantity != 0 }

        shoppingCartRepository.update(cartItemsToUpdate) { result ->
            result
                .onSuccess {
                    _event.postValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_PRODUCT_SUCCESS)
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_PRODUCT_FAILURE)
                }
        }
    }

    fun removeShoppingCartProduct(cartItem: CartItem) {
        shoppingCartRepository.remove(cartItem) { result: Result<Unit> ->
            result
                .onSuccess {
                    loadShoppingCart()
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE)
                }
        }
    }

    fun plusPage() {
        page++
        loadShoppingCart()
    }

    fun minusPage() {
        if (page != FIRST_PAGE) {
            page--
        }

        loadShoppingCart()
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val COUNT_PER_PAGE = 5
    }
}
