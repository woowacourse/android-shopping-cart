package woowacourse.shopping.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartsImpl
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductsImpl

class ProductDetailViewModel : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    fun loadProduct(productId: Long) {
        _product.value = ProductsImpl.find(productId)
    }

    fun addProductToCart() {
        _product.value?.let { CartsImpl.save(it) }
    }
}
