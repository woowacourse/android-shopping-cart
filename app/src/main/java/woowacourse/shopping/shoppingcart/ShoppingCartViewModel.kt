package woowacourse.shopping.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingRepository
import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.productlist.toProductUiModel
import kotlin.math.ceil

class ShoppingCartViewModel(
    private val repository: ShoppingRepository,
) : ViewModel() {
    private val _cartItems: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val cartItems: LiveData<List<ProductUiModel>> get() = _cartItems

    private val _totalSize = MutableLiveData<Int>(0)
    val totalSize: LiveData<Int> get() = _totalSize

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(DEFAULT_CURRENT_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    fun updatePageSize() {
        val totalItemSize = repository.shoppingCartSize()
        _totalSize.value = ceil(totalItemSize / PAGE_SIZE.toDouble()).toInt()
    }

    fun loadCartItems(currentPage: Int) {
        runCatching {
            repository.shoppingCartItems(currentPage - DEFAULT_CURRENT_PAGE, PAGE_SIZE)
        }.onSuccess { shoppingCartItems ->
            Log.d("테스트", "$shoppingCartItems")
            _cartItems.value = shoppingCartItems.map { it.product.toProductUiModel() }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun deleteCartItem(productId: Long) {
        runCatching {
            repository.deleteShoppingCartItem(productId)
        }.onSuccess {
            val currentPage = requireNotNull(_currentPage.value)
            loadCartItems(currentPage)
        }
    }

    fun nextPage() {
        _currentPage.value = _currentPage.value?.inc()
    }

    fun previousPage() {
        _currentPage.value = _currentPage.value?.dec()
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val DEFAULT_CURRENT_PAGE = 1
    }
}
