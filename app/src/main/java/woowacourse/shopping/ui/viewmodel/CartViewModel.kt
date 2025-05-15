package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.Product

class CartViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> get() = _products

    init {
        update()
    }

    fun update() {
        repository.fetchAll { products ->
            _products.postValue(products)
        }
    }
}
