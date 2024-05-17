package woowacourse.shopping.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _page = MutableLiveData<Int>(INITIALIZE_PAGE)
    val page: LiveData<Int> get() = _page

    private val _showSeeMore = MutableLiveData<Boolean>()
    val showSeeMore: LiveData<Boolean> get() = _showSeeMore

    private var totalProductCount: Int = 0

    fun loadPage() {
        val currentPage = _page.value ?: return
        val loadedPageProducts = productRepository.findRange(currentPage, PAGE_SIZE)

        _products.value = loadedPageProducts
        _page.value = currentPage + 1

        totalProductCount += loadedPageProducts.size
        _showSeeMore.value = false
    }

    fun changeSeeMoreVisibility(lastPosition: Int) {
        _showSeeMore.value = (lastPosition + 1) % PAGE_SIZE == 0 && lastPosition + 1 == totalProductCount
    }

    companion object {
        private const val INITIALIZE_PAGE = 0
        private const val PAGE_SIZE = 20
    }
}
