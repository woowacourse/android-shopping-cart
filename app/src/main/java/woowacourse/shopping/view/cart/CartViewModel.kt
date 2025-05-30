package woowacourse.shopping.view.cart

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityController
import woowacourse.shopping.view.ToastMessageHandler

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel(),
    QuantityController,
    ToastMessageHandler {
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

    private val _toastMessage = MutableLiveData<Event<Unit>>()
    override val toastMessage: LiveData<Event<Unit>> = _toastMessage

    private var cachedItems: List<CartItem> = emptyList()

    private fun loadAllItemsAndThen(action: () -> Unit) {
        cartRepository.getAll { result ->
            result
                .onSuccess { allItems ->
                    _cartItems.postValue(allItems)
                    cachedItems = allItems
                    action()
                }.onFailure {
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    init {
        loadAllItemsAndThen {
            loadPage(INITIAL_PAGE)
        }
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
            cartRepository.update(it.product.id, it.quantity) { result ->
                result
                    .onSuccess {
                        return@update
                    }.onFailure {
                        _toastMessage.postValue(Event(Unit))
                    }
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        cartRepository.remove(cartItem.product.id) { result ->
            result
                .onSuccess {
                    loadAllItemsAndThen {
                        if (!existPage()) _currentPageNumber.postValue(minusPageNumber())
                        loadPage(_currentPageNumber.value ?: INITIAL_PAGE)
                    }
                }.onFailure {
                    _toastMessage.postValue(Event(Unit))
                }
        }
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

    private fun getMaxPageNumber(): Int = ((cachedItems.size - ONE_PAGE_COUNT) / PAGE_SIZE) + ONE_PAGE_COUNT

    private fun minusPageNumber(): Int = (_currentPageNumber.value ?: INITIAL_PAGE) - ONE_PAGE_COUNT

    private fun plusPageNumber(): Int = (_currentPageNumber.value ?: INITIAL_PAGE) + ONE_PAGE_COUNT

    private fun checkFirstPage(): Boolean = (_currentPageNumber.value == INITIAL_PAGE)

    private fun checkLastPage(): Boolean {
        val totalPageNumber = getMaxPageNumber()
        return _currentPageNumber.value == totalPageNumber
    }

    private fun checkOnlyOnePage(): Boolean = cachedItems.size <= PAGE_SIZE

    private fun existPage(): Boolean {
        val maxPageNumber = getMaxPageNumber()
        return (_currentPageNumber.value ?: INITIAL_PAGE) <= maxPageNumber
    }

    private fun loadPage(page: Int) {
        val start = (page - ONE_PAGE_COUNT) * PAGE_SIZE
        val end = minOf(start + PAGE_SIZE, cachedItems.size)

        val items = cachedItems.subList(start, end)
        Handler(Looper.getMainLooper()).post {
            _cartItems.value = items
            _currentPageNumber.value = page
            _isOnlyOnePage.value = checkOnlyOnePage()
            _isFirstPage.value = checkFirstPage()
            _isLastPage.value = checkLastPage()
        }
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
                    val application = checkNotNull(extras[APPLICATION_KEY])
                    val context = application.applicationContext

                    val database = ShoppingDatabase.getDatabase(context)
                    val cartDao = database.cartDao()
                    extras.createSavedStateHandle()

                    return CartViewModel(
                        CartRepositoryImpl(cartDao),
                    ) as T
                }
            }
    }
}
