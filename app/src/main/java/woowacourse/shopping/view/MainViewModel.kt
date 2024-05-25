package woowacourse.shopping.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product

class MainViewModel : ViewModel() {
    private val _updateProductEvent = MutableLiveData<Event<ProductUpdate>>()
    val updateProductEvent: LiveData<Event<ProductUpdate>> = _updateProductEvent

    private val _updatedRecentProduct = MutableLiveData<Event<Product>>()
    val updatedRecentProduct: LiveData<Event<Product>> = _updatedRecentProduct

    fun setUpdateProductEvent(
        id: Long,
        updatedValue: Int,
    ) {
        _updateProductEvent.value = Event(ProductUpdate(id, updatedValue))
    }

    fun setUpdatedRecentViewedProduct(product: Product) {
        _updatedRecentProduct.value = Event(product)
    }
}
