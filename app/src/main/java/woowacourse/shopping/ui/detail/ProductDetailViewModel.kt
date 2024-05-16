package woowacourse.shopping.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao

class ProductDetailViewModel(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    fun loadProduct(productId: Long) {
        _product.value = productDao.find(productId)
    }

    fun addProductToCart() {
        _product.value?.let { cartDao.save(it) }
    }
}
