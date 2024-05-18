package woowacourse.shopping.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _showLoadMore = MutableLiveData<Boolean>(false)
    val showLoadMore: LiveData<Boolean> get() = _showLoadMore

    private var page: Int = INITIALIZE_PAGE

    init {
        loadPage()
    }

    fun loadPage() {
        val currentProducts = _products.value ?: emptyList()
        _products.value = currentProducts + productRepository.findRange(page++, PAGE_SIZE)
        _showLoadMore.value = false
    }

    fun changeSeeMoreVisibility(lastPosition: Int) {
        _showLoadMore.value = (lastPosition + 1) % PAGE_SIZE == 0 && lastPosition + 1 == _products.value?.size
    }

    companion object {
        private const val INITIALIZE_PAGE = 0
        private const val PAGE_SIZE = 20
    }
}
