package woowacourse.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product

class ProductDetailViewModel : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(Product(0, "", "", 0))
    val product: LiveData<Product> get() = _product

    val productsDummyRepository = ProductDummyRepositoryImpl

    fun updateProductDetail(id: Int) {
        _product.value = productsDummyRepository.fetchProductDetail(id)
    }
}
