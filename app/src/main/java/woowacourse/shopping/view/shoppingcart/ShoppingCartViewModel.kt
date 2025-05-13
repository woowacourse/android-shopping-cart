package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Product

class ShoppingCartViewModel : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(listOf())
    val products: LiveData<List<Product>> get() = _products

    fun addProduct(product: Product) {
        _products.value = (_products.value ?: listOf()) + product
    }

    fun removeProduct(product: Product) {
        _products.value = (_products.value ?: listOf()) - product
    }
}
