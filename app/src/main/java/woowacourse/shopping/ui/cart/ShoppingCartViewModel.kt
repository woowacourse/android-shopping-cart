package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.domain.repository.ShoppingCartItemRepository

class ShoppingCartViewModel(
    private val shoppingCartItemRepository: ShoppingCartItemRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(), CartItemRecyclerViewAdapter.OnProductItemClickListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private var _itemsInCurrentPage =
        MutableLiveData<List<ProductData>>(
            shoppingCartItemRepository.loadPagedCartItems(currentPage.value ?: currentPageIsNullException()),
        )
    val itemsInCurrentPage: LiveData<List<ProductData>> get() = _itemsInCurrentPage

    private var _isLastPage: MutableLiveData<Boolean> =
        MutableLiveData(
            shoppingCartItemRepository.isFinalPage(currentPage.value ?: currentPageIsNullException()),
        )
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private var _deletedItemId: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val deletedItemId: SingleLiveData<Int> get() = _deletedItemId

    fun nextPage() {
        if (isLastPage.value == true) return

        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
        _isLastPage.value = shoppingCartItemRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())

        _itemsInCurrentPage.value =
            shoppingCartItemRepository.loadPagedCartItems(
                currentPage.value ?: currentPageIsNullException(),
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

    override fun onClick(productId: Int) {
        _deletedItemId.setValue(productId)
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
    }
}
