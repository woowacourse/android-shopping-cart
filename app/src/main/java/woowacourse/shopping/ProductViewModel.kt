package woowacourse.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    fun load(
        productRepository: ProductRepository,
        productId: Long,
    ) {
        _product.value = productRepository.find(productId)
        // TODO: 잘못된 id 넘어 왔을 때 예외 처리
    }
}
