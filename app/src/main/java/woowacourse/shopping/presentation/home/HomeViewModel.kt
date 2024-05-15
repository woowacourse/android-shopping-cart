package woowacourse.shopping.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class HomeViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private var page: Int = 0

    private val _products: MutableLiveData<List<Product>> =
        MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>>
        get() = _products

    private val _productId: MutableLiveData<Long> = MutableLiveData()
    val productId: LiveData<Long>
        get() = _productId

    fun loadProducts() {
        _products.value = productRepository.fetchSinglePage(page++)
    }

    fun navigateToProductDetail(id: Long) {
        _productId.value = id
    }
}
