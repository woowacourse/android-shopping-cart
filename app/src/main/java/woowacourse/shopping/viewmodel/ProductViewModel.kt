package woowacourse.shopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class ProductViewModel : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun loadProduct(
        productRepository: ProductRepository,
        productId: Long,
    ) {
        _product.value = productRepository.find(productId)
        // TODO: 잘못된 id 넘어 왔을 때 예외 처리
    }

    fun loadPage(
        productRepository: ProductRepository,
        page: Int,
        pageSize: Int,
    ) {
        _products.value = productRepository.findRange(page, pageSize)
    }
}
