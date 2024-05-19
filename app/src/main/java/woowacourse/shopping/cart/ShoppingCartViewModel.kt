package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.data.Product
import woowacourse.shopping.repository.ShoppingCartItemRepository

class ShoppingCartViewModel(
    private val shoppingCartItemRepository: ShoppingCartItemRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel() {
    val currentPage: LiveData<Int> get() = _currentPage

    private var _itemsInCurrentPage =
        MutableLiveData<List<Product>>(
            shoppingCartItemRepository.loadPagedCartItems(currentPage.value ?: currentPageIsNullException()),
        )
    val itemsInCurrentPage: LiveData<List<Product>> get() = _itemsInCurrentPage

    private var _isLastPage: MutableLiveData<Boolean> =
        MutableLiveData(
            shoppingCartItemRepository.isFinalPage(currentPage.value ?: currentPageIsNullException()),
        )
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    fun nextPage() {
        if (isLastPage.value == true) return

        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
        _isLastPage.value = shoppingCartItemRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())

        _itemsInCurrentPage.postValue(
            shoppingCartItemRepository.loadPagedCartItems(
                currentPage.value ?: currentPageIsNullException(),
            ),
        )
    }

    fun previousPage() {
        if (currentPage.value == FIRST_PAGE) return

        _currentPage.value = _currentPage.value?.minus(PAGE_MOVE_COUNT)
        _isLastPage.value = shoppingCartItemRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())

        _itemsInCurrentPage.postValue(
            shoppingCartItemRepository.loadPagedCartItems(
                currentPage.value ?: currentPageIsNullException(),
            ),
        )
    }

    fun deleteItem(cartItemId: Int) {
        shoppingCartItemRepository.removeCartItem(cartItemId)

        _itemsInCurrentPage.postValue(
            shoppingCartItemRepository.loadPagedCartItems(
                currentPage.value ?: currentPageIsNullException(),
            ),
        )

        _isLastPage.value = shoppingCartItemRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
    }
}
