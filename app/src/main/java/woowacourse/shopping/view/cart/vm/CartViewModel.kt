package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.domain.Product

class CartViewModel(private val cartStorage: CartStorage) : ViewModel() {
    private val _products = MutableLiveData(cartStorage.getAll())
    val products: LiveData<List<Product>> = _products
}
