package woowacourse.shopping.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener

class ShoppingCartViewModel(
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private var _itemsInCurrentPage =
        MutableLiveData<List<Product>>()
    val itemsInCurrentPage: LiveData<List<Product>> get() = _itemsInCurrentPage

    private var _isLastPage: MutableLiveData<Boolean> = MutableLiveData()
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private var _deletedItemId: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val deletedItemId: SingleLiveData<Int> get() = _deletedItemId

    fun loadAll() {
        _itemsInCurrentPage.value =
            shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
        _isLastPage.value =
            shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())
    }

    fun nextPage() {
        if (isLastPage.value == true) return

        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
        _isLastPage.value =
            shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())

        _itemsInCurrentPage.value =
            shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
    }

    fun previousPage() {
        if (currentPage.value == FIRST_PAGE) return

        _currentPage.value = _currentPage.value?.minus(PAGE_MOVE_COUNT)
        _isLastPage.value =
            shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())

        _itemsInCurrentPage.value =
            shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
    }

    fun deleteItem(cartItemId: Int) {
        shoppingProductsRepository.removeShoppingCartProduct(cartItemId)

        _itemsInCurrentPage.value =
            shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())

        _isLastPage.value =
            shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())
    }

    override fun onClick(productId: Int) {
        _deletedItemId.setValue(productId)
    }

    override fun onIncrease(productId: Int) {
        Log.d(TAG, "onIncrease: called")
        shoppingProductsRepository.increaseShoppingCartProduct(productId)
        _itemsInCurrentPage.value =
            shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
    }

    override fun onDecrease(productId: Int) {
        Log.d(TAG, "onDecrease: called")
        val product = shoppingProductsRepository.loadProduct(productId)
        if (product.quantity == 1) {
            // TODO: 토스트 메시지 수량은 1개 이상이어야 합니다
            return
        }

        shoppingProductsRepository.decreaseShoppingCartProduct(productId)
        _itemsInCurrentPage.value =
            shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
        private const val TAG = "ShoppingCartViewModel"
    }
}
