package woowacourse.shopping.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _showPageButton = MutableLiveData(false)
    val showPageButton: LiveData<Boolean> get() = _showPageButton
    private var currentPage: Int = 1
    private val _page = MutableLiveData(currentPage)
    val page: LiveData<Int> get() = _page
    val cart: LiveData<List<Goods>> =
        _page.switchMap { pageNum ->
            getProducts(pageNum)
        }
    val totalItemsCount: LiveData<Int> = cartRepository.getAllItemsSize()
    private val _isLeftPageEnable = MutableLiveData(false)
    val isLeftPageEnable: LiveData<Boolean> get() = _isLeftPageEnable
    private val _isRightPageEnable = MutableLiveData(false)
    val isRightPageEnable: LiveData<Boolean> get() = _isRightPageEnable

    fun delete(goods: Goods) {
        val total = totalItemsCount.value ?: 0
        val endPage = ((total - 1) / PAGE_SIZE) + 1

        if (currentPage == endPage && (total - 1) == ((currentPage - 1) * PAGE_SIZE)) {
            currentPage--
            _page.value = currentPage
        }

        cartRepository.delete(goods)
        updatePageButtonStates()
    }

    fun plusPage() {
        currentPage++
        _page.value = currentPage
    }

    fun minusPage() {
        currentPage--
        _page.value = currentPage
    }

    fun updatePageButtonStates() {
        val total = totalItemsCount.value ?: 0
        val endPage = ((total - 1) / PAGE_SIZE) + 1

        val leftEnabled = currentPage > 1
        val rightEnabled = currentPage < endPage
        val showButtons = total > PAGE_SIZE

        _isLeftPageEnable.value = leftEnabled
        _isRightPageEnable.value = rightEnabled
        _showPageButton.value = showButtons
    }

    private fun getProducts(page: Int): LiveData<List<Goods>> = cartRepository.getPage(PAGE_SIZE, (page - 1) * PAGE_SIZE)

    companion object {
        private const val PAGE_SIZE = 5
    }
}
