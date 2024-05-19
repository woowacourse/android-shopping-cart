package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.repository.ShoppingCartItemRepository

class ShoppingCartViewModel(
    private val shoppingCartItemRepository: ShoppingCartItemRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel() {
    val currentPage: LiveData<Int> get() = _currentPage

    private var _itemsInCurrentPage =
        MutableLiveData<List<Product>>(
            shoppingCartItemRepository.loadPagedCartItems(currentPage.value ?: 1),
        )
    val itemsInCurrentPage: LiveData<List<Product>> get() = _itemsInCurrentPage

    private var _isLastPage: MutableLiveData<Boolean> =
        MutableLiveData(
            shoppingCartItemRepository.isFinalPage(currentPage.value ?: 1),
        )
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    fun nextPage() {
        if (isLastPage.value == true) return

        _currentPage.value = _currentPage.value?.plus(1)
        _isLastPage.value = shoppingCartItemRepository.isFinalPage(currentPage.value ?: 1)

        _itemsInCurrentPage.postValue(
            shoppingCartItemRepository.loadPagedCartItems(
                currentPage.value ?: throw IllegalStateException("currentPage is null"),
            ),
        )
    }

    fun previousPage() {
        if (currentPage.value == FIRST_PAGE) return

        _currentPage.value = _currentPage.value?.minus(1)
        _isLastPage.value = shoppingCartItemRepository.isFinalPage(currentPage.value ?: 1)

        _itemsInCurrentPage.postValue(
            shoppingCartItemRepository.loadPagedCartItems(
                currentPage.value ?: throw IllegalStateException("currentPage is null"),
            ),
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
        private val TAG = ShoppingCartViewModel::class.simpleName
    }
}
