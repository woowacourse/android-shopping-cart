package woowacourse.shopping.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products
    private val _showLoadMore: MutableLiveData<Boolean> = MutableLiveData(true)
    val showLoadMore: LiveData<Boolean> = _showLoadMore

    private var currentPage = 0
    private val pageSize = 10

    init {
        fetchData()
    }

    fun fetchData() {
        val firstPage = productRepository.getPagedProducts(currentPage, pageSize)
        _products.value = firstPage
        currentPage++
    }

    fun loadMore() {
        val nextPage = productRepository.getPagedProducts(currentPage, pageSize)
        val currentList = _products.value.orEmpty()
        _products.value = currentList + nextPage
        currentPage++
        _showLoadMore.value = (_products.value?.size ?: 0) < DummyProducts.values.size
    }
}
