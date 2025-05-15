package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.domain.Product

class DetailViewModel(
    private val productStorage: ProductStorage,
    private val cartStorage: CartStorage,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    fun load(productId: Long) {
        _product.value = productStorage[productId]
    }

    fun addProduct() {
        product.value?.let {
            cartStorage.insert(it)
        }
    }
}
