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
    var totalItems: Int = 0
    private val _isLeftPageEnable = MutableLiveData(false)
    val isLeftPageEnable: LiveData<Boolean> get() = _isLeftPageEnable
    private val _isRightPageEnable = MutableLiveData(false)
    val isRightPageEnable: LiveData<Boolean> get() = _isRightPageEnable

    fun getPosition(goods: Goods): Int? {
        val idx = cart.value?.indexOf(goods) ?: return null
        return if (idx >= 0) idx else null
    }

    fun delete(goods: Goods) {
        val endPage = ((totalItems - 1) / PAGE_SIZE) + 1
        if (currentPage == endPage && (totalItems - 1) == ((currentPage - 1) * PAGE_SIZE)) {
            currentPage--
            _page.value = currentPage
        }
        cartRepository.delete(goods)
    }

    fun plusPage() {
        currentPage++
        _page.value = currentPage
    }

    fun minusPage() {
        currentPage--
        _page.value = currentPage
    }

    fun updatePageButton() {
        val endPage = ((totalItems - 1) / PAGE_SIZE) + 1
        when {
            currentPage == 1 -> {
                _isLeftPageEnable.value = false
                _isRightPageEnable.value = currentPage < endPage
            }
            currentPage < endPage -> {
                _isLeftPageEnable.value = currentPage > 1
                _isRightPageEnable.value = currentPage < endPage
            }
            currentPage == endPage -> {
                _isLeftPageEnable.value = currentPage > 1
                _isRightPageEnable.value = false
            }
        }
    }

    private fun getProducts(page: Int): LiveData<List<Goods>> = cartRepository.getPage(PAGE_SIZE, (page - 1) * PAGE_SIZE)

    fun updatePageButtonVisibility() {
        _showPageButton.value = totalItems > PAGE_SIZE
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
