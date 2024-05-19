package woowacourse.shopping.productList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.repository.ShoppingProductsRepository

class ProductListViewModel(
    private val productsRepository: ShoppingProductsRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel() {
    val currentPage: LiveData<Int> get() = _currentPage

    private val _loadedProducts: MutableLiveData<List<Product>> =
        MutableLiveData(
            productsRepository.loadPagedItems(currentPage.value ?: FIRST_PAGE),
        )

    val loadedProducts: LiveData<List<Product>>
        get() = _loadedProducts

    private var _isLastPage: MutableLiveData<Boolean> =
        MutableLiveData(
            productsRepository.isFinalPage(currentPage.value ?: FIRST_PAGE),
        )
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    fun loadNextPageProducts() {
        if (isLastPage.value == true) return
        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
        _isLastPage.value = productsRepository.isFinalPage(currentPage.value ?: FIRST_PAGE)

        val result = productsRepository.loadPagedItems(currentPage.value ?: FIRST_PAGE)
        Log.d(TAG, "loadProducts: $result")
        Log.d(TAG, "loadProducts: ${_loadedProducts.value}")

        _loadedProducts.value =
            _loadedProducts.value?.toMutableList()?.apply {
                addAll(result)
            }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
        private const val TAG = "ProductListViewModel2"
    }
}
