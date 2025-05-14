package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.domain.Product

class CartViewModel(private val cartStorage: CartStorage) : ViewModel() {
    private val _products = MutableLiveData(cartStorage.getAll())
    val products: LiveData<List<Product>> = _products

    private val _removeItemPosition = MutableLiveData<Int>()
    val removeItemPosition: LiveData<Int> = _removeItemPosition

    fun deleteProduct(productId: Long) {
        val position = _products.value?.indexOfFirst { it.id == productId } ?: -1
        _products.value = _products.value?.filter { it.id != productId }
        _removeItemPosition.value = position
        cartStorage.deleteProduct(productId)
    }
}
