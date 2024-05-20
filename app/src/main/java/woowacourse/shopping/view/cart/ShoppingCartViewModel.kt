package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val repository: ProductRepository,
) : ViewModel(), ShoppingCartActionHandler {
    private var shoppingCart = ShoppingCart()

    private val _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _isPrevButtonActivated: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPrevButtonActivated: LiveData<Boolean> get() = _isPrevButtonActivated

    private val _isNextButtonActivated: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNextButtonActivated: LiveData<Boolean> get() = _isNextButtonActivated

    private val _pagedData: MutableLiveData<List<CartItem>> = MutableLiveData(emptyList())
    val pagedData: LiveData<List<CartItem>> get() = _pagedData

    init {
        loadCartItems()
        updatePagedData()
    }

    private fun loadCartItems() {
        var pagingData = emptyList<CartItem>()
        thread {
            val itemSize = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE
            pagingData = repository.loadPagingCartItems(itemSize, CART_ITEM_LOAD_PAGING_SIZE)
        }.join()

        if (pagingData.isNotEmpty()) {
            shoppingCart.addProducts(pagingData)
            updateButtonState()
        }
    }

    override fun onRemoveCartItemButtonClicked(cartItemId: Long) {
        thread { repository.deleteCartItem(cartItemId) }.join()
        shoppingCart.deleteProduct(cartItemId)
        updatePagedData()
    }

    override fun onPreviousPageButtonClicked() {
        _currentPage.value = _currentPage.value?.minus(1)
        updatePagedData()
    }

    override fun onNextPageButtonClicked() {
        _currentPage.value = _currentPage.value?.plus(1)
        updatePagedData()
    }

    private fun updatePagedData() {
        val pageNumber = currentPage.value ?: 1
        val startIndex = (pageNumber - MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE
        if (shouldLoadCartItems(pageNumber)) {
            loadCartItems()
        }
        val endIndex = minOf(pageNumber * CART_ITEM_PAGE_SIZE, shoppingCart.getItemCount())
        val newData = shoppingCart.cartItems.value?.subList(startIndex, endIndex) ?: emptyList()
        _pagedData.value = newData

        updateButtonState()
    }

    private fun shouldLoadCartItems(pageNumber: Int): Boolean {
        return pageNumber * CART_ITEM_PAGE_SIZE > shoppingCart.getItemCount()
    }

    private fun updateButtonState() {
        val pageNumber = currentPage.value ?: 1
        _isPrevButtonActivated.value = pageNumber > MIN_PAGE_COUNT
        _isNextButtonActivated.value = repository.hasNextPage(pageNumber, CART_ITEM_PAGE_SIZE)
    }

    companion object {
        private const val DEFAULT_ITEM_SIZE = 0
        private const val CART_ITEM_LOAD_PAGING_SIZE = 5
        private const val CART_ITEM_PAGE_SIZE = 3
        private const val MIN_PAGE_COUNT = 1
    }
}
