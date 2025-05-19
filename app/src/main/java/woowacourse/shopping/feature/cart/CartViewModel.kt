package woowacourse.shopping.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods
import kotlin.concurrent.thread
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _isMultiplePages = MutableLiveData(false)
    val isMultiplePages: LiveData<Boolean> get() = _isMultiplePages

    private var currentPage: Int = 1
        set(value) {
            field = value
            _page.postValue(value)
        }
    private val _page = MutableLiveData(currentPage)
    val page: LiveData<Int> get() = _page

    private val _cart = MutableLiveData<List<Goods>>()
    val cart: LiveData<List<Goods>> get() = _cart

    private var totalCartSizeData: Int = 0

    private val _isLeftPageEnable = MutableLiveData(false)
    val isLeftPageEnable: LiveData<Boolean> get() = _isLeftPageEnable

    private val _isRightPageEnable = MutableLiveData(false)
    val isRightPageEnable: LiveData<Boolean> get() = _isRightPageEnable

    private val endPage: Int get() = max(1, (totalCartSizeData + PAGE_SIZE - 1) / PAGE_SIZE)

    init {
        updateCartData()
        updateCartDataSize()
    }

    fun getPosition(goods: Goods): Int? {
        val idx = cart.value?.indexOf(goods) ?: return null
        return if (idx >= 0) idx else null
    }

    private fun updateCartData() {
        thread {
            val currentPageCartItems = cartRepository.getPage(PAGE_SIZE, (currentPage - 1) * PAGE_SIZE)
            _cart.postValue(currentPageCartItems)
        }
    }

    private fun updateCartDataSize() {
        thread {
            totalCartSizeData = cartRepository.getAllItemsSize()
            _isMultiplePages.postValue(totalCartSizeData > PAGE_SIZE)
            updatePageMoveAvailability()
        }
    }

    fun delete(goods: Goods) {
        cartRepository.delete(goods) {
            thread {
                totalCartSizeData = cartRepository.getAllItemsSize()
                if (currentPage > endPage) {
                    currentPage = endPage
                }
                _isMultiplePages.postValue(totalCartSizeData > PAGE_SIZE)
                updatePageMoveAvailability()
                updateCartData()
            }
        }
    }

    fun plusPage() {
        currentPage++
        updateCartData()
        updatePageMoveAvailability()
    }

    fun minusPage() {
        currentPage--
        updateCartData()
        updatePageMoveAvailability()
    }

    private fun updatePageMoveAvailability() {
        when {
            currentPage == 1 -> {
                _isLeftPageEnable.postValue(false)
                _isRightPageEnable.postValue(currentPage < endPage)
            }
            currentPage < endPage -> {
                _isLeftPageEnable.postValue(currentPage > 1)
                _isRightPageEnable.postValue(currentPage < endPage)
            }
            currentPage == endPage -> {
                _isLeftPageEnable.postValue(currentPage > 1)
                _isRightPageEnable.postValue(false)
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
