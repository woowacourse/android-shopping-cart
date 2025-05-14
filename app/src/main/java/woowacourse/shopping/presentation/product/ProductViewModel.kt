package woowacourse.shopping.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.domain.Product

class ProductViewModel : ViewModel() {
    private val productRepositoryImpl =
        ProductRepositoryImpl(ShoppingApplication().providerCartDao())

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    fun fetchData() {
        _products.value = productRepositoryImpl.getProducts()
    }
}
