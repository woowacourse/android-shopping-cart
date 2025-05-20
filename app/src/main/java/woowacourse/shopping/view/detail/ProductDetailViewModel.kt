package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product

class ProductDetailViewModel : ViewModel() {
    private val _productLiveData: MutableLiveData<Product> = MutableLiveData()
    val productLiveData: LiveData<Product> get() = _productLiveData

    fun addProduct(product: Product) {
        DummyShoppingCart.products.add(0, product)
    }

    fun setProduct(product: Product) {
        _productLiveData.value = product
    }
}
