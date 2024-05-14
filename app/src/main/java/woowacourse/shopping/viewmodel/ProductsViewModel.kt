package woowacourse.shopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.model.Product

class ProductsViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun update(productRepository: ProductRepository) {
        _products.value = productRepository.findAll()
    }
}
