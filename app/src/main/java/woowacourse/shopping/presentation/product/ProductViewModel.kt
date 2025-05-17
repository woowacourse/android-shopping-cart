package woowacourse.shopping.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products
    private val _showLoadMore: MutableLiveData<Boolean> = MutableLiveData(true)
    val showLoadMore: LiveData<Boolean> get() = _showLoadMore

    private var currentPage = 0
    private val pageSize = 10

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

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer { ProductViewModel(ShoppingApplication.provideProductRepository()) }
            }
    }
}
