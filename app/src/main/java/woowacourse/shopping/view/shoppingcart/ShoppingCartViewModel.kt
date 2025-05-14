package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product

class ShoppingCartViewModel : ViewModel() {
    private val _productsLiveData: MutableLiveData<List<Product>> = MutableLiveData(
        DummyShoppingCart.products
    )
    val productsLiveData: LiveData<List<Product>> get() = _productsLiveData
    val products: List<Product> get() = _productsLiveData.value ?: emptyList()

    fun addProduct(product: Product) {
        _productsLiveData.value = products + product
    }

    fun removeProduct(product: Product) {
        _productsLiveData.value = products - product
    }
}
