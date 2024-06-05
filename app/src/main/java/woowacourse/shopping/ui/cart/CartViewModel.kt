package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.mapper.toModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.livedata.MutableSingleLiveData
import woowacourse.shopping.ui.livedata.SingleLiveData

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _cartProducts: MutableLiveData<List<Product>> =
        MutableLiveData(emptyList())
    val cartProducts: LiveData<List<Product>> get() = _cartProducts

    private val _onCartProductDeleted = MutableSingleLiveData<Unit>()
    val onCartProductDeleted: SingleLiveData<Unit> get() = _onCartProductDeleted

    fun getAllCartProducts() {
        viewModelScope.launch {
            val cartProducts = cartRepository
                .getAllCartProducts()
                .map { it.toModel() }
            _cartProducts.value = cartProducts
        }
    }

    fun deleteCartProduct(id: Int) {
        viewModelScope.launch {
            cartRepository.deleteCartProduct(id.toLong())
            _onCartProductDeleted.setValue(Unit)
        }
    }
}
