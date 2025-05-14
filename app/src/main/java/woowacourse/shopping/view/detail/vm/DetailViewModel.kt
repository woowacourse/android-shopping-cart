package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

class DetailViewModel(
    private val storage: ProductStorage,
) : ViewModel() {
    private val _product = MutableLiveData(Product(0, "", Price(1), ""))
    val product: LiveData<Product> get() = _product

    fun load(productId: Long) {
        _product.value = storage[productId]
    }
}
