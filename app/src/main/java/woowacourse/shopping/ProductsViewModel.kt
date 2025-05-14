package woowacourse.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product

class ProductsViewModel : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> get() = _products

    val productsDummyRepository = ProductDummyRepositoryImpl

    fun updateProducts(count: Int) {
        val newProducts = productsDummyRepository.fetchProducts(count, products.value?.lastOrNull()?.id ?: 0)
        _products.value = products.value?.plus(newProducts)
    }
}
