package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_PAGE_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.UpdateCartItemResult
import woowacourse.shopping.domain.model.UpdateCartItemType
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.exception.NoSuchDataException
import woowacourse.shopping.utils.livedata.MutableSingleLiveData
import woowacourse.shopping.utils.livedata.SingleLiveData
import woowacourse.shopping.view.cart.model.ShoppingCart

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
    ) {
        try {
            shoppingCartRepository.deleteCartItem(cartItemId)
            shoppingCart.deleteProduct(cartItemId)
            _shoppingCartEvent.value =
                ShoppingCartEvent.UpdateProductEvent.DELETE(productId = productId)
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.setValue(
                        ShoppingCartEvent.DeleteShoppingCart.Fail,
                    )

                else ->
                    _errorEvent.setValue(
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
                    _errorEvent.setValue(
                        ShoppingCartEvent.LoadCartItemList.Fail,
                    )

                else ->
                    _errorEvent.setValue(
                        ShoppingCartEvent.ErrorState.NotKnownError,
                    )
            }
        }
    }

    fun increaseCartItem(product: Product) {
        updateCartItem(product, UpdateCartItemType.INCREASE)
    }

    fun decreaseCartItem(product: Product) {
        updateCartItem(product, UpdateCartItemType.DECREASE)
    }

    private fun updateCartItem(
        product: Product,
        updateCartItemType: UpdateCartItemType,
    ) {
        try {
            val updateCartItemResult =
                shoppingCartRepository.updateCartItem(
                    product.id,
                    updateCartItemType,
                )
            when (updateCartItemResult) {
                UpdateCartItemResult.ADD -> throw NoSuchDataException()
                is UpdateCartItemResult.DELETE ->
                    deleteShoppingCartItem(
                        updateCartItemResult.cartItemId,
                        productId = product.id,
                    )

                is UpdateCartItemResult.UPDATED -> {
                    product.updateCartItemCount(updateCartItemResult.cartItemResult.counter.itemCount)
                    _shoppingCartEvent.value =
                        ShoppingCartEvent.UpdateProductEvent.Success(
                            productId = product.id,
                            count = product.cartItemCounter.itemCount,
                        )
                }
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.setValue(ShoppingCartEvent.UpdateProductEvent.Fail)

                else -> _errorEvent.setValue(ShoppingCartEvent.ErrorState.NotKnownError)
            }
        }
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

    companion object {
        private const val MIN_PAGE_COUNT = 1
        private const val INCREMENT_AMOUNT = 1
    }
}
