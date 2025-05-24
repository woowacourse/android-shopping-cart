package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityController

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel(),
    QuantityController {
    private val _currentPageNumber = MutableLiveData(INITIAL_PAGE)
    val currentPageNumber: LiveData<Int> = _currentPageNumber

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _isOnlyOnePage = MutableLiveData<Boolean>()
    val isOnlyOnePage: LiveData<Boolean> = _isOnlyOnePage

    private val _isFirstPage = MutableLiveData<Boolean>()
    val isFirstPage: LiveData<Boolean> = _isFirstPage

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val _finishCart = MutableLiveData<Event<Unit>>()
    val finishCart: LiveData<Event<Unit>> = _finishCart

    init {
        loadPage(INITIAL_PAGE)
    }

    override fun increaseQuantity(
        productId: Long,
        quantityIncrease: Int,
    ) {
        _cartItems.value =
            _cartItems.value?.map {
                if (it.product.id == productId) {
                    val newQuantity = it.quantity + quantityIncrease
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            }
    }

    override fun decreaseQuantity(
        productId: Long,
        quantityDecrease: Int,
        minQuantity: Int,
    ) {
        _cartItems.value =
            _cartItems.value?.map {
                if (it.product.id == productId && it.quantity > minQuantity) {
                    val newQuantity = it.quantity - quantityDecrease
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            }
    }

    override fun updateQuantity() {
        _cartItems.value?.forEach {
            cartRepository.update(it.product.id, it.quantity)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        cartRepository.remove(cartItem.product.id)
        _cartItems.value = cartRepository.getAll()
        if (!existPage()) _currentPageNumber.value = minusPageNumber()

        loadPage(_currentPageNumber.value ?: INITIAL_PAGE)
    }

    fun loadNextPage() {
        val nextPage = plusPageNumber()
        val maxPage = getMaxPageNumber()
        if (nextPage <= maxPage) {
            loadPage(nextPage)
        }
    }

    fun loadPreviousPage() {
        val prevPage = minusPageNumber()
        if (prevPage >= INITIAL_PAGE) {
            loadPage(prevPage)
        }
    }

    fun finishCart() {
        _finishCart.value = Event(Unit)
    }

    private fun getMaxPageNumber(): Int = ((cartRepository.getAll().size - ONE_PAGE_COUNT) / PAGE_SIZE) + ONE_PAGE_COUNT

    private fun minusPageNumber(): Int = (_currentPageNumber.value ?: INITIAL_PAGE) - ONE_PAGE_COUNT

    private fun plusPageNumber(): Int = (_currentPageNumber.value ?: INITIAL_PAGE) + ONE_PAGE_COUNT

    private fun checkFirstPage(): Boolean = (_currentPageNumber.value == INITIAL_PAGE)

    private fun checkLastPage(): Boolean {
        val totalPageNumber = getMaxPageNumber()
        return _currentPageNumber.value == totalPageNumber
    }

    private fun checkOnlyOnePage(): Boolean = cartRepository.getAll().size <= PAGE_SIZE

    private fun existPage(): Boolean {
        val maxPageNumber = getMaxPageNumber()
        return (_currentPageNumber.value ?: INITIAL_PAGE) <= maxPageNumber
    }

    private fun loadPage(page: Int) {
        val start = (page - ONE_PAGE_COUNT) * PAGE_SIZE
        val end = minOf(start + PAGE_SIZE, cartRepository.getAll().size)

        val items = cartRepository.fetchProducts(start, end)
        _cartItems.postValue(items)
        _currentPageNumber.value = page
        _isOnlyOnePage.value = checkOnlyOnePage()
        _isFirstPage.value = checkFirstPage()
        _isLastPage.value = checkLastPage()
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 1

        private const val ONE_PAGE_COUNT = 1
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    checkNotNull(extras[APPLICATION_KEY])
                    extras.createSavedStateHandle()

                    return CartViewModel(
                        CartRepositoryImpl,
                    ) as T
                }
            }
    }
}
