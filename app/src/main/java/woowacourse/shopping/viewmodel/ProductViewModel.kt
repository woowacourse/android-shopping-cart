package woowacourse.shopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun loadProduct(productId: Long) {
        _product.value = productRepository.find(productId)
    }

    fun loadPage(
        page: Int,
        pageSize: Int,
    ) {
        _products.value = productRepository.findRange(page, pageSize)
    }
}
