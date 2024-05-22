package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_PAGE_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.MutableSingleLiveData
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.utils.SingleLiveData
import woowacourse.shopping.view.cart.model.ShoppingCart
import woowacourse.shopping.view.cartcounter.ChangeCartItemResultState
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    var shoppingCart = ShoppingCart()
    private val totalItemSize: Int get() = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE

    private val _currentPage: MutableLiveData<Int> =
        MutableLiveData(MIN_PAGE_COUNT)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _shoppingCartEvent: MutableLiveData<ShoppingCartEvent.SuccessEvent> =
        MutableLiveData()
    val shoppingCartEvent: LiveData<ShoppingCartEvent.SuccessEvent> get() = _shoppingCartEvent

    private val _errorEvent: MutableSingleLiveData<ShoppingCartEvent.ErrorState> =
        MutableSingleLiveData()
    val errorEvent: SingleLiveData<ShoppingCartEvent.ErrorState> get() = _errorEvent

    fun deleteShoppingCartItem(
        cartItemId: Long,
        productId: Long,
        itemCounter: CartItemCounter,
    ) {
        try {
            shoppingCartRepository.deleteCartItem(cartItemId)
            shoppingCart.deleteProduct(cartItemId)
            _shoppingCartEvent.value = ShoppingCartEvent.DeleteShoppingCart.Success
            _shoppingCartEvent.value =
                ShoppingCartEvent.UpdateProductEvent.Success(
                    productId = productId,
                    count = itemCounter.itemCount,
                )
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(
                        ShoppingCartEvent.DeleteShoppingCart.Fail,
                    )

                else ->
                    _errorEvent.postValue(
                        ShoppingCartEvent.ErrorState.NotKnownError,
                    )
            }
        }
    }

    fun loadPagingCartItemList(pagingSize: Int) {
        try {
            val itemSize = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE
            val pagingData = shoppingCartRepository.loadPagingCartItems(itemSize, pagingSize)
            shoppingCart.addProducts(pagingData)
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(
                        ShoppingCartEvent.LoadCartItemList.Fail,
                    )

                else ->
                    _errorEvent.postValue(
                        ShoppingCartEvent.ErrorState.NotKnownError,
                    )
            }
        }
    }

    fun increaseCartItem(product: Product) {
        try {
            when (val cartItemResult = getCartItemResult(product.id)) {
                is CartItemResult.Exists -> {
                    when (cartItemResult.counter.increase()) {
                        ChangeCartItemResultState.Success -> {
                            shoppingCartRepository.updateCartItem(
                                cartItemResult.cartItemId,
                                cartItemResult.counter.itemCount,
                            )
                            product.cartItemCounter.selectItem()
                            product.cartItemCounter.updateCount(cartItemResult.counter.itemCount)
                            _shoppingCartEvent.value =
                                ShoppingCartEvent.UpdateProductEvent.Success(
                                    productId = product.id,
                                    count = product.cartItemCounter.itemCount,
                                )
                        }

                        ChangeCartItemResultState.Fail -> throw NoSuchDataException()
                    }
                }

                CartItemResult.NotExists -> throw NoSuchDataException()
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(ShoppingCartEvent.UpdateProductEvent.Fail)

                else -> _errorEvent.postValue(ShoppingCartEvent.ErrorState.NotKnownError)
            }
        }
    }

    fun decreaseCartItem(product: Product) {
        try {
            when (val cartItemResult = getCartItemResult(product.id)) {
                is CartItemResult.Exists -> {
                    when (product.cartItemCounter.decrease()) {
                        ChangeCartItemResultState.Success -> {
                            shoppingCartRepository.updateCartItem(
                                cartItemResult.cartItemId,
                                product.cartItemCounter.itemCount,
                            )
                            _shoppingCartEvent.value =
                                ShoppingCartEvent.UpdateProductEvent.Success(
                                    productId = product.id,
                                    count = product.cartItemCounter.itemCount,
                                )
                        }

                        ChangeCartItemResultState.Fail ->
                            deleteShoppingCartItem(
                                cartItemResult.cartItemId,
                                itemCounter = cartItemResult.counter,
                                productId = product.id,
                            )
                    }
                }

                CartItemResult.NotExists -> throw NoSuchDataException()
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(ShoppingCartEvent.UpdateProductEvent.Fail)

                else -> _errorEvent.postValue(ShoppingCartEvent.ErrorState.NotKnownError)
            }
        }
    }

    fun updateShoppingCart(cartItem: CartItem) {
        thread {
            shoppingCartRepository.updateCartItem(
                cartItem.id,
                cartItem.product.cartItemCounter.itemCount,
            )
        }.join()
    }

    fun getUpdatePageData(): List<CartItem> {
        val startIndex =
            ((currentPage.value ?: MIN_PAGE_COUNT) - MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE
        val endIndex = getLastItemIndex()
        return shoppingCart.cartItems.value?.subList(startIndex, endIndex)
            ?: emptyList()
    }

    fun hasLastItem(): Boolean {
        val endIndex = getLastItemIndex()
        return endIndex >= (totalItemSize)
    }

    fun isExistPrevPage(): Boolean {
        return (currentPage.value ?: MIN_PAGE_COUNT) > MIN_PAGE_COUNT
    }

    fun isExistNextPage(): Boolean {
        return (currentPage.value ?: MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE < totalItemSize
    }

    fun increaseCurrentPage() {
        _currentPage.value = _currentPage.value?.plus(INCREMENT_AMOUNT)
    }

    fun decreaseCurrentPage() {
        _currentPage.value = _currentPage.value?.minus(INCREMENT_AMOUNT)
    }

    private fun getLastItemIndex(): Int {
        return minOf(
            (currentPage.value ?: MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE,
            totalItemSize,
        )
    }

    private fun getCartItemResult(productId: Long): CartItemResult {
        return shoppingCartRepository.getCartItemResultFromProductId(productId = productId)
    }

    companion object {
        private const val MIN_PAGE_COUNT = 1
        private const val INCREMENT_AMOUNT = 1
    }
}
