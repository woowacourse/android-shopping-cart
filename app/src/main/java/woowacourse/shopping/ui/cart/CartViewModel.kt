package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product

class CartViewModel : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> get() = _products

    val cartDummyRepository = CartDummyRepositoryImpl

    fun updateProducts(count: Int) {
        val newProducts = cartDummyRepository.fetchCartProducts(count, products.value?.lastOrNull()?.id ?: 0)
        _products.value = products.value?.plus(newProducts)
    }

    fun removeCartProduct(id: Int) {
        cartDummyRepository.removeCartProduct(id)
        _products.value = products.value?.filter { it.id != id }
    }
}
